/**
 * 
 */
package concurrent.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * 
 * @author yangwm Jul 23, 2010 11:00:54 AM
 */
public class ThreadLocalSample {
    public static void main(String[] args) {
        // The shared Object for threads.
        //final OperationSample operationSample = new OperationSample();
        //final OperationSample2 operationSample = new OperationSample2();
        final OperationSample3 operationSample = new OperationSample3();

        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    operationSample.printAndIncrementNum();
                }
            }).start();
        }
    }
}

class OperationSample {
    private int num;

    // public synchronized void printAndIncrementNum() {
    public void printAndIncrementNum() {
        for (int i = 0; i < 2; i++) {
            System.out.println(Thread.currentThread().getName() + "[id=" + num + "]");
            num += 10;
        }
    }
}

class OperationSample2 {

    private static ThreadLocal<Integer> threadArg = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };

    public void printAndIncrementNum() {
        for (int i = 0; i < 2; i++) {
            int num = threadArg.get();
            threadArg.set(num + 10);
            System.out.println(Thread.currentThread().getName() + "[id=" + num + "]");
        }
    }
}

class OperationSample3 {

    private static final AtomicInteger uniqueId = new AtomicInteger(0);
    private static ThreadLocal<Integer> threadArg = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return uniqueId.getAndIncrement();
        }
    };

    public void printAndIncrementNum() {
        for (int i = 0; i < 2; i++) {
            int num = threadArg.get();
            threadArg.set(num + 10);
            System.out.println(Thread.currentThread().getName() + "[id=" + num + "]");
        }
    }
}
