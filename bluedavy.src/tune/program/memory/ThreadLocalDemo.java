/**
 * 
 */
package tune.program.memory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 释放不必要的引用：代码持有了不需要的对象引用，造成这些对象无法被GC，从而占据了JVM堆内存。
 * （使用ThreadLocal：注意在线程内动作执行完毕时，需执行 ThreadLocal.set把对象清除，避免持有不必要的对象引用）
 * 
 * @author yangwm Aug 24, 2010 11:29:59 AM
 */
public class ThreadLocalDemo {
    
    public static void main(String[] args) {
        ThreadLocalDemo demo = new ThreadLocalDemo();
        demo.run();
    }
    
    public void run() {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        executor.execute(new Task());
        System.gc();
    }
    
    class Task implements Runnable {
        @Override
        public void run() {
            ThreadLocal<byte[]> localString = new ThreadLocal<byte[]>();
            localString.set(new byte[1024 * 1024 * 30]);
            
            // 业务逻辑 
            
            //localString.set(null); // 释放不必要的引用 
        }
    }

}
