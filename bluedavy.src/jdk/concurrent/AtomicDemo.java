/**
 * 
 */
package jdk.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * atomic操作类（基于CAS的数据结构），CAS由硬件提供原子操作指令实现的（CPU原语）。
 * CAS方式耗时为156毫秒， synchronized方式耗时为313毫秒。
 * 
 * @author yangwm Aug 12, 2010 4:45:06 PM
 */
public class AtomicDemo {
    
    private static int id = 0;
    private static AtomicInteger atomicId = new AtomicInteger();
    private static CountDownLatch latch = null;
    
    public synchronized static int getNextId() {
        return ++id;
    }
    
    public static int getNextIdWithAtomic() {
        return atomicId.incrementAndGet();
    }

    static class Task implements Runnable {
        private boolean isAtomic;

        public Task(boolean isAtomic) {
            this.isAtomic = isAtomic;
        }

        @Override
        public void run() {
            if (isAtomic) {
                for (int i = 0; i < 1000; i++) {
                    getNextIdWithAtomic();
                }
            } else {
                for (int i = 0; i < 1000; i++) {
                    getNextId();
                }
            }
            latch.countDown();
        }
    }

    
    public static void main(String[] args) throws InterruptedException {
        latch = new CountDownLatch(50);
        long beginTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            new Thread(new Task(false)).start();
        }
        latch.await();
        System.out.println("Synchronized style consume time:" + (System.currentTimeMillis() - beginTime) + " ms");
        
        latch = new CountDownLatch(50);
        beginTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            new Thread(new Task(true)).start();
        }
        latch.await();
        System.out.println("         CAS style consume time:" + (System.currentTimeMillis() - beginTime) + " ms");
    }

}

/*
Synchronized style consume time:313 ms
         CAS style consume time:156 ms
*/

