/**
 * 
 */
package concurrent.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * 原子类使用的精髓－－CAS操作, 是硬件原生实现的，极为轻量级的无锁同步方式
 * 
 * @author yangwm Jul 23, 2010 11:28:52 AM
 */
public class AtomicCounterSample {
    public static void main(String[] args) throws Exception {
        final CountDownLatch end = new CountDownLatch(10);
        
        final AtomicCounter atomicCounter = new AtomicCounter();
        //final AtomicCounter2 atomicCounter = new AtomicCounter2();

        for (int i = 0; i < 5000; i++) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        atomicCounter.counterIncrement();
                    } finally {
                        end.countDown();
                    }
                }
            }).start();
        }

        end.await();
        System.out.println("counter=" + atomicCounter.getCounter());
    }
}

class AtomicCounter {
    private AtomicInteger counter = new AtomicInteger(0);

    public int getCounter() {
        return counter.get();
    }

    public void counterIncrement() {
        for (;;) {
            int current = counter.get();
            int next = current + 1;
            if (counter.compareAndSet(current, next)) {
                return;
            }
        }
    }
}

class AtomicCounter2 {
    private volatile int counter;
    private static final AtomicIntegerFieldUpdater<AtomicCounter2> counterUpdater = AtomicIntegerFieldUpdater
            .newUpdater(AtomicCounter2.class, "counter");

    public int getCounter() {
        return counter;
    }

    public int counterIncrement() {
        //return counter++; // 非线程安全的代码，常出现<5000的数字
        return counterUpdater.getAndIncrement(this);
    }
}
