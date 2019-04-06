package manual.producer.with_blockqueue;

import java.util.concurrent.*;

public class ThreadPoolTest {
    private static int i = 0;
    private static Thread t1 = new MyThread("t1");
    private static Thread t2 = new MyThread("t2");
    private static Thread t3 = new MyThread("t3");
    private static Thread t4 = new MyThread("t4");
    public static void main(String[] args) {
//        testThreadPoolExecutor();
//        testExecutorCachePool();
//        testFixedPool();
//        testScheduledPool();
        testSinglePool();
    }

    private static void testThreadPoolExecutor() {
        ExecutorService service = new ThreadPoolExecutor(6, 12, 5,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(6 * 4), new ThreadPoolExecutor.CallerRunsPolicy());
        for (int j = 0; j < 12; j++) {
            service.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(i++ + " ");
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            });
        }

        try {
            Thread.sleep(10000);
            service.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void testExecutorCachePool() {
        ExecutorService service = Executors.newCachedThreadPool();
        Future<Integer> future = service.submit(new Callable<Integer>() {
            @Override
            public Integer call() {
                System.out.println("submit");
                return 2;
            }
        });
        try {
            Integer num = future.get();
            System.out.println(num);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static void testFixedPool() {
        ExecutorService service = Executors.newFixedThreadPool(4);


        service.execute(t1);
        service.execute(t2);
        service.execute(t3);
        service.execute(t4);
    }

    private static void testScheduledPool() {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(4);
        service.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("schedule延迟3s进行");
            }
        }, 3, TimeUnit.SECONDS);
        service.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("延迟2s终点-开始4s进行");
            }
        }, 2, 4, TimeUnit.SECONDS);
        service.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                System.out.println("延迟1s开始-开始5s进行");
            }
        }, 1, 5, TimeUnit.SECONDS);
    }

    private static void testSinglePool() {
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(t1);
        service.execute(t2);
        service.execute(t3);

    }

    private
    static class MyThread extends Thread {
        public MyThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            System.out.println(this.getName());

        }
    }

}
