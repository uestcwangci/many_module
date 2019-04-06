# Java线程池 #

----------
继承于ExecutorService接口

## 线程池的配置 ##

	public ThreadPoolExecutor(int corePoolSize, 
		int maximumPoolSize,
		long keepAliveTime,
		TimeUnit unit,
		BlockingQueue<Runnable> workQueue,
		ThreadFactory threadFactory,
		RejectedExecutionHandler handler)
### 1. corePoolSize ###
默认情况下，核心线程中的线程**一直存活**在线程池中，即便他们在线程池中处于闲置状态。除非我们将ThreadPoolExecutor的allowCoreThreadTimeOut属性设为true的时候，这时候处于闲置的核心线程在等待新任务到来时会有超时策略，这个超时时间由keepAliveTime来指定。一旦超过所设置的超时时间，闲置的核心线程就会被终止。
### 2.maximumPoolSize  ###
线程池中所容纳的**最大线程数**，如果活动的线程达到这个数值以后，后续的新任务将会被阻塞。包含核心线程数+非核心线程数。
### 3. keepAliveTime ###
**非核心线程闲置时的超时时长**，对于非核心线程，闲置时间超过这个时间，非核心线程就会被回收。只有对ThreadPoolExecutor的allowCoreThreadTimeOut属性设为true的时候，这个超时时间才会对核心线程产生效果。
### 4. unit ###
用于指定keepAliveTime参数的时间单位。他是一个枚举，可以使用的单位有天（**TimeUnit.DAYS**），小时（**TimeUnit.HOURS**），分钟（**TimeUnit.MINUTES**），秒(**TimeUnit.SECONDS** )，毫秒(**TimeUnit.MILLISECONDS**)
### 5. workQueue ###
线程池中保存等待执行的任务的阻塞队列。通过线程池中的execute方法提交的Runable对象都会存储在该队列中。我们可以选择下面几个阻塞队列。

队列 | 说明
:-|:-
ArrayBlockingQueue |基于数组实现的有界的阻塞队列,该队列按照FIFO（先进先出）原则对队列中的元素进行排序。
LinkedBlockingQueue|基于链表实现的阻塞队列，该队列按照FIFO（先进先出）原则对队列中的元素进行排序。
PriorityBlockingQueue|具有优先级的无限阻塞队列。
SynchronousQueue|内部没有任何容量的阻塞队列。在它内部没有任何的缓存空间。
### 6. threadFactory ###
线程工厂，为线程池提供新线程的创建。ThreadFactory是一个接口，里面只有一个newThread方法。 默认为DefaultThreadFactory类。
### 7. handler ###
是RejectedExecutionHandler对象，而RejectedExecutionHandler是一个接口，里面只有一个rejectedExecution方法。当任务队列已满并且线程池中的活动线程已经**达到所限定的最大值**或者是**无法成功执行任务**，这时候ThreadPoolExecutor会调用RejectedExecutionHandler中的rejectedExecution方法。在ThreadPoolExecutor中有四个内部类实现了
RejectedExecutionHandler接口。在线程池中它默认是AbortPolicy。

ThreadPoolExecutor.xxPolicy()|说明
:-|:-
AbortPolicy|直接抛出RejectedExecutionException异常。(默认方式)
DiscardPolicy|丢弃掉该任务，不进行处理。
DiscardOldestPolicy|丢弃队列里最老的一个任务，并执行当前任务.
CallerRunsPolicy|只用调用者所在线程来运行任务。
## 线程池的执行 ##
### 1.submit() ###
当我们使用submit来提交任务时,它会返回一个future,我们就可以通过这个future来判断任务是否执行成功，还可以通过future的get方法来获取返回值。如果子线程任务没有完成，get方法会阻塞住直到任务完成，而使用get(long timeout, TimeUnit unit)方法则会阻塞一段时间后立即返回，这时候有可能任务并没有执行完。传入Callable接口，重写call()方法.
    
    Future<Integer> future = service.submit(new Callable<Integer>() {
    
	    @Override
	    	public Integer call() throws Exception {
		    System.out.println("submit方式");
		    return 2;
	    }
    });
    try {
	    Integer number = future.get();
    } catch (ExecutionException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

### 2.execute() ###
execute方法没有返回值，所以说我们也就无法判定任务是否被线程池执行成功。
## 线程池的关闭 ##
- shutdown原理：将线程池状态设置成SHUTDOWN状态，然后中断所有没有正在执行任务的线程。
- shutdownNow原理：将线程池的状态设置成STOP状态，然后中断所有任务(包括正在执行的)的线程，并返回等待执行任务的列表。**中断采用interrupt方法，所以无法响应中断的任务可能永远无法终止。**
## 四种已配置的线程池 通过Executors.xx(args..)创建##
### 1. newCachedThreadPool ###
	public static ExecutorService newCachedThreadPool() {
	    return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
	        60L, TimeUnit.SECONDS,
	        new SynchronousQueue<Runnable>());
	}
 **核心线程数为0**， **线程池的最大线程数Integer.MAX_VALUE**。而Integer.MAX_VALUE是一个很大的数，也差不多可以说 这个线程池中的最大线程数可以任意大。

**当线程池中的线程都处于活动状态的时候，线程池就会创建一个新的线程来处理任务。**该线程池中的线程超时时长为60秒，所以**当线程处于闲置状态超过60秒的时候便会被回收。** 这也就意味着若是整个线程池的线程都处于闲置状态超过60秒以后，在newCachedThreadPool线程池中是不存在任何线程的，所以这时候它几乎不占用任何的系统资源。

对于newCachedThreadPool他的任务队列采用的是SynchronousQueue，上面说到在SynchronousQueue内部没有任何容量的阻塞队列。SynchronousQueue内部相当于一个空集合，我们无法将一个任务插入到SynchronousQueue中。所以说在线程池中如果现有线程无法接收任务,将会创建新的线程来执行任务。
### 2. newFixedThreadPool ###
构造函数

	public static ExecutorService newFixedThreadPool(int nThreads) {
	    return new ThreadPoolExecutor(nThreads, nThreads,
	        0L, TimeUnit.MILLISECONDS,
	        new LinkedBlockingQueue<Runnable>());
	}
实现方法

	ExecutorService service = Executors.newFixedThreadPool(4);
**核心线程数=最大线程数，**所容纳最大的线程数就是我们设置的核心线程数。newFixedThreadPool只有核心线程，并且不存在超时机制，采用LinkedBlockingQueue，所以对于任务队列的大小也是没有限制的。
### 3. newScheduledThreadPool ###

	ScheduledExecutorService service = Executors.newScheduledThreadPool(4);//实例化方式不同

	public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize) {
	    return new ScheduledThreadPoolExecutor(corePoolSize);
	}
	public ScheduledThreadPoolExecutor(int corePoolSize) {
	    super(corePoolSize, Integer.MAX_VALUE, 0, NANOSECONDS,
	          new DelayedWorkQueue());
	}
它的**核心线程数是固定的**，对于**非核心线程几乎可以说是没有限制的**，并且当非核心线程处于限制状态的时候就会立即被回收。

	schedule(Runnable command, long delay, TimeUnit unit);延迟一定时间后执行Runnable任务；

	schedule(Callable callable, long delay, TimeUnit unit);延迟一定时间后执行Callable任务；

	scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit);延迟一定时间后，
	以间隔period时间的频率周期性地执行任务；方法的周期时间间隔是以上一个任务开始执行到下一个任务开始执行的间隔
	线程1开始 - 线程2开始  间隔

	scheduleWithFixedDelay(Runnable command, long initialDelay, long delay,TimeUnit unit);
	该方法的周期时间间隔是以上一个任务执行结束到下一个任务开始执行的间隔.
	线程1结束 - 线程2开始 间隔
### 4.newSingleThreadExecutor ###
	public static ExecutorService newSingleThreadExecutor() {
	    return new FinalizableDelegatedExecutorService
	    (new ThreadPoolExecutor(1, 1,
	        0L, TimeUnit.MILLISECONDS,
	        new LinkedBlockingQueue<Runnable>()));
	}
在这个线程池中**只有一个核心线程**，对于任务队列没有大小限制，也就意味着这**一个任务处于活动状态时，其他任务都会在任务队列中排队等候依次执行**。
## CPU密集 / IO密集 ##
任务类型|说明
-|-
CPU密集型任务|线程池中线程个数应尽量少，如配置N+1个线程的线程池。
IO密集型任务|由于IO操作速度远低于CPU速度，那么在运行这类任务时，CPU绝大多数时间处于空闲状态，那么线程池可以配置尽量多些的线程，以提高CPU利用率，如2*N。
混合型任务|可以拆分为CPU密集型任务和IO密集型任务，当这两类任务执行时间相差无几时，通过拆分再执行的吞吐率高于串行执行的吞吐率，但若这两类任务执行时间有数据级的差距，那么没有拆分的意义。
