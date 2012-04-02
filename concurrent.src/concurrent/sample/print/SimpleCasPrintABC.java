/**
 * 
 */
package concurrent.sample.print;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * three Thread A, B C; 
 * Thread A print "A"
 * Thread B print "B"
 * Thread C print "C"
 * 
 * like this ABC...ABC(Total 10 ABC)
 * 
 * 小结： 
 * 利用lock+cas对简单实现（synchronized+volatile）进行提升性能, 并简化编程  
 * 使用while(condition) { lock.wait(); }+lockfree算法 提升并发调度健壮性
 * 
 * @author yangwm Jul 29, 2010 11:23:32 AM
 */
public class SimpleCasPrintABC {
    
    public static void main(String[] args) {
        new Thread(new PrintRun(), "A").start();
        new Thread(new PrintRun(), "B").start();
        new Thread(new PrintRun(), "C").start();
    }
    
    /** mutex */
    private static final Lock PRINT_LOCK = new ReentrantLock();

    /** mutex for waiting print */
    private static final Condition PRINT_CONDITION = PRINT_LOCK.newCondition();
    
    /** total print times */
    private static final int TOTAL_PRINT_COUNT = 10;

    /** semaphore */
    private static AtomicReference<String> semaphore = new AtomicReference<String>("A");
    
    public static boolean nextSemaphore() {
        boolean result = false;
        
        String semaphoreValue = semaphore.get();
        if("A".equals(semaphoreValue))  {
            result = semaphore.compareAndSet(semaphoreValue, "B");
        } else if("B".equals(semaphoreValue)) {
            result = semaphore.compareAndSet(semaphoreValue, "C");
        } else if("C".equals(semaphoreValue)) {
            result = semaphore.compareAndSet(semaphoreValue, "A");
        }
        
        return result;
    }   

    /**
     * Thread print current thread name
     */
    private static class PrintRun implements Runnable {
        /** print times */
        private int printCount = TOTAL_PRINT_COUNT;
        
        @Override
        public void run() {
            while (printCount > 0) {
                String threadName = Thread.currentThread().getName();
                PRINT_LOCK.lock();
                try {
                    try {
                        while(!semaphore.get().equals(threadName)) {
                            PRINT_CONDITION.await();
                        }
                    } catch (InterruptedException e) {
                        System.err.println(threadName + " Print InterruptedException");
                        PRINT_CONDITION.signalAll();
                    }
                } finally {
                    PRINT_LOCK.unlock();
                }
                
                /* do print */
                System.out.print(threadName);
                
                for(;;) {
                    if (nextSemaphore()) {
                        break;
                    }
                }
                printCount--;
                
                PRINT_LOCK.lock();
                try {
                    PRINT_CONDITION.signalAll();
                } finally {
                    PRINT_LOCK.unlock();
                }
            }
        }
    }

}

/*
ABCABCABCABCABCABCABCABCABCABC

*/

