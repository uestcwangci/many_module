package manual.producer.with_blockqueue;

import java.util.concurrent.*;

public class Test {
    public static void main(String[] args) {
        BlockingQueue<Data> blockingQueue = new LinkedBlockingDeque<>(10);
        Producer p1 = new Producer(blockingQueue);
        Producer p2 = new Producer(blockingQueue);
        Producer p3 = new Producer(blockingQueue);
        Producer p4 = new Producer(blockingQueue);
        Consumer c1 = new Consumer(blockingQueue);
        Consumer c2 = new Consumer(blockingQueue);
        Consumer c3 = new Consumer(blockingQueue);
        Consumer c4 = new Consumer(blockingQueue);
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(p1);
        service.execute(p2);
        service.execute(p3);
        service.execute(p4);
        service.execute(c1);
        service.execute(c2);
        service.execute(c3);
        service.execute(c4);
        try {
            Thread.sleep(5000);
            p1.stop();
            p2.stop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        service.shutdown();

    }
}
