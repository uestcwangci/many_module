# Handler机制 #
---
> 流程图
> ![handler流程](https://img-blog.csdn.net/20180620112532709?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FpamluZ2xhaQ==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)
> 主要涉及到
> 
> 1. ThreadLocal 管理不同线程looper对象，保证looper唯一性
> 2. Looper 主要管理MessageQueue中消息的出队
> 3. MessageQueue 消息按照时间顺序排列在其中,提供next()方法，使消息出队
> 4. Handler 消息入队与消息的接收者
> 5. Message 消息对象

## ThreadLocal ##
> 2个主要方法get()、set()

保证不同线程中相同类型的对象各自独立。
## Meaasage ##
    public final class Message implements Parcelable {
 
    public int what;
    public int arg1; 
    public int arg2;
    public Object obj;
    /*package*/ Handler target;
    /*package*/ Runnable callback;
 
    /**
     * Return a new Message instance from the global pool. Allows us to
     * avoid allocating new objects in many cases.
     */
    public static Message obtain() {
        synchronized (sPoolSync) {
            if (sPool != null) {
                Message m = sPool;
                sPool = m.next;
                m.next = null;
                m.flags = 0; // clear in-use flag
                sPoolSize--;
                return m;
            }
        }
        return new Message();
    }
 
    /** Constructor (but the preferred way to get a Message is to call {@link #obtain() Message.obtain()}).
    */
    public Message() {}
	}
Message 实现了Parcelable 接口，也就是说实现了序列化，这就说明Message可以在不同进程之间传递。

- 包含一个名为target的Handler对象
- 包含一个名为callback的Runnable对象
- 使用obtain 方法可以从消息池中获取Message的实例，也是推荐大家使用的方法，而不是直接调用构造方法。
## MeaasgeQueue ##
管理消息出队next()和入队enqueueMessage

	Message next() {...}
	boolean enqueueMessage(Message msg, long when) {...}
	
其中涉及很多native方法
## Looper ##
> 2个主要方法 prepare()和loop()

- Looper.getMainLooper()获取主线程looper对象
- Looper.myLooper()获取当先线程looper对象


**构造方法**中可以看出Looper构造是自动创建了一个MessageQueue

	private Looper(boolean quitAllowed) {
        mQueue = new MessageQueue(quitAllowed);
        mThread = Thread.currentThread();
    }
**Prepare()方法**，通过sThreadLocal.get()获取到一个Looper对象，一个线程只能有一个Looper否则会报错

    private static void prepare(boolean quitAllowed) {
        if (sThreadLocal.get() != null) {
            throw new RuntimeException("Only one Looper may be created per thread");
        }
        sThreadLocal.set(new Looper(quitAllowed));
    }
**Looper()方法**

	public static void loop() {
        final Looper me = myLooper();
        if (me == null) {
            throw new RuntimeException("No Looper; Looper.prepare() wasn't called on this thread.");
        }
        final MessageQueue queue = me.mQueue;
        for (;;) {
            Message msg = queue.next(); // might block
            if (msg == null) {
                // No message indicates that the message queue is quitting.
                return;
            }
            msg.target.dispatchMessage(msg);
            msg.recycleUnchecked();
        }
    }

1. myLooper()方法返回此线程唯一的looper，保证线程间互相不会干扰
2. 不断从MessageQueue中获取Message对象
3. target是个hanlder对象，调用handler的dispatchMessage()方法
## Handler ##
### 2种构造方式 ###
绑定需要绑定线程looper构造

	public Handler(Looper looper) {
        this(looper, null, false);
    }
绑定callback的构造，这里callback是一个runnable对象，即调用者的线程

	public Handler(Callback callback) {
        this(callback, false);
    }
### 几种常用发送方法(入队) ###
    public final boolean post(Runnable r)
    {
       return  sendMessageDelayed(getPostMessage(r), 0);
	}

----------

	public final boolean postDelayed(Runnable r, long delayMillis)
    {
        return sendMessageDelayed(getPostMessage(r), delayMillis);
    }

----------
	public final boolean sendMessage(Message msg)
    {
        return sendMessageDelayed(msg, 0);
    }

----------
    public final boolean sendMessageDelayed(Message msg, long delayMillis)
    {
        if (delayMillis < 0) {
            delayMillis = 0;
        }
        return sendMessageAtTime(msg, SystemClock.uptimeMillis() + delayMillis);
    }

最后都是调用了sendMessageAtTime()方法

	public boolean sendMessageAtTime(Message msg, long uptimeMillis) {
        MessageQueue queue = mQueue;
        if (queue == null) {
            RuntimeException e = new RuntimeException(
                    this + " sendMessageAtTime() called with no mQueue");
            Log.w("Looper", e.getMessage(), e);
            return false;
        }
        return enqueueMessage(queue, msg, uptimeMillis);
    }

	private boolean enqueueMessage(MessageQueue queue, Message msg, long uptimeMillis) {
        msg.target = this; //这里的this指当前handler对象
        if (mAsynchronous) {
            msg.setAsynchronous(true);
        }
        return queue.enqueueMessage(msg, uptimeMillis);
    }
此时完成消息的入队,消息在队列中是根据消息要分发的时间排序在MessageQueue中，等待loop()方法调用
### 出队,是由looper.loop()方法调用 ###

	public void dispatchMessage(Message msg) {
        if (msg.callback != null) {
            handleCallback(msg);
        } else {
            if (mCallback != null) {
                if (mCallback.handleMessage(msg)) {
                    return;
                }
            }
            handleMessage(msg);
        }
    }

	// 这里执行的是run方法而不是start方法，表示是外部线程来执行start方法
	private static void handleCallback(Message message) {
        message.callback.run();
    }

  	// 覆写此方法即可收到消息
	public void handleMessage(Message msg) {
    }