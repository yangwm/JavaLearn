/**
 * 
 */
package coroutine.java.benchmark;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * java coroutine
 * 
 * @author yangwm Jul 26, 2010 11:11:56 PM
 */
public class Main {

    private static final int PROCESSER_COUNT = Runtime.getRuntime().availableProcessors();

    private static final int THREAD_COUNT = PROCESSER_COUNT * 200;

    private static final ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

    private static final ExecutorService callbackExecutor = Executors.newCachedThreadPool();

    private static int perThreadRequests = 10000;

    private static int request_count = PROCESSER_COUNT * perThreadRequests;

    private static CountDownLatch processorLatch = new CountDownLatch(request_count);

    public static void main(String[] args) throws Exception {
        if (args.length == 1) {
            perThreadRequests = Integer.valueOf(args[0]);
            request_count = PROCESSER_COUNT * perThreadRequests;
            processorLatch = new CountDownLatch(request_count);
        }
        System.out.println("=========Java Version=========");
        System.out.println(" Receive Thread Count: " + PROCESSER_COUNT);
        System.out.println(" Requests Per Receive Task: " + perThreadRequests);
        System.out.println(" Request Thread Counts: " + THREAD_COUNT);
        System.out.println(" Request Counts: " + request_count);
        Main main = new Main();
        main.execute();
    }

    protected void execute() throws Exception {
        final CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
        Runnable runnable = new Runnable() {
            public void run() {
                try {
                    Thread.sleep(5000);
                    latch.countDown();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        // init threadPool
        for (int i = 0; i < THREAD_COUNT; i++) {
            executor.execute(runnable);
        }
        latch.await();
        long beginTime = System.currentTimeMillis();
        for (int i = 0; i < PROCESSER_COUNT; i++) {
            new Thread(new Task()).start();
        }
        processorLatch.await();
        long endTime = System.currentTimeMillis();
        long consumeTime = endTime - beginTime;
        System.out.println("Consume Time: " + consumeTime + " ms");
        System.out.println("TPS About: " + request_count * 1000 / consumeTime);
        System.out.println("=========Java Version=========");
        executor.shutdownNow();
        callbackExecutor.shutdownNow();
    }

    class Task implements Runnable {

        public void run() {
            for (int i = 0; i < perThreadRequests; i++) {
                Runnable processor = new Processor();
                executor.execute(processor);
            }
        }

    }

    class Processor implements Runnable {

        public void run() {
            // 改为做个异步的操作，这样在java版里就可以采用
            for (Integer i = 0; i < 10; i++) {
                BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(1);
                Runnable runnable = new AsyncProcessor(queue);
                callbackExecutor.execute(runnable);
                queue.poll();
            }
            processorLatch.countDown();
        }

    }

    class AsyncProcessor implements Runnable {

        private BlockingQueue<Integer> queue;

        public AsyncProcessor(BlockingQueue<Integer> queue) {
            this.queue = queue;
        }

        public void run() {
            StringBuilder strBuilder = new StringBuilder();
            for (int i = 0; i < 1000; i++) {
                strBuilder.append("ArrayList[");
                strBuilder.append(i);
                strBuilder.append("];");
            }
            try {
                queue.put(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}

/*
D:\study\tempProject\JavaLearn\classes>java coroutine.java.benchmark.Main
=========Java Version=========
 Receive Thread Count: 2
 Requests Per Receive Task: 10000
 Request Thread Counts: 400
 Request Counts: 20000
Consume Time: 95515 ms
TPS About: 209
=========Java Version=========

D:\study\tempProject\JavaLearn\classes>

*/
