/**
 * 
 */
package benchmark;

import java.util.Arrays;

/**
 * 启动测试：java -server -classpath "benchmarkDemo.jar" benchmark.CpuUsBenchmarkDemo 
 * 测试规则：top看load, 在负载稳定情况下，负载低的获胜    
 * 
 * 总结来说，当us 值高时，主要是由于启动的Java 线程一直在执行（例如循环执行），
 * 并且线程中所执行的步骤不太需要等待IO 或进入sleep、wait 等状态，
 * 又或者是启动的线程很多，当一个线程sleep、wait 后，其他的又在运行。
 * 
 * @author yangwm Nov 10, 2011 2:57:22 PM
 */
public class CpuUsBenchmarkDemo {
    private static int threadCount = 2;

    public static void main(String[] args) throws Exception {
        threadCount = Runtime.getRuntime().availableProcessors();
        if (args.length == 1) {
            threadCount = Integer.parseInt(args[0]);
        }
        
        System.out.println("CpuUsBenchmarkDemo begin, notConsume CPU task count:" + threadCount 
                + " consume CPU task count:" + (threadCount * 100));

        CpuUsBenchmarkDemo demo = new CpuUsBenchmarkDemo();
        demo.runTest();
    }
    
    private void runTest() throws Exception{
        for (int i = 0; i < threadCount; i++) {
            new Thread(new ConsumeCPUTask()).start();
        }
        for (int i = 0; i < threadCount * 100; i++) {
            new Thread(new NotConsumeCPUTask()).start();
        }
    }
    
    
    class ConsumeCPUTask implements Runnable{
        public void run() {
            float i=0.002f;
            float j=232.13243f;
        
            while (true) {
                j=i*j;
                long[] ids = BenchmarkUtil.getIdsFrmVector(BenchmarkUtil.MAX_FRIEND_COUNT);
                Arrays.sort(ids);
                long[] pageIds = BenchmarkUtil.paginationAndReverse(ids, ids.length, -1, -1, 0, 1);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    class NotConsumeCPUTask implements Runnable{
        public void run() {
            while (true) {
                try {
                    Thread.sleep(100000);
                    //Thread.sleep(10000000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
}

// consume cpu 52%--59%
