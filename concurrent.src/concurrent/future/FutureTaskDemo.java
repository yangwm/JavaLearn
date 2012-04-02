/**
 * 
 */
package concurrent.future;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * Piggybacking on Synchronization
 * 
 * @author yangwm Feb 25, 2011 3:56:26 PM
 */
public class FutureTaskDemo {
    
    private final ConcurrentMap<String, Future<Object>> cache = new ConcurrentHashMap<String, Future<Object>>();

    public Object get(final String key) throws InterruptedException {
        while (true) {
            Future<Object> f = cache.get(key);
            if (f == null) {
                Callable<Object> eval = new Callable<Object>() {
                    public String call() throws InterruptedException {
                        String result = null;
                        /*
                         * get value from memCache
                         */
                        // result = memCache.get(key) 
                        
                        return result;
                    }
                };
                FutureTask<Object> ft = new FutureTask<Object>(eval);
                f = cache.putIfAbsent(key, ft);
                if (f == null) {
                    f = ft;
                    ft.run();
                }
            }
            try {
                return f.get();
            } catch (CancellationException e) {
                cache.remove(key, f);
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
