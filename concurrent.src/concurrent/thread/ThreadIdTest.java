/**
 * 
 */
package concurrent.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * 
 * @author yangwm Jul 23, 2010 10:30:07 AM
 */
public class ThreadIdTest {
    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread() {
                @Override
                public void run() {
                    System.out.println(ThreadId.get());
                }
            }.start();/**/
            
            /*new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(ThreadId.get());
                }
            }).start();*/
        }
    }

}
/*
4
0
1
2
3
*/

class ThreadId {
    // Atomic integer containing the next thread ID to be assigned
    private static final AtomicInteger nextId = new AtomicInteger(0);

    // Thread local variable containing each thread's ID
    private static final ThreadLocal<Integer> threadId =
        new ThreadLocal<Integer>() {
            @Override protected Integer initialValue() {
                return nextId.getAndIncrement();
        }
    };

    // Returns the current thread's unique ID, assigning it if necessary
    public static int get() {
        return threadId.get();
    }
}
