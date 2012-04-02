/**
 * 
 */
package concurrent.join;

/**
 * join方法，在简单的多线程编程中，常常使用它来控制线程的执行顺序，也仅仅是一小部分的作用。
 * 它的内部实现是wait(0)直至被调用线程执行完毕，调用线程才被唤醒。
 * 
 * @author yangwm Jul 22, 2010 6:12:17 PM
 */
public class JoinSample {
    public static void main(String[] args) throws Exception {
        Thread t = new Thread(new SubThread());
        t.start();
        t.join();
        System.out.println("The end of main thread...");
    }
}

class SubThread implements Runnable {
    public void run() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("The end of sub thread...");
    }
}
