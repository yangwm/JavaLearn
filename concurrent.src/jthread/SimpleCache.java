/**
 * 
 */
package jthread;

import java.util.HashMap;
import java.util.Map;

/**
 * 简单的线程安全的高速缓存
 * 
 * @author yangwm in Nov 20, 2009 11:20:37 AM
 */
public class SimpleCache {
    private final Map<String, Object> cache = new HashMap<String, Object>();

    public Object load(String objectName) {
      // load the object somehow
      return objectName; 
    }

    public void clearCache() { 
      synchronized (cache) { 
        cache.clear();
      }
    }

    public Object getObject(String objectName) {
      Object o = null; 
        
      synchronized (cache) { 
        o = cache.get(objectName);
        if (o == null) {
          o = load(objectName);
          cache.put(objectName, o);
        }
      }

      return o;
    }

}
