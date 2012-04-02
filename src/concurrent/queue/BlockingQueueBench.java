/**
 * 
 */
package concurrent.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.LinkedBlockingQueue;

import concurrent.queue.RemindBean.Type;

/**
 * 
 * 
 * @author yangwm Mar 24, 2011 8:48:34 PM
 */
public class BlockingQueueBench {
    
    private static final int executeTimes = 100 * 1000;
    private static int threadCount = 100;
    
    //private static BlockingQueue<RemindBean> queue = new ArrayBlockingQueue<RemindBean>(10000000);
    //private static BlockingQueue<RemindBean> queue = new LinkedBlockingQueue<RemindBean>(10000000);
    private static BlockingQueue<RemindBean> queue = new DelayQueue<RemindBean>();
    //private static BlockingQueue<RemindBean> queue = new SynchronousQueue<RemindBean>();
    
    private static CountDownLatch startLatch = new CountDownLatch(1);
    private static CountDownLatch endLatch = new CountDownLatch(threadCount);

    /**
     * @param args
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws InterruptedException {
        /*
         * 
         */
        for (int i = 0; i < executeTimes; i++) {
            try {
                RemindBean remindBean = new RemindBean(Type.INCR, i);
                queue.put(remindBean);
            } catch (InterruptedException e) {
                ;
            }
            RemindBean result = queue.poll();
        }
        Task task = new Task();
        System.out.println("begin...");
        
        /*
         * 
         */
        long beginTime = System.currentTimeMillis();
        for (int i = 0; i < threadCount; i++) {
            //System.out.println("Thread " + i + " start...");
            new Thread(task).start();
        }
        startLatch.countDown();
        endLatch.await();
        long consumeTime = System.currentTimeMillis() - beginTime;
        System.out.println(queue.getClass().getName() + " put executeTimes: " + executeTimes + ", summary consumeTime: " 
                + consumeTime + " ms" + ", for Per Thread consumeTime: " + (consumeTime / threadCount) + " ms");
    }
    
    static class Task implements Runnable {
        @Override
        public void run() {
            try {
                startLatch.await();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            for (int i = 0; i < executeTimes; i++) {
                try {
                    RemindBean remindBean = new RemindBean(Type.INCR, i);
                    queue.put(remindBean);
                } catch (InterruptedException e) {
                    ;
                }
                RemindBean result = queue.poll();
            }
            endLatch.countDown();
        }
    }
    
}

/*
begin...
java.util.concurrent.LinkedBlockingQueue put executeTimes: 100000, summary consumeTime: 6812 ms, for Per Thread consumeTime: 68 ms

begin...
java.util.concurrent.DelayQueue put executeTimes: 100000, summary consumeTime: 44719 ms, for Per Thread consumeTime: 447 ms




threadCount 10:
java.util.concurrent.ArrayBlockingQueue put executeTimes: 1000000, summary consumeTime: 6047 ms, for Per Thread consumeTime: 604 ms
java.util.concurrent.LinkedBlockingQueue put executeTimes: 1000000, summary consumeTime: 6610 ms, for Per Thread consumeTime: 661 ms


threadCount 10:
java.util.concurrent.ArrayBlockingQueue offer executeTimes: 1000000, summary consumeTime: 1859 ms, for Per Thread consumeTime: 185 ms
java.util.concurrent.LinkedBlockingQueue offer executeTimes: 1000000, summary consumeTime: 2343 ms, for Per Thread consumeTime: 234 ms

threadCount 100:
java.util.concurrent.ArrayBlockingQueue offer executeTimes: 1000000, summary consumeTime: 18875 ms, for Per Thread consumeTime: 188 ms
java.util.concurrent.LinkedBlockingQueue offer executeTimes: 1000000, summary consumeTime: 26859 ms, for Per Thread consumeTime: 268 ms


*/

