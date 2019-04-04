package manual.producer;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {
    private BlockingQueue<Data> blockingQueue;
    private static final int SLEEP_TIME = 5000;

    public Consumer(BlockingQueue<Data> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        System.out.println("消费者:" + Thread.currentThread().getName());
        Random random = new Random();
        try {
            while (true) {
                Data data = blockingQueue.take();
                if (data != null) {
                    int val = data.getData();
                    System.out.println(Thread.currentThread().getId() + "消费 " + val);
                    Thread.sleep(random.nextInt(SLEEP_TIME));
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}
