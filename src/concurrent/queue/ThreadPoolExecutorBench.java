/**
 * 
 */
package concurrent.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 高性能（任务缓冲队列大小为1，直接交给线程执行）：SynchronousQueue
 * 缓冲执行（任务缓冲队列大小为corePoolSize）：ArrayBlockingQueue、LinkedBlockingQueue
 * 
 * @author yangwm Mar 25, 2011 3:44:19 PM
 */
public class ThreadPoolExecutorBench {

    //final BlockingQueue<Runnable> queue = new SynchronousQueue<Runnable>();
    //final BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(10);
    final BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();
    final ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 600, 30, TimeUnit.SECONDS, queue, 
            Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
    final AtomicInteger completedTask = new AtomicInteger(0);
    final AtomicInteger rejectedTask = new AtomicInteger(0);
    static long beginTime;
    final int count = 1000;

    public void start() {
        CountDownLatch latch = new CountDownLatch(count);
        CyclicBarrier barrier = new CyclicBarrier(count);
        for (int i = 0; i < count; i++) {
            new Thread(new TestThread(latch, barrier)).start();
        }
        try {
            latch.await();
            executor.shutdownNow();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        
        System.out.println("queue:" + queue.getClass().getName());
        System.out.println("reject task number:" + rejectedTask.get());
        System.out.println("execute task number:" + completedTask.get());
        System.out.println("end execute cosume time:" + (System.currentTimeMillis() - beginTime) + " ms");
    }
    

    class TestThread implements Runnable {
        private CountDownLatch latch;
        private CyclicBarrier barrier;

        public TestThread(CountDownLatch latch, CyclicBarrier barrier) {
            this.latch = latch;
            this.barrier = barrier;
        }

        @Override
        public void run() {
            try {
                barrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                executor.execute(new Task(latch));
            } catch (RejectedExecutionException exception) {
                latch.countDown();
                rejectedTask.incrementAndGet();
            }
        }
    }

    class Task implements Runnable {
        private CountDownLatch latch;

        public Task(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            completedTask.incrementAndGet();
            //System.out.println("task execute cosume time:" + (System.currentTimeMillis() - beginTime) + " ms");
            latch.countDown();
        }
    }


    /**
     * @param args
     */
    public static void main(String[] args) {
        beginTime = System.currentTimeMillis();
        ThreadPoolExecutorBench bench = new ThreadPoolExecutorBench();
        bench.start();
    }

}

/*
new SynchronousQueue<Runnable>(): 
reject task number:400
execute task number:600
end execute cosume time:3438 ms

new ArrayBlockingQueue<Runnable>(10);
reject task number:390
execute task number:610
end execute cosume time:6188 ms

new LinkedBlockingQueue<Runnable>(10);
reject task number:390
execute task number:610
end execute cosume time:6203 ms

*/

