/**
 * 
 */
package concurrent.sample.print;


/**
 * three Thread A, B C; 
 * Thread A print "A"
 * Thread B print "B"
 * Thread C print "C"
 * 
 * like this ABC...ABC(Total 10 ABC)
 * 
 * 小结： synchronized+volatile的简单实现
 * 
 * @author yangwm Jul 28, 2010 9:17:14 PM
 */
public class PrintABC {
    
    public static void main(String[] args) {
        semaphoreA = true;
        
        new Thread(new PrintRunA(), "A").start();
        new Thread(new PrintRunB(), "B").start();
        new Thread(new PrintRunC(), "C").start();
    }
    
    /** mutex */
    private static final Object LOCK = new Object();
    
    /** total print times */
    private static final int TOTAL_PRINT_COUNT = 10;
    
    /** if semaphoreA is true thread A can print */
    private static volatile boolean semaphoreA = false;
    /** if semaphoreB is true thread B can print */
    private static volatile boolean semaphoreB = false;
    /** if semaphoreC is true thread C can print */
    private static volatile boolean semaphoreC = false;

    /**
     * Thread A print "A"
     */
    private static class PrintRunA implements Runnable {
        /** print times */
        private int printCount = TOTAL_PRINT_COUNT;

        @Override
        public void run() {
            synchronized (LOCK) {
                while (printCount > 0) {
                    try {
                        while (!semaphoreA) {
                            LOCK.wait();
                        }
                    } catch (InterruptedException e) {
                        System.err.println("PrintA InterruptedException");
                        LOCK.notifyAll();
                    }
                    
                    
                    semaphoreA = false;
                    
                    /* do print */
                    System.out.print(Thread.currentThread().getName());
                    
                    semaphoreB = true;
                    printCount--;
                    LOCK.notifyAll();
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
            synchronized (LOCK) {
                while (printCount > 0) {
                    try {
                        while (!semaphoreB) {
                            LOCK.wait();
                        }
                    } catch (InterruptedException e) {
                        System.err.println("PrintB InterruptedException");
                        LOCK.notifyAll();
                    }
                    
                    
                    semaphoreB = false;
                    
                    /* do print */
                    System.out.print(Thread.currentThread().getName());
                    
                    semaphoreC = true;
                    printCount--;
                    LOCK.notifyAll();
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
            synchronized (LOCK) {
                while (printCount > 0) {
                    try {
                        while (!semaphoreC) {
                            LOCK.wait();
                        }
                    } catch (InterruptedException e) {
                        System.err.println("PrintC InterruptedException");
                        LOCK.notifyAll();
                    }
                    
                    
                    semaphoreC = false;
                    
                    /* do print */
                    System.out.print(Thread.currentThread().getName());
                    
                    semaphoreA = true;
                    printCount--;
                    LOCK.notifyAll();
                }
            }
        }
    }

}

/*
ABCABCABCABCABCABCABCABCABCABC

*/

