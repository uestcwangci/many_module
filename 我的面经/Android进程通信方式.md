# Android进程通信方式 #

----------
## 使用Intent ##
1. Activity，Service，Receiver 都支持在 Intent 中传递 Bundle 数据，而 Bundle 实现了 Parcelable 接口，可以在不同的进程间进行传输。
1. 在一个进程中启动了另一个进程的 Activity，Service 和 Receiver ，可以在 Bundle 中附加要传递的数据通过 Intent 发送出去。
## 使用文件共享 ##
1. Windows 上，一个文件如果被加了排斥锁会导致其他线程无法对其进行访问，包括读和写；而 Android 系统基于 Linux ，使得其并发读取文件没有限制地进行，甚至允许两个线程同时对一个文件进行读写操作，尽管这样可能会出问题。
1. 可以在一个进程中序列化一个对象到文件系统中，在另一个进程中反序列化恢复这个对象（注意：并不是同一个对象，只是内容相同。）。
1. SharedPreferences 是个特例，系统对它的读 / 写有一定的缓存策略，即内存中会有一份 ShardPreferences 文件的缓存，系统对他的读 / 写就变得不可靠，当面对高并发的读写访问，SharedPreferences 有很多大的几率丢失数据。因此，IPC 不建议采用 SharedPreferences。

[SharedPreference基本用法](https://www.cnblogs.com/ywtk/p/3795184.html "SharedPreference基本用法")
## 使用 Messenger ##
Messenger 是一种轻量级的 IPC 方案，它的底层实现是 AIDL ，可以在不同进程中传递 Message 对象，它**一次只处理一个请求**，在服务端不需要考虑线程同步的问题，服务端不存在并发执行的情形。**是一种同步的通信方式，不适合于处理大量数据。**
### 使用方法： ###
- 服务端进程：服务端创建一个 Service 来处理客户端请求，同时通过一个 Handler 对象来实例化一个 Messenger 对象，然后在 Service 的 onBind 中返回这个 Messenger 对象底层的 Binder 即可。

		public class MessengerService extends Service {
		    private final static String TAG = "LogTAG.MessengerService";
		
		    public final static int MSG_FROM_CLIENT = 0;
		    public final static int MSG_FROM_SERVICE = 1;
		
		    private Messenger mMessenger = new Messenger(new MessengerHandler());
		
		    public MessengerService() {
		    }
		
		    @Override
		    public void onCreate() {
		        super.onCreate();
		    }
		
		    @Override
		    public int onStartCommand(Intent intent, int flags, int startId) {
		
		        return START_STICKY;
		    }
		
		    @Override
		    public IBinder onBind(Intent intent) {
		
		        return mMessenger.getBinder();
		    }
		
		    @Override
		    public void onDestroy() {
		        super.onDestroy();
		    }
		
		    private static class MessengerHandler extends Handler {
		        @Override
		        public void handleMessage(Message msg) {
		            super.handleMessage(msg);
		
		            switch (msg.what) {
		                case MSG_FROM_CLIENT:
		                    Log.i(TAG,"Message from client = " + msg.getData().getString("msg"));
		                    Message serviceMsg = Message.obtain();
							Messenger client = msg.replyTo;
		                    serviceMsg.what = MSG_FROM_SERVICE;
		                    Bundle data = new Bundle();
		                    data.putString("msg","Hi, peter ! I am lemon .");
		                    serviceMsg.setData(data);
		                    if(client != null){
		                        try {
		                            client.send(serviceMsg);
		                        } catch (RemoteException e) {
		                            e.printStackTrace();
		                        }
		                    }
		
		                    break;
		                default:
		                    //do nothing
		            }
		        }
		    }
		}
- 客户端进程：首先绑定服务端 Service ，绑定成功之后用服务端的 IBinder 对象创建一个 Messenger ，通过这个 Messenger 就可以向服务端发送消息了，消息类型是 Message 。如果需要服务端响应，则需要创建一个 Handler 并通过它来创建一个 Messenger（和服务端一样），并通过 Message 的 replyTo 参数传递给服务端。服务端通过 Message 的 replyTo 参数就可以回应客户端了。

		public class ClientActivity extends AppCompatActivity implements View.OnClickListener{
		
		    private final static String TAG = "LogTAG.ClientActivity";
		
		    public final static int MSG_FROM_CLIENT = 0;
		    public final static int MSG_FROM_SERVICE = 1;
		
		    private Messenger mClientMessenger;
		    private Messenger mServiceMessenger;
		    private Button mBindService;
		    private Button mSend;
		
		    private ServiceConnection mSC = new ServiceConnection() {
		        @Override
		        public void onServiceConnected(ComponentName name, IBinder service) {
		            Log.i(TAG,"onServiceConnected name = " + name.toString());
		            mServiceMessenger = new Messenger(service);
		        }
		
		        @Override
		        public void onServiceDisconnected(ComponentName name) {
		            Log.i(TAG,"onServiceDisconnected name = " + name.toString());
		            mServiceMessenger = null;
		        }
		    };
		
		    @Override
		    protected void onCreate(Bundle savedInstanceState) {
		        super.onCreate(savedInstanceState);
		        setContentView(R.layout.activity_client);
		
		        mClientMessenger = new Messenger(new MessengerHandler());
		        mBindService = findViewById(R.id.btn_bind_service);
		        mBindService.setOnClickListener(this);
		        mSend = findViewById(R.id.btn_client_send);
		        mSend.setOnClickListener(this);
		    }
		
		    @Override
		    protected void onResume() {
		        super.onResume();
		    }
		
		    @Override
		    protected void onPause() {
		        super.onPause();
		    }
		
		    @Override
		    protected void onDestroy() {
		        super.onDestroy();
		        if(mSC != null) {
		            unbindService(mSC);
		        }
		    }
		
		    @Override
		    public void onClick(View v) {
		        switch (v.getId()) {
		            case R.id.btn_bind_service:
		                Intent intent = new Intent(this,MessengerService.class);
		                bindService(intent,mSC, Context.BIND_AUTO_CREATE);
		                break;
		            case R.id.btn_client_send:
		                Message clientMsg = Message.obtain();
		                clientMsg.what = MSG_FROM_CLIENT;
		                clientMsg.replyTo = mClientMessenger;
		                Bundle data = new Bundle();
		                data.putString("msg","Hi, I am peter ! What's your name ?");
		                clientMsg.setData(data);
		                if(mServiceMessenger != null) {
		                    try {
		                        mServiceMessenger.send(clientMsg);
		                    } catch (RemoteException e) {
		                        e.printStackTrace();
		                    }
		                }
		                break;
		            default:
		                //do nothing
		        }
		    }
		
		    private static class MessengerHandler extends Handler {
		        @Override
		        public void handleMessage(Message msg) {
		            super.handleMessage(msg);
		
		            switch (msg.what) {
		                case MSG_FROM_SERVICE:
		                    Log.i(TAG,"Message from service = " + msg.getData().getString("msg"));
		                    break;
		                default:
		                    //do nothing
		            }
		        }
		    }
		}
注意：客户端和服务端是通过拿到对方的 Messenger 来发送 Message 的。只不过客户端通过 bindService onServiceConnected 而服务端通过 message.replyTo 来获得对方的 Messenger 。Messenger 中有一个 Hanlder 以串行的方式处理队列中的消息。不存在并发执行，因此我们不用考虑线程同步的问题。
## 使用 ContentProvider ##
底层实现同样是 Binder 和 AIDL，系统做了封装，使用简单。 系统预置了许多 ContentProvider ，如通讯录、日程表，需要跨进程访问。 使用方法：继承 ContentProvider 类实现 6 个抽象方法，这六个方法均运行在 ContentProvider 进程中，除 onCreate 运行在主线程里，其他五个方法均由外界回调运行在 Binder 线程池中。

ContentProvider 的底层数据，可以是 SQLite 数据库，可以是文件，也可以是内存中的数据。

	public class BookProvider extends ContentProvider {
	    private static final String TAG = "BookProvider";
	    public static final String AUTHORITY = "com.jc.ipc.Book.Provider";
	
	    public static final Uri BOOK_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/book");
	    public static final Uri USER_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/user");
	
	    public static final int BOOK_URI_CODE = 0;
	    public static final int USER_URI_CODE = 1;
	    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	
	    static {
	        sUriMatcher.addURI(AUTHORITY, "book", BOOK_URI_CODE);
	        sUriMatcher.addURI(AUTHORITY, "user", USER_URI_CODE);
	    }
	
	    private Context mContext;
	    private SQLiteDatabase mDB;
	
	    @Override
	    public boolean onCreate() {
	        mContext = getContext();
	        initProviderData();
	
	        return true;
	    }
	
	    private void initProviderData() {
	        //不建议在 UI 线程中执行耗时操作
	        mDB = new DBOpenHelper(mContext).getWritableDatabase();
	        mDB.execSQL("delete from " + DBOpenHelper.BOOK_TABLE_NAME);
	        mDB.execSQL("delete from " + DBOpenHelper.USER_TABLE_NAME);
	        mDB.execSQL("insert into book values(3,'Android');");
	        mDB.execSQL("insert into book values(4,'iOS');");
	        mDB.execSQL("insert into book values(5,'Html5');");
	        mDB.execSQL("insert into user values(1,'haohao',1);");
	        mDB.execSQL("insert into user values(2,'nannan',0);");
	
	    }
	
	    @Nullable
	    @Override
	    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
	        Log.d(TAG, "query, current thread"+ Thread.currentThread());
	        String table = getTableName(uri);
	        if (table == null) {
	            throw new IllegalArgumentException("Unsupported URI" + uri);
	        }
	
	        return mDB.query(table, projection, selection, selectionArgs, null, null, sortOrder, null);
	    }
	
	    @Nullable
	    @Override
	    public String getType(Uri uri) {
	        Log.d(TAG, "getType");
	        return null;
	    }
	
	    @Nullable
	    @Override
	    public Uri insert(Uri uri, ContentValues values) {
	        Log.d(TAG, "insert");
	        String table = getTableName(uri);
	        if (table == null) {
	            throw new IllegalArgumentException("Unsupported URI" + uri);
	        }
	        mDB.insert(table, null, values);
	        // 通知外界 ContentProvider 中的数据发生变化
	        mContext.getContentResolver().notifyChange(uri, null);
	        return uri;
	    }
	
	    @Override
	    public int delete(Uri uri, String selection, String[] selectionArgs) {
	        Log.d(TAG, "delete");
	        String table = getTableName(uri);
	        if (table == null) {
	            throw new IllegalArgumentException("Unsupported URI" + uri);
	        }
	        int count = mDB.delete(table, selection, selectionArgs);
	        if (count > 0) {
	            mContext.getContentResolver().notifyChange(uri, null);
	        }
	
	        return count;
	    }
	
	    @Override
	    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
	        Log.d(TAG, "update");
	        String table = getTableName(uri);
	        if (table == null) {
	            throw new IllegalArgumentException("Unsupported URI" + uri);
	        }
	        int row = mDB.update(table, values, selection, selectionArgs);
	        if (row > 0) {
	            getContext().getContentResolver().notifyChange(uri, null);
	        }
	        return row;
	    }
	
	    private String getTableName(Uri uri) {
	        String tableName = null;
	        switch (sUriMatcher.match(uri)) {
	            case BOOK_URI_CODE:
	                tableName = DBOpenHelper.BOOK_TABLE_NAME;
	                break;
	            case USER_URI_CODE:
	                tableName = DBOpenHelper.USER_TABLE_NAME;
	                break;
	            default:
	                break;
	        }
	
	        return tableName;
	
	    }
	}

----------
	public class DBOpenHelper extends SQLiteOpenHelper {
	
	    private static final String DB_NAME = "book_provider.db";
	    public static final String BOOK_TABLE_NAME = "book";
	    public static final String USER_TABLE_NAME = "user";
	
	    private static final int DB_VERSION = 1;
	
	    private String CREATE_BOOK_TABLE = "CREATE TABLE IF NOT EXISTS "
	            + BOOK_TABLE_NAME + "(_id INTEGER PRIMARY KEY," + "name TEXT)";
	
	    private String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS "
	            + USER_TABLE_NAME + "(_id INTEGER PRIMARY KEY," + "name TEXT,"
	            + "sex INT)";
	
	
	
	    public DBOpenHelper(Context context) {
	        super(context, DB_NAME, null, DB_VERSION);
	    }
	
	    @Override
	    public void onCreate(SQLiteDatabase db) {
	        db.execSQL(CREATE_BOOK_TABLE);
	        db.execSQL(CREATE_USER_TABLE);
	
	    }
	
	    @Override
	    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	
	    }
	}

----------
	public class ProviderActivity extends AppCompatActivity {
	    private static final String TAG = ProviderActivity.class.getSimpleName();
	    private TextView displayTextView;
	    private Handler mHandler;
	
	    @Override
	    protected void onCreate(@Nullable Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_provider);
	        displayTextView = (TextView) findViewById(R.id.displayTextView);
	        mHandler = new Handler();
	
	        getContentResolver().registerContentObserver(BookProvider.BOOK_CONTENT_URI, true, new ContentObserver(mHandler) {
	            @Override
	            public boolean deliverSelfNotifications() {
	                return super.deliverSelfNotifications();
	            }
	
	            @Override
	            public void onChange(boolean selfChange) {
	                super.onChange(selfChange);
	            }
	
	            @Override
	            public void onChange(boolean selfChange, Uri uri) {
	                Toast.makeText(ProviderActivity.this, uri.toString(), Toast.LENGTH_SHORT).show();
	                super.onChange(selfChange, uri);
	            }
	        });
	
	
	
	
	    }
	
	    public void insert(View v) {
	        ContentValues values = new ContentValues();
	        values.put("_id",1123);
	        values.put("name", "三国演义");
	        getContentResolver().insert(BookProvider.BOOK_CONTENT_URI, values);
	
	    }
	    public void delete(View v) {
	        getContentResolver().delete(BookProvider.BOOK_CONTENT_URI, "_id = 4", null);
	
	
	    }
	    public void update(View v) {
	        ContentValues values = new ContentValues();
	        values.put("_id",1123);
	        values.put("name", "三国演义新版");
	        getContentResolver().update(BookProvider.BOOK_CONTENT_URI, values , "_id = 1123", null);
	
	
	    }
	    public void query(View v) {
	        Cursor bookCursor = getContentResolver().query(BookProvider.BOOK_CONTENT_URI, new String[]{"_id", "name"}, null, null, null);
	        StringBuilder sb = new StringBuilder();
	        while (bookCursor.moveToNext()) {
	            Book book = new Book(bookCursor.getInt(0),bookCursor.getString(1));
	            sb.append(book.toString()).append("\n");
	        }
	        sb.append("--------------------------------").append("\n");
	        bookCursor.close();
	
	        Cursor userCursor = getContentResolver().query(BookProvider.USER_CONTENT_URI, new String[]{"_id", "name", "sex"}, null, null, null);
	        while (userCursor.moveToNext()) {
	            sb.append(userCursor.getInt(0))
	                    .append(userCursor.getString(1)).append(" ,")
	                    .append(userCursor.getInt(2)).append(" ,")
	                    .append("\n");
	        }
	        sb.append("--------------------------------");
	        userCursor.close();
	        displayTextView.setText(sb.toString());
	    }
	}
## 使用AIDL ##
Messenger 是以串行的方式处理客户端发来的消息，如果大量消息同时发送到服务端，服务端只能一个一个处理，所以大量并发请求就不适合用 Messenger ，而且 Messenger 只适合传递消息，不能跨进程调用服务端的方法。AIDL 可以解决并发和跨进程调用方法的问题，要知道 Messenger 本质上也是 AIDL ，只不过系统做了封装方便上层的调用而已。
## 使用Socket ##


![Socket通信](https://github.com/astaxie/build-web-application-with-golang/raw/master/zh/images/8.1.socket.png?raw=true)

Client端代码：

	public class TCPClientActivity extends AppCompatActivity implements View.OnClickListener{
	
	    private static final String TAG = "TCPClientActivity";
	    public static final int MSG_RECEIVED = 0x10;
	    public static final int MSG_READY = 0x11;
	    private EditText editText;
	    private TextView textView;
	    private PrintWriter mPrintWriter;
	    private Socket mClientSocket;
	    private Button sendBtn;
	    private StringBuilder stringBuilder;
	    private Handler mHandler = new Handler(){
	        @Override
	        public void handleMessage(Message msg) {
	            switch (msg.what) {
	                case MSG_READY:
	                    sendBtn.setEnabled(true);
	                    break;
	                case MSG_RECEIVED:
	                    stringBuilder.append(msg.obj).append("\n");
	                    textView.setText(stringBuilder.toString());
	                    break;
	                default:
	                    super.handleMessage(msg);
	            }
	
	    }
	    };
	
	    @Override
	    protected void onCreate(@Nullable Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.tcp_client_activity);
	        editText = (EditText) findViewById(R.id.editText);
	        textView = (TextView) findViewById(R.id.displayTextView);
	        sendBtn = (Button) findViewById(R.id.sendBtn);
	        sendBtn.setOnClickListener(this);
	        sendBtn.setEnabled(false);
	        stringBuilder = new StringBuilder();
	
	        Intent intent = new Intent(TCPClientActivity.this, TCPServerService.class);
	        startService(intent);
	
	        new Thread(){
	            @Override
	            public void run() {
	                connectTcpServer();
	            }
	        }.start();
	    }
	
	
	    private String formatDateTime(long time) {
	        return new SimpleDateFormat("(HH:mm:ss)").format(new Date(time));
	    }
	
	    private void connectTcpServer() {
	        Socket socket = null;
	        while (socket == null) {
	            try {
	                socket = new Socket("localhost", 8888);
	                mClientSocket = socket;
	                mPrintWriter = new PrintWriter(new BufferedWriter(
	                        new OutputStreamWriter(socket.getOutputStream())
	                ), true);
	                mHandler.sendEmptyMessage(MSG_READY);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	
	        // receive message
	        BufferedReader bufferedReader = null;
	        try {
	            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        while (!isFinishing()) {
	            try {
	                String msg = bufferedReader.readLine();
	                if (msg != null) {
	                    String time = formatDateTime(System.currentTimeMillis());
	                    String showedMsg = "server " + time + ":" + msg
	                            + "\n";
	                    mHandler.obtainMessage(MSG_RECEIVED, showedMsg).sendToTarget();
	                }
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	
	        }
	    }
	
	    @Override
	    public void onClick(View v) {
	        if (mPrintWriter != null) {
	            String msg = editText.getText().toString();
	            mPrintWriter.println(msg);
	            editText.setText("");
	            String time = formatDateTime(System.currentTimeMillis());
	            String showedMsg = "self " + time + ":" + msg + "\n";
	            stringBuilder.append(showedMsg);
	
	        }
	
	    }
	
	    @Override
	    protected void onDestroy() {
	        if (mClientSocket != null) {
	            try {
	                mClientSocket.shutdownInput();
	                mClientSocket.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        super.onDestroy();
	    }
	}
Server 端代码：

	public class TCPServerService extends Service {
	    private static final String TAG = "TCPServerService";
	    private boolean isServiceDestroyed = false;
	    private String[] mMessages = new String[]{
	            "Hello! Body!",
	            "用户不在线！请稍后再联系！",
	            "请问你叫什么名字呀？",
	            "厉害了，我的哥！",
	            "Google 不需要科学上网是真的吗？",
	            "扎心了，老铁！！！"
	    };
	
	
	    @Override
	    public void onCreate() {
	        new Thread(new TCPServer()).start();
	        super.onCreate();
	    }
	
	    @Override
	    public void onDestroy() {
	        isServiceDestroyed = true;
	        super.onDestroy();
	    }
	
	    @Nullable
	    @Override
	    public IBinder onBind(Intent intent) {
	        return null;
	    }
	
	    private class TCPServer implements Runnable {
	
	        @Override
	        public void run() {
	            ServerSocket serverSocket = null;
	            try {
	                serverSocket = new ServerSocket(8888);
	            } catch (IOException e) {
	                e.printStackTrace();
	                return;
	            }
	            while (!isServiceDestroyed) {
	                // receive request from client
	                try {
	                    final Socket client = serverSocket.accept();
	                    Log.d(TAG, "=============== accept ==================");
	                    new Thread(){
	                        @Override
	                        public void run() {
	                            try {
	                                responseClient(client);
	                            } catch (IOException e) {
	                                e.printStackTrace();
	                            }
	                        }
	                    }.start();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	
	        }
	    }
	
	
	    private void responseClient(Socket client) throws IOException {
	        //receive message
	        BufferedReader in = new BufferedReader(
	                new InputStreamReader(client.getInputStream()));
	        //send message
	        PrintWriter out = new PrintWriter(
	                new BufferedWriter(
	                        new OutputStreamWriter(
	                                client.getOutputStream())),true);
	        out.println("欢迎来到聊天室！");
	
	        while (!isServiceDestroyed) {
	            String str = in.readLine();
	            Log.d(TAG, "message from client: " + str);
	            if (str == null) {
	                return;
	            }
	            Random random = new Random();
	            int index = random.nextInt(mMessages.length);
	            String msg = mMessages[index];
	            out.println(msg);
	            Log.d(TAG, "send Message: " + msg);
	        }
	        out.close();
	        in.close();
	        client.close();
	
	    }
	}