package com.unit1;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;



/**
 * java多线程模拟生产者消费者问题
 *
 * ProducerConsumer是主类，Producer生产者，Consumer消费者，Product产品，Storage仓库
 *
 * @author 林计钦
 * @version 1.0 2013-7-24 下午04:49:02
 */
public class ProducerConsumer {
    public static void main(String[] args) {
        ProducerConsumer pc = new ProducerConsumer();

        Storage s = pc.new Storage();

        ExecutorService service = Executors.newCachedThreadPool();
        Producer p = pc.new Producer("张三", s);
        Producer p2 = pc.new Producer("李四", s);
        Producer p3 = pc.new Producer("李3", s);
        Producer p4 = pc.new Producer("李4", s);
        Producer p5 = pc.new Producer("李5", s);
        Producer p6 = pc.new Producer("李6", s);
        Producer p7 = pc.new Producer("李7", s);
        Consumer c = pc.new Consumer("王五", s);
//        Consumer c2 = pc.new Consumer("老刘", s);
//        Consumer c3 = pc.new Consumer("老林", s);
        Date start = new Date(System.currentTimeMillis());
        SimpleDateFormat sf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        System.out.println(sf.format(start));
        service.execute(p);
        service.execute(p2);
        service.execute(p3);
        service.execute(p4);
        service.execute(p5);
        service.execute(p6);
        service.execute(p7);
        service.execute(c);
//        service.execute(c2);
//        service.execute(c3);
        try {
            Thread.sleep(5 * 1000);
            p.stop();
            p2.stop();
            p3.stop();
            p4.stop();
            p5.stop();
            p6.stop();
            p7.stop();
            Date stop = new Date(System.currentTimeMillis());
            System.out.println(sf.format(stop));
            Thread.sleep(3000);
            System.out.println("sleep 3000");
            service.shutdown();
            System.out.println("shutdown");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * 消费者
     *
     * @author 林计钦
     * @version 1.0 2013-7-24 下午04:53:30
     */
    class Consumer implements Runnable {
        private String name;
        private Storage s = null;

        public Consumer(String name, Storage s) {
            this.name = name;
            this.s = s;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    System.out.println(name + "准备消费产品.");
                    Product product = s.pop();
                    System.out.println(name + "已消费(" + product.toString() + ").");
                    System.out.println("===============");
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();

            }

        }

    }

    /**
     * 生产者
     *
     * @author 林计钦
     * @version 1.0 2013-7-24 下午04:53:44
     */
    class Producer implements Runnable {
        private String name;
        private Storage s = null;
        private volatile boolean isRunning = true;

        public Producer(String name, Storage s) {
            this.name = name;
            this.s = s;
        }

        @Override
        public void run() {
            try {
                while (isRunning) {
                    Product product = new Product((int) (Math.random() * 10000)); // 产生0~9999随机整数
                    System.out.println(name + "准备生产(" + product.toString() + ").");
                    s.push(product);
                    System.out.println(name + "已生产(" + product.toString() + ").");
                    System.out.println("===============");
                    Thread.sleep(500);
                }
            } catch (InterruptedException e1) {
                e1.printStackTrace();
                Thread.currentThread().interrupt();
            }

        }

        public void stop() {
            isRunning = false;
        }
    }

    /**
     * 仓库，用来存放产品
     *
     * @author 林计钦
     * @version 1.0 2013-7-24 下午04:54:16
     */
    public class Storage {
        BlockingQueue<Product> queues = new LinkedBlockingQueue<Product>(10);

        /**
         * 生产
         *
         * @param p
         *            产品
         * @throws InterruptedException
         */
        public void push(Product p) throws InterruptedException {
            queues.put(p);
        }

        /**
         * 消费
         *
         * @return 产品
         * @throws InterruptedException
         */
        public Product pop() throws InterruptedException {
            return queues.take();
        }
    }

    /**
     * 产品
     *
     * @author 林计钦
     * @version 1.0 2013-7-24 下午04:54:04
     */
    public class Product {
        private int id;

        public Product(int id) {
            this.id = id;
        }

        @Override
        public String toString() {// 重写toString方法
            return "产品：" + this.id;
        }
    }

}