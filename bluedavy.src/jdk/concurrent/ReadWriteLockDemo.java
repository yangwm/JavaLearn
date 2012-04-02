/**
 * 
 */
package jdk.concurrent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 到读锁调用lock方法时，如果没有线程持有写锁，就可获得读锁而无需阻塞等待。
 * 到写锁调用lock方法时，如果没有线程持有读锁或写锁，就可获得写锁而无需阻塞等待，否则就需要阻塞等待，因此写操作影响整体性能。
 * 
 * 读写锁分离，在读多写少的场景下可大幅度提升性能。
 * 使用ReentrantReadWriteLock耗时为328毫秒；改为使用ReentrantLock耗时为10281毫秒；
 * 
 * @author yangwm Aug 12, 2010 6:08:32 PM
 */
public class ReadWriteLockDemo {

    private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private static Lock writeLock = lock.writeLock();
    private static Lock readLock = lock.readLock();
    private static Map<String, String> maps = new HashMap<String, String>();
    private static CountDownLatch latch = new CountDownLatch(102);
    private static CyclicBarrier barrier = new CyclicBarrier(102);
    
    private static ReentrantLock reentrantLock = new ReentrantLock();

    static class WirteTask implements Runnable {
        @Override
        public void run() {
            try {
                barrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            try {
                writeLock.lock();
                
                maps.put("yangwm", "habby is baskball");
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                writeLock.unlock();
            }
            latch.countDown();
        }
    }
    
    static class ReadTask implements Runnable {
        @Override
        public void run() {
            try {
                barrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            try {
                readLock.lock();
                
                maps.get("yangwm");
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                readLock.unlock();
            }
            latch.countDown();
        }
    }
    
    static class ReentrantWirteTask implements Runnable {
        @Override
        public void run() {
            try {
                barrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            try {
                reentrantLock.lock();
                
                maps.put("yangwm", "habby is baskball");
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                reentrantLock.unlock();
            }
            latch.countDown();
        }
    }
    
    static class ReentrantReadTask implements Runnable {
        @Override
        public void run() {
            try {
                barrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            try {
                reentrantLock.lock();
                
                maps.get("yangwm");
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                reentrantLock.unlock();
            }
            latch.countDown();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        long beginTime = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            new Thread(new ReadTask()).start();
        }
        for (int i = 0; i < 2; i++) {
            new Thread(new WirteTask()).start();
        }
        latch.await();
        System.out.println("ReentrantReadWriteLock Consume time:" + (System.currentTimeMillis() - beginTime) + " ms");
        
        
        latch = new CountDownLatch(102);
        barrier = new CyclicBarrier(102);
        beginTime = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            new Thread(new ReentrantReadTask()).start();
        }
        for (int i = 0; i < 2; i++) {
            new Thread(new ReentrantWirteTask()).start();
        }
        latch.await();
        System.out.println("ReentrantLock Consume time:" + (System.currentTimeMillis() - beginTime) + " ms");
    }

}

/*
ReentrantReadWriteLock Consume time:328 ms
ReentrantLock Consume time:10281 ms

*/

