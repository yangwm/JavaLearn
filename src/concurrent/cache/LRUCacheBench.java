/**
 * 
 */
package concurrent.cache;

import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * 
 * 
 * @author yangwm Mar 20, 2011 5:56:07 PM
 */
public class LRUCacheBench {
    
    private static final long executeTimes = 10 * 100 * 1000;
    private static int threadCount = 100;
    private static int size = 10000;
    
    private static Map<Long, Long> map = new CopyOnWriteLRUHashCache<Long, Long>(size, 100 * 1000);
    
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
        for (long i = 1; i <= 150; i++) {
            map.put(i, i);
        }
        for (long i = 150; i >= 1; i--) {
            map.put(i, i);
        }
        for (long i = 0; i < executeTimes; i++) {
            Long result = map.get(new Long(i % 100));
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
        System.out.println(map.getClass().getName() + " get executeTimes: " + executeTimes + ", summary consumeTime: " 
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
            
            for (long i = 0; i < executeTimes; i++) {
                Long result = map.get(new Long(i % 100));
            }
            endLatch.countDown();
        }
    }
    
}

/*
threadCount 100:
concurrent.CopyOnWriteLRUHashCache get executeTimes:10000000, summary consumeTime: 5219 ms, for Per Thread consumeTime: 521 ms

*/

/*
threadCount 10:
concurrent.cache.SynchronizedLRUHashMap get executeTimes: 1000000, summary consumeTime: 1578 ms, for Per Thread consumeTime: 157 ms
concurrent.cache.CopyOnWriteLRUHashMap get executeTimes: 1000000, summary consumeTime: 891 ms, for Per Thread consumeTime: 89 ms

threadCount 50:
concurrent.cache.SynchronizedLRUHashMap get executeTimes: 1000000, summary consumeTime: 6438 ms, for Per Thread consumeTime: 128 ms
concurrent.cache.CopyOnWriteLRUHashMap get executeTimes: 1000000, summary consumeTime: 4266 ms, for Per Thread consumeTime: 85 ms

threadCount 200:
concurrent.cache.SynchronizedLRUHashMap get executeTimes: 1000000, summary consumeTime: 24875 ms, for Per Thread consumeTime: 124 ms
concurrent.cache.CopyOnWriteLRUHashMap get executeTimes: 1000000, summary consumeTime: 17641 ms, for Per Thread consumeTime: 88 ms

*/

