/**
 * 
 */
package guava.concurrent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

/**
 * 
 * 
 * @author yangwm Mar 19, 2012 1:04:39 AM
 */
public class ConcurrentSample {

    /**
     * @param args
     * @throws InterruptedException 
     * @throws ExecutionException 
     */
    public static void main(String[] args) {
        ExecutorService rawThreadPool = Executors.newFixedThreadPool(10);
        ListeningExecutorService service = MoreExecutors.listeningDecorator(rawThreadPool);
        
        long[] uids = {501, 405, 108};
        final Map<Long, String> results = new HashMap<Long, String>();
        
        List<Callable<String>> tasks = new ArrayList<Callable<String>>();
        for(final long uid : uids){
            Callable<String> task = new Callable<String>() {
                @Override
                public String call() throws Exception {
                    Thread.sleep(uid);
                    return uid + " success";
                }
            };
            tasks.add(task);
        }
        List<Future<String>> futures;
        try {
            futures = service.invokeAll(tasks, 500, TimeUnit.MILLISECONDS);
            int i = 0;
            for (Future<String> future : futures) {
                try {
                    results.put(uids[i++], future.get());
                } catch (Exception e) {
                    System.err.println(e.getLocalizedMessage());
                }
                //results.put(key, future.get());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println(results);
        
        /*
        for(final long uid : uids){
            ListenableFuture<String> result = service.submit(new Callable<String>() {
                public String call() {
                    return String.valueOf(uid);
                }
            });
            results.put(uid, result.get());
        }
        */
        
        rawThreadPool.shutdown();
    }

}
