/**
 * 
 */
package tune.program.coroutine.kilim;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 基于Kilim采用Coroutine方式支撑高并发请求 
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
        Handler hander = new Handler();
        long beginTime = System.currentTimeMillis();
        for (int i = 0; i < threadCount; i++) {
            new Handler().start();
        }
        latch.await();
        long endTime = System.currentTimeMillis();
        System.out.println("Kilim Coroutine SimpleBenchMark consume Time:  " + (endTime - beginTime) + " ms");
    }
    
    class Handler implements Task {
        
        @Override
        public void execute() throws Pausable, Exception {
            Mailbox<String> resultMailbox = new Mailbox<String>(1);
            resultMailbox.get(random.nextInt(10) + 100);
            latch.countDown();
        }
        
    }

}

/*
Java SimpleBenchMark consume Time:  2703 ms

*/

