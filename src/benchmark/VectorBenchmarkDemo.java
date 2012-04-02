/**
 * 
 */
package benchmark;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 启动测试：java -server -classpath "benchmarkDemo.jar"  benchmark.VectorBenchmarkDemo ${threadCount} ${executeTimes}
 * 测试规则：同样线程数，以tps高 的获胜（tps基本一样以load低获胜）
 * 
 * 
 * @author yangwm Nov 27, 2011 4:16:57 PM
 */
public class VectorBenchmarkDemo {
    
    public static AtomicInteger count = new AtomicInteger();
    public static AtomicInteger errorCount = new AtomicInteger();
    private static List<Integer> elapseTime = new Vector<Integer>();

    private static int threadCount = 20;
    private static int executeTimes = 1 * 1000;
    
    private static CountDownLatch startLatch = new CountDownLatch(1);
    private static CountDownLatch endLatch;

    public static void main(String[] args) throws Exception {
        threadCount = Runtime.getRuntime().availableProcessors();
        if (args.length == 1) {
            threadCount = Integer.parseInt(args[0]);
        } else if (args.length > 1) {
            executeTimes = Integer.parseInt(args[1]);
        }
        
        endLatch = new CountDownLatch(threadCount);
        System.out.println("VectorBenchmarkDemo begin Vectortask thread count:" + threadCount + 
                " vector executeTimes: " + executeTimes);
        warmUp();
        
        runTest();
    }

    private static void runTest() throws Exception{
        System.out.println("runTest begin");
        for (int i = 0; i < threadCount; i++) {
            new Thread(new VectorTask()).start();
        }
        
        startLatch.countDown();
        long start = System.currentTimeMillis();
        endLatch.await();
        long duration = System.currentTimeMillis() - start;
        TestPrintUtil.printStat(count, errorCount, elapseTime, duration);
        
        System.out.println("VectorBenchmarkDemo end summary consumeTime: " 
                + duration + " ms"  + ", for Per Thread consumeTime: " + (duration / threadCount) + " ms");
    }
    
    private static void warmUp() throws Exception {
        for (int t = 0; t < executeTimes; t++) {
            test();
        }
        
        System.out.println("warm up, time:" + new Date());
    }
    
    static class VectorTask implements Runnable {
        public void run() {
            try {
                startLatch.await();
                
                for (int t = 0; t < executeTimes; t++) {
                    try {
                        long n1 = System.nanoTime();
                        
                        test();
                        count.incrementAndGet();
                        
                        long n2 = System.nanoTime();
                        //System.out.println(", " + (int)(n2 - n1) / (1000 * 1000));
                        elapseTime.add((int)(n2 - n1) / (1000 * 1000));
                    } catch (Exception e) {
                        errorCount.incrementAndGet();
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                endLatch.countDown();
            }
            
        }

    }
    
    public static void test() {
        long[] ids = BenchmarkUtil.getIdsFrmVector(BenchmarkUtil.MAX_FRIEND_COUNT); // new long[]{1};
        Arrays.sort(ids);
        long[] pageIds = BenchmarkUtil.paginationAndReverse(ids, ids.length, -1, -1, 0, 1);
        int l = pageIds.length;
    }

}
