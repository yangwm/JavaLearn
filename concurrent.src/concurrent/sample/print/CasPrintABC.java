/**
 * 
 */
package concurrent.sample.print;

import java.util.concurrent.atomic.AtomicBoolean;
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
 * 小结： 利用lock+cas对简单实现（synchronized+volatile）进行提升性能 
 * 
 * @author yangwm Jul 29, 2010 9:03:32 AM
 */
public class CasPrintABC {
    
    public static void main(String[] args) {
        semaphoreA.set(true);
        
        new Thread(new PrintRunA(), "A").start();
        new Thread(new PrintRunB(), "B").start();
        new Thread(new PrintRunC(), "C").start();
    }
    
    /** mutex */
    private static final Lock PRINT_LOCK = new ReentrantLock();

    /** mutex for waiting print */
    private static final Condition PRINT_CONDITION = PRINT_LOCK.newCondition();
    
    /** total print times */
    private static final int TOTAL_PRINT_COUNT = 10;

    /** if semaphoreA is true thread A can print */
    private static AtomicBoolean semaphoreA = new AtomicBoolean(false);
    /** if semaphoreB is true thread B can print */
    private static AtomicBoolean semaphoreB = new AtomicBoolean(false);
    /** if semaphoreC is true thread C can print */
    private static AtomicBoolean semaphoreC = new AtomicBoolean(false);

    /**
     * Thread A print "A"
     */
    private static class PrintRunA implements Runnable {
        /** print times */
        private int printCount = TOTAL_PRINT_COUNT;
        
        @Override
        public void run() {
            while (printCount > 0) {
                PRINT_LOCK.lock();
                try {
                    try {
                        while(!semaphoreA.get()) {
                            PRINT_CONDITION.await();
                        }
                    } catch (InterruptedException e) {
                        System.err.println("PrintA InterruptedException");
                        PRINT_CONDITION.signalAll();
                    }
                } finally {
                    PRINT_LOCK.unlock();
                }
                
                
                semaphoreA.compareAndSet(true, false);
                
                /* do print */
                System.out.print(Thread.currentThread().getName());
                
                semaphoreB.compareAndSet(false, true);
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

    /**
     * Thread B print "B"
     */
    private static class PrintRunB implements Runnable {
        /** print times */
        private int printCount = TOTAL_PRINT_COUNT;
        
        @Override
        public void run() {
            while (printCount > 0) {
                PRINT_LOCK.lock();
                try {
                    try {
                        while(!semaphoreB.get()) {
                            PRINT_CONDITION.await();
                        }
                    } catch (InterruptedException e) {
                        System.err.println("PrintB InterruptedException");
                        PRINT_CONDITION.signalAll();
                    }
                } finally {
                    PRINT_LOCK.unlock();
                }
                
                
                semaphoreB.compareAndSet(true, false);
                
                /* do print */
                System.out.print(Thread.currentThread().getName());
                
                semaphoreC.compareAndSet(false, true);
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

    /**
     * Thread C print "C"
     */
    private static class PrintRunC implements Runnable {
        /** print times */
        private int printCount = TOTAL_PRINT_COUNT;
        
        @Override
        public void run() {
            while (printCount > 0) {
                PRINT_LOCK.lock();
                try {
                    try {
                        while(!semaphoreC.get()) {
                            PRINT_CONDITION.await();
                        }
                    } catch (InterruptedException e) {
                        System.err.println("PrintC InterruptedException");
                        PRINT_CONDITION.signalAll();
                    }
                } finally {
                    PRINT_LOCK.unlock();
                }
                
                
                semaphoreC.compareAndSet(true, false);
                
                /* do print */
                System.out.print(Thread.currentThread().getName());
                
                semaphoreA.compareAndSet(false, true);
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

