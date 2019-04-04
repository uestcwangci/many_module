package manual.producer;

import java.util.concurrent.*;

public class Test {
    public static void main(String[] args) {
        BlockingQueue<Data> blockingQueue = new LinkedBlockingDeque<>();
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

    }
}
