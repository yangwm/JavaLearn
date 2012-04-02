/**
 * 
 */
package concurrent.lock;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

/**
 * 
 * http://www.blogjava.net/BucketLi/archive/2010/09/30/333471.html
 * 
 * @author yangwm Jan 15, 2012 11:02:12 PM
 */
public class FIFOMutex {
    private AtomicBoolean locked = new AtomicBoolean(false);
    private Queue<Thread> waiters = new ConcurrentLinkedQueue<Thread>();

    public void lock() {
        boolean wasInterrupted = false;
        Thread current = Thread.currentThread();
        waiters.add(current);

        // 如果waiters的第一个等待者不为当前线程，或者当前locked的状态为被占用(true)
        // 那么park住当前线程
        while (waiters.peek() != current || !locked.compareAndSet(false, true)) {
            LockSupport.park();

            // 当线程被unpark时，第一时间检查当前线程是否被interrupted
            if (Thread.interrupted()) {
                wasInterrupted = true;
            }
        }

        // 得到锁后，从等待队列移除当前线程，如果，并且如果当前线程已经被interrupted，
        // 那么再interrupt一下以便供外部响应。
        waiters.remove();
        if (wasInterrupted) {
            current.interrupt();
        }
    }

    // unlock逻辑相对简单，设定当前锁为空闲状态，并且将等待队列中
    // 的第一个等待线程唤醒
    public void unlock() {
        locked.set(false);
        LockSupport.unpark(waiters.peek());
    }
}
