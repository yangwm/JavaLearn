/**
 * 
 */
package concurrent.latch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 简单的说明了CountDownLatch的使用方法，模拟了100米赛跑，10名选手已经准备就绪，只等裁判一声令下。
 * 当所有人都到达终点时，比赛结束。
 * 
 * 使用场景：
 * 1. 当你启动了一个线程，你需要等它执行结束。 
 * 2. 当你启动很多线程，你需要这些线程等到通知后才真正开始。
 * 
 * @author yangwm Apr 12, 2010 10:39:02 AM
 */
public class TestCountDownLatch {

    /**
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        // 开始的倒数锁
        final CountDownLatch begin = new CountDownLatch(1);
        // 结束的倒数锁
        final CountDownLatch end = new CountDownLatch(10);
        // 十名选手
        final ExecutorService exec = Executors.newFixedThreadPool(10);
        for(int index = 0; index < 10; index++) {
          final int NO = index + 1;
          Runnable run = new Runnable(){
            public void run() {
              try {
                begin.await();
                Thread.sleep((long) (Math.random() * 10000));
                System.out.println("No." + NO + " arrived");
              } catch (InterruptedException e) {
              } finally {
                end.countDown();
              }
            }
          };
          exec.submit(run);
        }
        System.out.println("Game Start");
        begin.countDown();
        end.await();
        System.out.println("Game Over");
        exec.shutdown();
    }

}
