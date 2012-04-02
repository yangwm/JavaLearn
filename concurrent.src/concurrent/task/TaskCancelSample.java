/**
 * 
 */
package concurrent.task;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;

/**
 * 
 * 
 * @author yangwm Oct 28, 2011 10:34:02 PM
 */
public class TaskCancelSample {

    /**
     * @param args
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newCachedThreadPool();
        
        Callable<Object> task = new SimpleACallable(1L);
        final Future<Object> future = executor.submit(task);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    future.get();
                    System.out.println("thread future.isCancelled():" + future.isCancelled() + ", future.isDone():" + future.isDone());
                } catch (InterruptedException e) {
                    //e.printStackTrace();
                } catch (ExecutionException e) {
                    //e.printStackTrace();
                }
            }
        }).start();
        
        future.cancel(true);
        System.out.println("future.isCancelled():" + future.isCancelled() + ", future.isDone():" + future.isDone());
    }


}

class SimpleACallable implements Callable<Object> {
    /**
     * Logger for this class
     */
    private static final Logger log = Logger.getLogger(SimpleCallable.class);

    private final Long id;
    
    public SimpleACallable(Long id) {
        this.id = id;
    }
    
    @Override
    public Object call() {
        log.debug(id + ", call begin");
        
        try {
            log.debug(id + ",  mill1 ");
            Thread.sleep(5000);
            log.debug(id + ",  mill2 ");
            Thread.sleep(5000);
            log.debug(id + ",  mill3 ");
        } catch (InterruptedException e) {
            //e.printStackTrace();
            //Thread.currentThread().interrupt();
        }
        
        log.debug(id + ",  call end");
        return null;
    }

    public Long getId() {
        return id;
    }
    
}