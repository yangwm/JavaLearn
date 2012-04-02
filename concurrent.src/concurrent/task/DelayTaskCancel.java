/**
 * 
 */
package concurrent.task;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

/**
 * Delay Task Cancel
 * 
 * @author yangwm Oct 29, 2010 10:40:04 AM
 */
public class DelayTaskCancel {
    /**
     * Logger for this class
     */
    private static final Logger log = Logger.getLogger(DelayTaskCancel.class);
    
    
    public static final ConcurrentMap<Long, ScheduledFuture<Object>> clearFutureMap = new ConcurrentHashMap<Long, ScheduledFuture<Object>>();

    /**
     * @param args
     */
    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);
        
        /*
         * add task 
         */
        for (long i = 1L; i <=5; i++) {
            final Long id = i;
            SimpleCallable simpleCallable = new SimpleCallable(id);
            ScheduledFuture<Object> future = scheduler.schedule(simpleCallable, 1, TimeUnit.SECONDS);
            clearFutureMap.put(simpleCallable.getId(), future);
        }
        log.debug("clearFutureMap:" + clearFutureMap.keySet());
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        /*
         * remove task 
         */
        for (long i = 2L; i <= 3; i++) {
            final Long id = i;
            final ScheduledFuture<Object> clearfuture = clearFutureMap.get(id);
            if (clearfuture == null || clearfuture.isCancelled() || clearfuture.isDone()) {
                // TODO add uid resoucce 
            } else if (clearfuture.getDelay(TimeUnit.NANOSECONDS) > 0) { // is not running 
                clearFutureMap.remove(id);
                clearfuture.cancel(true);
                // do nothing  
            } else { // is running 
                log.debug(id + ", clearfuture.get() begin");
                /*
                 * wait run completed 
                 */
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            clearfuture.get();
                            log.debug(id + ", clearfuture.get() end");
                            // TODO add uid resoucce 
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        }
        
        scheduler.shutdown();
    }
    
}

class SimpleCallable implements Callable<Object> {
    /**
     * Logger for this class
     */
    private static final Logger log = Logger.getLogger(SimpleCallable.class);

    private final Long id;
    
    public SimpleCallable(Long id) {
        this.id = id;
    }
    
    @Override
    public Object call() {
        log.debug(id + ", call begin");
        
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        
        DelayTaskCancel.clearFutureMap.remove(id);
        log.debug(id + ",  call begin end clearFutureMap:" + DelayTaskCancel.clearFutureMap.keySet());
        return null;
    }

    public Long getId() {
        return id;
    }
    
}

/*
17:34:45,781 [main] DEBUG [DelayTaskCancel.main(46)]  - clearFutureMap:[5, 2, 1, 3, 4]
17:34:46,781 [pool-1-thread-3] DEBUG [SimpleCallable.call(108)]  - 3, call begin
17:34:46,781 [pool-1-thread-1] DEBUG [SimpleCallable.call(108)]  - 1, call begin
17:34:46,781 [pool-1-thread-2] DEBUG [SimpleCallable.call(108)]  - 2, call begin
17:34:46,781 [pool-1-thread-4] DEBUG [SimpleCallable.call(108)]  - 4, call begin
17:34:46,781 [pool-1-thread-5] DEBUG [SimpleCallable.call(108)]  - 5, call begin
17:34:47,781 [main] DEBUG [DelayTaskCancel.main(68)]  - 2, clearfuture.get() begin
17:34:47,781 [main] DEBUG [DelayTaskCancel.main(68)]  - 3, clearfuture.get() begin
17:34:51,781 [pool-1-thread-1] DEBUG [SimpleCallable.call(117)]  - 1,  call begin end clearFutureMap:[5, 4]
17:34:51,781 [pool-1-thread-2] DEBUG [SimpleCallable.call(117)]  - 2,  call begin end clearFutureMap:[5, 4]
17:34:51,781 [pool-1-thread-3] DEBUG [SimpleCallable.call(117)]  - 3,  call begin end clearFutureMap:[5, 4]
17:34:51,781 [Thread-0] DEBUG [DelayTaskCancel$1.run(77)]  - 2, clearfuture.get() end
17:34:51,781 [pool-1-thread-4] DEBUG [SimpleCallable.call(117)]  - 4,  call begin end clearFutureMap:[]
17:34:51,781 [pool-1-thread-5] DEBUG [SimpleCallable.call(117)]  - 5,  call begin end clearFutureMap:[4]
17:34:51,781 [Thread-1] DEBUG [DelayTaskCancel$1.run(77)]  - 3, clearfuture.get() end

*/

