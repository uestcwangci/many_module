package manual.producer.with_wait_notify;

import java.util.IllegalFormatCodePointException;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Producer implements Runnable {
    private final Queue<Data> queue;
    private int length;
    private volatile boolean isRunning = true;
    private static final int SLEEP_TIME = 3000;
    private AtomicInteger count;

    public Producer(Queue<Data> queue, int length) {
        this.count = new AtomicInteger();
        this.queue = queue;
        this.length = length;
    }

    @Override
    public void run() {
        Data data = null;
        Random random = new Random();
        System.out.println("生产者：" + Thread.currentThread().getName());
        try {
            while (isRunning) {
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }
                data = new Data(count.incrementAndGet());
                System.out.println(Thread.currentThread().getName() + "生产了:" + data.getData());
                synchronized (queue) {
                    if (queue.size() >= length) {
                        System.out.println("队列已满，生产等待");
                        count.decrementAndGet();
                        queue.wait();
                    }
                    queue.offer(data);
                    queue.notifyAll();

                }
                Thread.sleep(random.nextInt(SLEEP_TIME));

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
