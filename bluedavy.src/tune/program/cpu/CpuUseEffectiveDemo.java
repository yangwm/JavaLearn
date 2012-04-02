/**
 * 
 */
package tune.program.cpu;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;


/**
 * 充分利用CPU：在能并行处理的场景中使用足够的线程（线程增加：CPU资源消耗可接受且不会带来激烈竞争锁的场景下）
 * 
 * @author yangwm Aug 25, 2010 9:54:50 AM
 */
public class CpuUseEffectiveDemo {
    
    private static int executeTimes = 10;
    private static int taskCount = 200;
    private static final int TASK_THREADCOUNT = 16;
    private static CountDownLatch latch;
    
    public static void main(String[] args) throws Exception {
        Task[] tasks = new Task[TASK_THREADCOUNT];
        for (int i = 0; i < TASK_THREADCOUNT; i++) {
            tasks[i] = new Task();
        }
        for (int i = 0; i < taskCount; i++) {
            int mod = i % TASK_THREADCOUNT;
            tasks[mod].addTask(Integer.toString(i));
        }
        
        long beginTime = System.currentTimeMillis();
        for (int i = 0; i < executeTimes; i++) {
            System.out.println("Round: " + (i + 1));
            latch = new CountDownLatch(TASK_THREADCOUNT);
            for (int j = 0; j < TASK_THREADCOUNT; j++) {
                Thread thread = new Thread(tasks[j]);
                thread.start();
            }
            latch.await();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Execute summary: Round( " + executeTimes + " ) TaskCount Per Round( " + taskCount 
                + " ) Execute Time ( " + (endTime - beginTime) + " ) ms");
    }
    
    static class Task implements Runnable {
        List<String> tasks = new ArrayList<String>();
        Random random = new Random();
        boolean exitFlag = false;
        
        public void addTask(String task) {
            List<String> copyTasks = new ArrayList<String>(tasks);
            copyTasks.add(task);
            
            tasks = copyTasks;
        }

        @Override
        public void run() {
            List<String> runTasks = tasks;
            List<String> removeTasks = new ArrayList<String>();
            for (String task : runTasks) {
                try {
                    Thread.sleep(random.nextInt(10));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
                removeTasks.add(task);
            }
            
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
            latch.countDown();
        }
        
    }
    
}

/*
Round: 1
......
Round: 10
Execute summary: Round( 10 ) TaskCount Per Round( 200 ) Execute Time ( 938 ) ms

*/
