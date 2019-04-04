package manual.producer;

import java.util.concurrent.*;

public class ThreadPoolTest {
    private static int i = 0;
    public static void main(String[] args) {
        testThreadPoolExecutor();

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
}
