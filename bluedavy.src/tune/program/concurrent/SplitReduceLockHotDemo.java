/**
 * 
 */
package tune.program.concurrent;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 尽可能少用锁：尽可能只对需要控制的资源做加锁操作
 * 拆分锁：独占锁拆分为多把锁（读写锁拆分、类似ConcurrentHashMap中默认拆分为16把锁）
 * 
 * @author yangwm Aug 24, 2010 11:59:35 PM
 */
public class SplitReduceLockHotDemo {

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
        private int lockCount = 10;
        private Lock[] locks = new Lock[lockCount];
        private Handler() {
            for (int i = 0; i < lockCount; i++) {
                locks[i] = new ReentrantLock();
            }
        }
        public static Handler getInstance() {
            return self;
        }
        public void handle(int id) {
            // execute sth don't need lock
            try {
                Thread.sleep(random.nextInt(5));
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            int mod = id % lockCount;
            try {
                locks[mod].lock();
                
                // execute sth
                try {
                    Thread.sleep(random.nextInt(5));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } finally {
                locks[mod].unlock();
            }
        }
    }

}

/*
Round: 1
......
Round: 10
Execute summary: Round( 10 ) Thread Per Round( 200 ) Execute Time ( 843 ) ms

*/

