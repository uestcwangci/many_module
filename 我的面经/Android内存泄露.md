# Android内存泄露 #
----------
![Java内存泄露原因](http://www.ibm.com/developerworks/cn/java/l-JavaMemoryLeak/2.gif)
## 集合类泄漏 ##
集合类如果仅仅有添加元素的方法，而没有相应的删除机制，导致内存被占用。如果这个集合类是全局性的变量 (比如类中的静态属性，全局性的 map 等即有静态引用或 final 一直指向它)，那么没有相应的删除机制，很可能导致集合所占用的内存只增不减。

	Vector v = new Vector(10);
	for (int i = 1; i < 100; i++) {
	    Object o = new Object();
	    v.add(o);
	    o = null;   
	}
在这个例子中，我们循环申请Object对象，并将所申请的对象放入一个 Vector 中，如果我们仅仅释放引用本身，那么 Vector 仍然引用该对象，所以这个对象对 GC 来说是不可回收的。因此，如果对象加入到Vector 后，还必须从 Vector 中删除，最简单的方法就是将 Vector 对象设置为 null。
## 单例造成的内存泄露 ##
由于单例的静态特性使得其生命周期跟应用的生命周期一样长，所以如果使用不恰当的话，很容易造成内存泄漏。

	public class AppManager {
	  private static AppManager instance;
	  private Context context;
	  private AppManager(Context context) {
	  this.context = context;
	  }
	  public static AppManager getInstance(Context context) {
	    if (instance == null) {
	    instance = new AppManager(context);
	    }
	    return instance;
	  }
	}
这是一个普通的单例模式，当创建这个单例的时候，由于需要传入一个Context，所以这个Context的生命周期的长短至关重要：

1. 如果此时传入的是 Application 的 Context，因为 Application 的生命周期就是整个应用的生命周期，所以这将没有任何问题。
1. 如果此时传入的是 Activity 的 Context，当这个 Context 所对应的 Activity 退出时，由于该 Context 的引用被单例对象所持有，其生命周期等于整个应用程序(Application)的生命周期，所以当前 Activity 退出时它的内存并不会被回收，这就造成泄漏了。

正确的使用方法

	public class AppManager {
	  private static AppManager instance;
	  private Context context;
	  private AppManager(Context context) {
	    this.context = context.getApplicationContext();// 使用Application 的context
	 }
	  public static AppManager getInstance(Context context) {
	    if (instance == null) {
	      instance = new AppManager(context);
	    }
	    return instance;
	  }
	}
## 匿名内部类/非静态内部类和异步线程 ##
### 非静态内部类创建静态实例造成的内存泄漏 ###
有的时候我们可能会在启动频繁的Activity中，为了避免重复创建相同的数据资源，可能会出现这种写法：

	public class MainActivity extends AppCompatActivity {
	  private static TestResource mResource = null;
	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);
	    if(mManager == null){
	      mManager = new TestResource();
	    }
	    //...
	    }
	  class TestResource {
	  //...
	  }
	}
这样就在Activity内部创建了一个非静态内部类的单例，每次启动Activity时都会使用该单例的数据，这样虽然避免了资源的重复创建，不过这种写法却会造成内存泄漏，**因为非静态内部类默认会持有外部类的引用，而该非静态内部类又创建了一个静态的实例，该实例的生命周期和应用的一样长，这就导致了该静态实例一直会持有该Activity的引用，导致Activity的内存资源不能正常回收。**

正确的做法为：将该内部类设为静态内部类或将该内部类抽取出来封装成一个单例，如果需要使用Context，请按照上面推荐的使用Application 的 Context。

当然，Application 的 context 不是万能的，所以也不能随便乱用，对于有些地方则必须使用 Activity 的 Context，对于Application，Service，Activity三者的Context的应用场景如下：
![](http://img.blog.csdn.net/20151123144226349)
其中： NO1表示 Application 和 Service 可以启动一个 Activity，不过需要创建一个新的 task 任务队列。而对于 Dialog 而言，只有在 Activity 中才能创建
### 匿名内部类 ###
android开发经常会继承实现Activity/Fragment/View，此时如果你使用了匿名类，并被异步线程持有了，那要小心了，如果没有任何措施这样一定会导致泄露.

	public class MainActivity extends Activity {
	  ...
	  Runnable ref1 = new MyRunable();
	  Runnable ref2 = new Runnable() {
	      @Override
	      public void run() {
	
	      }
	  };
	   ...
	}
ref1和ref2的区别是，ref2使用了匿名内部类。我们来看看运行时这两个引用的内存：

![](http://img2.tbcdn.cn/L1/461/1/fb05ff6d2e68f309b94dd84352c81acfe0ae839e)

可以看到，ref1没什么特别的。 但ref2这个匿名类的实现对象里面多了一个引用： this$0这个引用指向MainActivity.this，也就是说**当前的MainActivity实例会被ref2持有，如果将这个引用再传入一个异步线程，此线程和此Acitivity生命周期不一致的时候，就造成了Activity的泄露。**
## Handler 造成的内存泄漏 ##
Handler 的使用造成的内存泄漏问题应该说是最为常见了，很多时候我们为了避免 ANR 而不在主线程进行耗时操作，在处理网络任务或者封装一些请求回调等api都借助Handler来处理，但 Handler 不是万能的，对于 Handler 的使用代码编写一不规范即有可能造成内存泄漏。另外，我们知道 Handler、Message 和 MessageQueue 都是相互关联在一起的，万一 Handler 发送的 Message 尚未被处理，则该 Message 及发送它的 Handler 对象将被线程 MessageQueue 一直持有。 由于 Handler 属于 TLS(Thread Local Storage) 变量, 生命周期和 Activity 是不一致的。因此这种实现方式一般很难保证跟 View 或者 Activity 的生命周期保持一致，故很容易导致无法正确释放。

	public class SampleActivity extends Activity {
	
	  private final Handler mLeakyHandler = new Handler() {
	    @Override
	    public void handleMessage(Message msg) {
	      // ...
	    }
	  }
	
	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    // Post a message and delay its execution for 10 minutes.
	    mLeakyHandler.postDelayed(new Runnable() {
	      @Override
	      public void run() { /* ... */ }
	    }, 1000 * 60 * 10);
	
	    // Go back to the previous Activity.
	    finish();
	  }
	}
在该 SampleActivity 中声明了一个延迟10分钟执行的消息 Message，mLeakyHandler 将其 push 进了消息队列 MessageQueue 里。当该 Activity 被 finish() 掉时，延迟执行任务的 Message 还会继续存在于主线程中，它持有该 Activity 的 Handler 引用，所以此时 finish() 掉的 Activity 就不会被回收了从而造成内存泄漏（因 Handler 为非静态内部类，它会持有外部类的引用，在这里就是指 SampleActivity）。

修复方法：在 Activity 中避免使用非静态内部类，比如上面我们将 Handler 声明为静态的，则其存活期跟 Activity 的生命周期就无关了。同时通过弱引用的方式引入 Activity，避免直接将 Activity 作为 context 传进去，见下面代码：

	public class SampleActivity extends Activity {
	
	  /**
	   * Instances of static inner classes do not hold an implicit
	   * reference to their outer class.
	   */
	  private static class MyHandler extends Handler {
	    private final WeakReference<SampleActivity> mActivity;
	
	    public MyHandler(SampleActivity activity) {
	      mActivity = new WeakReference<SampleActivity>(activity);
	    }
	
	    @Override
	    public void handleMessage(Message msg) {
	      SampleActivity activity = mActivity.get();
	      if (activity != null) {
	        // ...
	      }
	    }
	  }
	
	  private final MyHandler mHandler = new MyHandler(this);
	
	  /**
	   * Instances of anonymous classes do not hold an implicit
	   * reference to their outer class when they are "static".
	   */
	  private static final Runnable sRunnable = new Runnable() {
	      @Override
	      public void run() { /* ... */ }
	  };
	
	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    // Post a message and delay its execution for 10 minutes.
	    mHandler.postDelayed(sRunnable, 1000 * 60 * 10);
	
	    // Go back to the previous Activity.
	    finish();
	  }
	}
创建一个静态Handler内部类，然后对 Handler 持有的对象使用弱引用，这样在回收时也可以回收 Handler 持有的对象，但是这样做虽然避免了 Activity 泄漏，不过 Looper 线程的消息队列中还是可能会有待处理的消息，所以我们在 Activity 的 Destroy 时或者 Stop 时应该移除消息队列 MessageQueue 中的消息。

下面几个方法都可以移除 Message：

	public final void removeCallbacks(Runnable r);
	
	public final void removeCallbacks(Runnable r, Object token);
	
	public final void removeCallbacksAndMessages(Object token);
	
	public final void removeMessages(int what);
	
	public final void removeMessages(int what, Object object);
## 资源未关闭造成的内存泄漏 ##
对于使用了BraodcastReceiver，ContentObserver，File，游标 Cursor，Stream，Bitmap等资源的使用，应该在Activity销毁时及时关闭或者注销，否则这些资源将不会被回收，造成内存泄漏。