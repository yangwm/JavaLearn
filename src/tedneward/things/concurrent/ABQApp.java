/**
 * 
 */
package tedneward.things.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * ArrayBlockingQueue
 * 它为读取器和编写器提供线程先入先出访问。
 * 还支持有时间参数的方法，时间参数表明线程在返回信号故障以插入或者检索有关项之前需要阻塞的时间。
 * 
 * @author yangwm Jul 26, 2010 3:51:02 PM
 */
public class ABQApp {
    public static void main(String[] args) {
        BlockingQueue<String> drop = new ArrayBlockingQueue<String>(1, true);
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
