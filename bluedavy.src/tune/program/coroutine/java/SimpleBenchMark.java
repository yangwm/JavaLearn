/**
 * 
 */
package tune.program.coroutine.java;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 传统方式支撑高并发请求 
 * 
 * @author yangwm Aug 24, 2010 10:39:14 AM
 */
public class SimpleBenchMark {
    
    private int threadCount = 10000;
    private CountDownLatch latch = new CountDownLatch(threadCount);
    private Random random = new Random();
    
    
    public static void main(String[] args) throws Exception {
        SimpleBenchMark benchMark = new SimpleBenchMark();
        benchMark.runBeanchmark();
    }
    
    public void runBeanchmark() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(400);
        Handler hander = new Handler();
        long beginTime = System.currentTimeMillis();
        for (int i = 0; i < threadCount; i++) {
            executor.execute(hander);
        }
        latch.await();
        long endTime = System.currentTimeMillis();
        System.out.println("Java SimpleBenchMark consume Time:  " + (endTime - beginTime) + " ms");
        executor.shutdown();
    }
    
    class Handler implements Runnable {
        
        @Override
        public void run() {
            BlockingQueue<String> resultQueue = new ArrayBlockingQueue<String>(1);
            try {
                resultQueue.poll(random.nextInt(10) + 100, TimeUnit.MILLISECONDS);
            } catch (Exception e) {
                e.printStackTrace();
            }
            latch.countDown();
        }
        
    }

}

/*
Java SimpleBenchMark consume Time:  2703 ms

*/

