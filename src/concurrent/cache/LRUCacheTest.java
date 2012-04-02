/**
 * 
 */
package concurrent.cache;

import java.util.Map;

/**
 * 
 * 
 * @author yangwm Mar 20, 2011 5:37:34 PM
 */
public class LRUCacheTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        int size = 10;
        long lifetime = 3 * 1000;
        Map<Integer, Integer> map = new CopyOnWriteLRUHashCache<Integer, Integer>(size, lifetime);
        for (int i = 1; i <= 15; i++) {
            map.put(i, i);
        }
        for (int i = 15; i >= 1; i--) {
            map.put(i, i);
        }
        System.out.println("map.size (" + map.size() + ") CopyOnWrite LRU Cache ");
        for (int i = 1; i <= 15; i++) {
            System.out.println(i + ", " + map.get(i));
        }
        System.out.println("map.size (" + map.size() + ") CopyOnWrite LRU Cache ");
        
        try {
            Thread.sleep(lifetime + 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("map.size (" + map.size() + ") CopyOnWrite LRU Cache2 ");
        for (int i = 1; i <= 15; i++) {
            System.out.println(i + ", " + map.get(i));
        }
        System.out.println("map.size (" + map.size() + ") CopyOnWrite LRU Cache2 ");
        
        map = new CopyOnWriteLRUHashCache<Integer, Integer>(size, lifetime);
        for (int i = 1; i <= 15; i++) {
            map.put(i, i);
        }
        for (int i = 15; i >= 1; i--) {
            map.put(i, i);
        }
        System.out.println("map.size (" + map.size() + ") CopyOnWrite LRU Cache3 ");
        for (int i = 1; i <= 15; i++) {
            System.out.println(i + ", " + map.get(i));
        }
        System.out.println("map.size (" + map.size() + ") CopyOnWrite LRU Cache3 ");
    }

}
