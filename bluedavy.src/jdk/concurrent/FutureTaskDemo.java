/**
 * 
 */
package jdk.concurrent;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.ReentrantLock;

import util.ConnectionUtil;

/**
 * ConcurrentHashMap + FutureTask使得create Connection这种耗时操作并行化（异步获取执行结果）。
 * HashMap + lock耗时为187毫秒， ConcurrentHashMap + FutureTask耗时为3172毫秒。
 * 
 * @author yangwm Aug 12, 2010 5:56:41 PM
 */
public class FutureTaskDemo {
    
    static Map<String, Connection> connectionPool = new HashMap<String, Connection>();
    static ReentrantLock lock = new ReentrantLock();
    static CountDownLatch latch = null;
    
    /**
     * create Connection耗时100毫秒以上 
     * 
     * @return
     */
    public static Connection createConnection() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ConnectionUtil.getConnectionForPgsql();
    }

    public static Connection getConnection(String key) {
        try {
            lock.lock();
            if (connectionPool.containsKey(key)) {
                return connectionPool.get(key);
            } else {
                Connection connection = createConnection(); //create Connection
                connectionPool.put(key, connection);
                return connection;
            }
        } finally {
            lock.unlock();
        }
    }


    static ConcurrentMap<String, FutureTask<Connection>> connectionPoolFutureTask = new ConcurrentHashMap<String, FutureTask<Connection>>();

    public static Connection getConnectionFutureTask(String key) throws InterruptedException, ExecutionException {
        FutureTask<Connection> connectionTask = connectionPoolFutureTask.get(key);
        if (connectionTask != null) {
            return connectionTask.get();
        } else {
            Callable<Connection> callable = new Callable<Connection>() {
                    @Override
                    public Connection call() {
                        return createConnection();
                    }
                }; // create Connection
            FutureTask<Connection> newTask = new FutureTask<Connection>(callable);
            connectionTask = connectionPoolFutureTask.putIfAbsent(key, newTask);
            if (connectionTask == null) {
                connectionTask = newTask;
                connectionTask.run();
            }
            return connectionTask.get();
        }
    }

    
    static class GetConnectionRun implements Runnable {
        private int id;
        
        public GetConnectionRun(int id) {
            this.id = id;
        }
        
        @Override
        public void run() {
            FutureTaskDemo.getConnection("yangwm" + id);
            latch.countDown();
        }
    }
    
    static class GetConnectionFutureTaskRun implements Runnable {
        private int id;
        
        public GetConnectionFutureTaskRun(int id) {
            this.id = id;
        }
        
        @Override
        public void run() {
            try {
                FutureTaskDemo.getConnectionFutureTask("yangwm" + id);
                latch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int len = 30;
        long beginTime = 0;
        
        // 预热 一下 
        FutureTaskDemo.getConnectionFutureTask("abc");
        FutureTaskDemo.getConnection("abc");
        
        latch = new CountDownLatch(len);
        beginTime = System.currentTimeMillis();
        for (int i = 0; i < len; i++) {
            new Thread(new GetConnectionFutureTaskRun(i)).start();
        }
        latch.await();
        System.out.println("GetConnectionFutureTaskRun Consume time:" + (System.currentTimeMillis() - beginTime) + " ms");
        
        latch = new CountDownLatch(len);
        beginTime = System.currentTimeMillis();
        for (int i = 0; i < len; i++) {
            new Thread(new GetConnectionRun(i)).start();
        }
        latch.await();
        System.out.println("GetConnectionRun Consume time:" + (System.currentTimeMillis() - beginTime) + " ms");
        
    }
    
}

/*
GetConnectionFutureTaskRun Consume time:187 ms
GetConnectionRun Consume time:3172 ms

*/
