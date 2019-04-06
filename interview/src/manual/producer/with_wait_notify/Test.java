package manual.producer.with_wait_notify;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {
    public static void main(String[] args) {
        int queueLen = 10;
        Queue<Data> queue = new LinkedList<>();
        Producer p1 = new Producer(queue, queueLen);
        Producer p2 = new Producer(queue, queueLen);
        Producer p3 = new Producer(queue, queueLen);
        Producer p4 = new Producer(queue, queueLen);
        Consumer c1 = new Consumer(queue);
        Consumer c2 = new Consumer(queue);
        Consumer c3 = new Consumer(queue);
        Consumer c4 = new Consumer(queue);
        ExecutorService service = Executors.newFixedThreadPool(8);
        service.execute(p1);
        service.execute(p2);
        service.execute(p3);
        service.execute(p4);
        service.execute(c1);
        service.execute(c2);
//        service.execute(c3);
//        service.execute(c4);

    }
}
