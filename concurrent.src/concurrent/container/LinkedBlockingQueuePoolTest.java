/**
 * 
 */
package concurrent.container;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 测试LinkedBlockingQueue.poll超时的程序, 很不准的现象
 * http://www.blogjava.net/BlueDavy/archive/2009/03/12/259376.html
 * 
 * @author yangwm Jul 22, 2010 10:39:59 AM
 */
public class LinkedBlockingQueuePoolTest {
    
    public static void main(String[] args) throws Exception {
        long beginTime = System.currentTimeMillis();
        Map<String, byte[]> cache = new HashMap<String, byte[]>();
        for (int i = 0; i < 1000000; i++) {
            cache.put(String.valueOf(i), new byte[500]);
        }
        CountDownLatch latch = new CountDownLatch(25);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Thread thread = new Thread(new TestThread(latch));
                thread.start();
            }
        }
        latch.await();
        long endTime = System.currentTimeMillis();
        System.out.println("总共执行时间：" + (endTime - beginTime));
    }

    static class TestThread implements Runnable {

        private CountDownLatch latch;

        public TestThread(CountDownLatch latch) {
            this.latch = latch;
        }

        public void run() {
            BlockingQueue<String> queue = new LinkedBlockingQueue<String>(1);
            try {
                for (int i = 0; i < 5; i++) {
                    long beginTime = System.currentTimeMillis();
                    queue.poll(100, TimeUnit.MILLISECONDS);
                    long endTime = System.currentTimeMillis();
                    long consumeTime = endTime - beginTime;
                    if (consumeTime > 200) {
                        System.out.println("获取queue的时间为：" + consumeTime);
                    }
                    @SuppressWarnings("unused")
                    byte[] bytesNew = new byte[25506000];
                }
            } catch (Exception e) {
                ;
            }
            latch.countDown();
        }

    }
}

/*
concurrent.container.LinkedBlockingQueuePoolTest -Xms640M -Xmx640M

获取queue的时间为：234
获取queue的时间为：203
获取queue的时间为：203
获取queue的时间为：344
获取queue的时间为：219
获取queue的时间为：203
获取queue的时间为：203
获取queue的时间为：235
获取queue的时间为：203
获取queue的时间为：203
获取queue的时间为：203
获取queue的时间为：203
获取queue的时间为：250
获取queue的时间为：203
获取queue的时间为：234
获取queue的时间为：203
获取queue的时间为：203
总共执行时间：8859

*/

