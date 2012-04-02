/**
 * 
 */
package tune.program.concurrent;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 锁竞争的状况会比较明显，这时候线程很容易处于等待锁的状况，从而导致性能下降以及CPU sy上升
 * 
 * @author yangwm Aug 24, 2010 11:59:35 PM
 */
public class LockHotDemo {

    private static int executeTimes = 10;
    private static int threadCount = Runtime.getRuntime().availableProcessors() * 100;
    private static CountDownLatch latch = null;
    
    public static void main(String[] args) throws Exception {
        HandleTask task = new HandleTask();
        long beginTime = System.currentTimeMillis();
        for (int i = 0; i < executeTimes; i++) {
            System.out.println("Round: " + (i + 1));
            latch = new CountDownLatch(threadCount);
            for (int j = 0; j < threadCount; j++) {
                new Thread(task).start();
            }
            latch.await();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Execute summary: Round( " + executeTimes + " ) Thread Per Round( " + threadCount 
                + " ) Execute Time ( " + (endTime - beginTime) + " ) ms");
    }
    
    static class HandleTask implements Runnable {
        private final Random random = new Random();

        @Override
        public void run() {
            Handler.getInstance().handle(random.nextInt(10000));
            latch.countDown();
        }
        
    }
    
    static class Handler {
        private static final Handler self = new Handler();
        private final Random random = new Random();
        private final Lock lock = new ReentrantLock();
        private Handler() {
            
        }
        public static Handler getInstance() {
            return self;
        }
        public void handle(int id) {
            try {
                lock.lock();
                
                // execute sth
                try {
                    Thread.sleep(random.nextInt(10));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } finally {
                lock.unlock();
            }
        }
    }

}

/*
Round: 1
......
Round: 10
Execute summary: Round( 10 ) Thread Per Round( 200 ) Execute Time ( 10625 ) ms

*/

