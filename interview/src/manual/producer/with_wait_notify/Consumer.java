package manual.producer.with_wait_notify;

import java.util.Queue;
import java.util.Random;

public class Consumer implements Runnable {
    private final Queue<Data> queue;
    private static final int SLEEP_TIME = 2000;

    public Consumer(Queue<Data> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            Random random = new Random();
            System.out.println("消费者：" + Thread.currentThread().getName());
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }
                synchronized (queue) {
                    if (queue.isEmpty()) {
                        System.out.println("队列为空，消费等待");
                        queue.wait();
                        queue.notifyAll();
                    }
                    Data data = queue.poll();
                    if (data != null) {
                        System.out.println("消费:" + data.getData());
                    }

                }
                Thread.sleep(random.nextInt(SLEEP_TIME));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
