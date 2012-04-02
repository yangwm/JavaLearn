/**
 * 
 */
package concurrent.latch;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch最重要的方法是countDown()和await()，
 * 前者主要是倒数一次，后者是等待倒数到0，如果没有到达0，就只有阻塞等待了。
 * 
 * 使用场景：
 * 1. 当你启动了一个线程，你需要等它执行结束。 
 * 2. 当你启动很多线程，你需要这些线程等到通知后才真正开始。
 * 
 * @author yangwm Apr 12, 2010 10:25:00 AM
 */
public class CountDownLatchTest {
    
    public void countDownLatchExa() throws InterruptedException  
    {  
        final CountDownLatch countDownBegin = new CountDownLatch(1);  
        final CountDownLatch countDownEnd = new CountDownLatch(2);  
        Thread threadA = new Thread()  
        {  
            @Override  
            public void run()  
            {  
                try  
                {  
                    countDownBegin.await();  
                    System.out.println("threadA");  
                }  
                catch (InterruptedException e)  
                {  
                    e.printStackTrace();  
                }  
                finally  
                {  
                    countDownEnd.countDown();  
                }  
            }  
        };  
        Thread threadB = new Thread()  
        {  
            @Override  
            public void run()  
            {  
                try  
                {  
                    countDownBegin.await();  
                    System.out.println("threadB");  
                }  
                catch (InterruptedException e)  
                {  
                    e.printStackTrace();  
                }  
                finally  
                {  
                    countDownEnd.countDown();  
                }  
            }  
        }; 
        
        System.out.println("countDownLatchExa-X");  
        threadA.start();  
        threadB.start();  
        countDownBegin.countDown();  
        countDownEnd.await();  
        System.out.println("countDownLatchExa-Y");  
    }
    
    
    /**
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        CountDownLatchTest test = new CountDownLatchTest();
        test.countDownLatchExa(); 
    }
    
}

/*
countDownLatchExa-X
threadA
threadB
countDownLatchExa-Y

*/

