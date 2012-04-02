/**
 * 
 */
package tedneward.things.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * SynchronousQueue
 * 这是一个阻塞队列，其中，每个插入操作必须等待另一个线程的对应移除操作，反之亦然。
 * 一个同步队列不具有任何内部容量，甚至不具有 1 的容量。 
 * 
 * @author yangwm Jul 26, 2010 4:31:27 PM
 */
public class SynQApp {
    public static void main(String[] args) {
        BlockingQueue<String> drop = new SynchronousQueue<String>();
        (new Thread(new Producer(drop))).start();
        (new Thread(new Consumer(drop))).start();
    }
}
/*
Mares eat oats
Does eat oats
Little lambs eat ivy
Wouldn't you eat ivy too?

*/
