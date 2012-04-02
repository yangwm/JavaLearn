/**
 * 
 */
package benchmark;

import java.util.Random;

/**
 * 
 * 启动测试：java -server -classpath "benchmarkDemo.jar"  benchmark.CpuSyBenchmarkDemo ${threadCount} 
 * 测试规则：top看load, 在负载稳定情况下，负载低、线程数多的获胜    
 * 
 * 当sy 值高时，表示系统调用耗费了较多的CPU，对于Java 应用程序而言，造成这种现象的主要原因是启动的线程比较多，
 * 并且这些线程多数都处于不断的等待（例如锁等待状态）和执行状态的变化过程中，
 * 这就导致了操作系统要不断的调度这些线程，切换执行。
 * 
 * @author yangwm Nov 10, 2011 2:46:51 PM
 */
public class CpuSyBenchmarkDemo {
    private static int threadCount = 500;

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        if (args.length == 1) {
            threadCount = Integer.parseInt(args[0]);
        }

        System.out.println("CpuSyBenchmarkDemo begin, task count:" + threadCount);
        CpuSyBenchmarkDemo demo = new CpuSyBenchmarkDemo();
        demo.runTest();
    }
    
    private Random random = new Random();
    private Object[] locks;
    
    private void runTest() throws Exception {
        locks = new Object[threadCount];
        
        for (int i = 0; i < threadCount; i++) {
            locks[i] = new Object();
        }
        
        for (int i = 0; i < threadCount; i++) {
            new Thread(new ATask(i)).start();
            new Thread(new BTask(i)).start();
        }
    }
    

    class ATask implements Runnable {
        private Object lockObject = null;
        
        public ATask(int i) {
            lockObject = locks[i];
        }
        
        public void run() {
            while (true) {
                try {
                    synchronized (lockObject) {
                        lockObject.wait(random.nextInt(10));
                    }
                } catch (Exception e) {
                    ;
                }
            }
        }
    }
    
    class BTask implements Runnable {
        private Object lockObject = null;
        
        public BTask(int i) {
            lockObject = locks[i];
        }
        
        public void run() {
            while (true) {
                synchronized (lockObject) {
                    lockObject.notifyAll();
                }
                
                try {
                    Thread.sleep(random.nextInt(5));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
