package manual.producer;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Producer implements Runnable{
    private BlockingQueue<Data> blockingQueue;
    private volatile boolean isRunning = true;
    private AtomicInteger count = new AtomicInteger();
    private static final int SLEEP_TIME = 5000;

    public Producer(BlockingQueue<Data> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        Data data = null;
        Random random = new Random();
        System.out.println("生产者:" + Thread.currentThread().getName());
        try {
            while (isRunning) {
                Thread.sleep(random.nextInt(SLEEP_TIME));
                data = new Data(count.incrementAndGet());
                System.out.println(Thread.currentThread().getId() + "生产 " + data.getData());
                if (!blockingQueue.offer(data, 2, TimeUnit.SECONDS)) {
                    System.out.println("加入失败");
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
    public void stop() {
        isRunning = false;
    }
}
