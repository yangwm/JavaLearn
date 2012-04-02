/**
 * 
 */
package concurrent.latch;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * CountDownLatch Example
 * 
 * @author yangwm Dec 5, 2011 9:59:34 PM
 */
public class CountDownLatchExample { 

    /**
     * @param args
     * @throws InterruptedException 
     * @throws TimeoutException 
     */
    public static void main(String[] args) throws InterruptedException, TimeoutException {
        new CountDownLatchExample().test();
    }
    
    private long opTimeout = 1000L; // 10000L 
    
    public void test() throws InterruptedException, TimeoutException {
        final TestCommand cmd = new TestCommand();
        Thread threadA = new Thread() {
            @Override
            public void run() {
                int sleepTime = new Random().nextInt((int)opTimeout * 2);
                try { Thread.sleep(sleepTime); } catch (InterruptedException e) { e.printStackTrace(); }
                cmd.countDown();
                System.out.println("threadA cmd.countDown sleepTime:" + sleepTime);
            }
        };
        threadA.start();
        
        awaitResponse(cmd);
        System.out.println("test end currentTime:" + System.currentTimeMillis());
    }
    
    private void awaitResponse(TestCommand cmd) throws InterruptedException, TimeoutException {
        System.out.println("awaitResponse begin currentTime:" + System.currentTimeMillis());
        if (!cmd.await(opTimeout, TimeUnit.MILLISECONDS)) {
            throw new TimeoutException("Operation timeout in " + opTimeout + " ms.");
        }
        System.out.println("awaitResponse end currentTime:" + System.currentTimeMillis());
    }
    
    class TestCommand {
        private final CountDownLatch latch;
        
        public TestCommand() {
            super();
            this.latch = new CountDownLatch(1);
        }

        public boolean await(long timeout, TimeUnit unit)
                throws InterruptedException {
            return this.latch.await(timeout, unit);
        }
        
        public void countDown() {
            this.latch.countDown();
        }
    }

}

/*
1. 
awaitResponse begin currentTime:1323103212259
threadA cmd.countDown sleepTime:744
awaitResponse end currentTime:1323103213005
test end currentTime:1323103213005

2. 
awaitResponse begin currentTime:1323103228378
Exception in thread "main" java.util.concurrent.TimeoutException: Operation timeout in 1000 ms.
    at concurrent.latch.CountDownLatchExample.awaitResponse(CountDownLatchExample.java:49)
    at concurrent.latch.CountDownLatchExample.test(CountDownLatchExample.java:42)
    at concurrent.latch.CountDownLatchExample.main(CountDownLatchExample.java:24)
threadA cmd.countDown sleepTime:1375

*/

