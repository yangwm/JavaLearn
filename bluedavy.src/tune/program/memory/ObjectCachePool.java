/**
 * 
 */
package tune.program.memory;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * 采用合理的缓存失效算法: FIFO、LRU、LFU等 
 * 
 * @author yangwm Aug 24, 2010 6:06:48 PM
 */
public class ObjectCachePool<K, V> {

    public static void main(String[] args) {
        // FIFO_POLICY
        int size = 10;
        int policy = 1;
        ObjectCachePool<Integer, Integer> objectCachePool = new ObjectCachePool<Integer, Integer>(size, policy);
        for (int i = 1; i <= 15; i++) {
            objectCachePool.put(i, i);
        }
        for (int i = 15; i >= 1; i--) {
            objectCachePool.put(i, i);
        }
        System.out.println("size(" + size + "), policy(" + policy + ") FIFO ");
        for (Map.Entry<Integer, Integer> entry : objectCachePool.entrySet()) {
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }
        
        // LRU_POLICY
        size = 10;
        policy = 2;
        objectCachePool = new ObjectCachePool<Integer, Integer>(size, policy);
        for (int i = 1; i <= 15; i++) {
            objectCachePool.put(i, i);
        }
        for (int i = 15; i >= 1; i--) {
            objectCachePool.put(i, i);
        }
        System.out.println("size(" + size + "), policy(" + policy + ") LRU ");
        for (Map.Entry<Integer, Integer> entry : objectCachePool.entrySet()) {
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }
    }

    private static final int FIFO_POLICY = 1;
    private static final int LRU_POLICY = 2;
    private static final int DEFAULT_SIZE = 10;
    private Map<K, V> cacheObjects;

    public ObjectCachePool() {
        this(DEFAULT_SIZE);
    }

    public ObjectCachePool(int size) {
        this(size, FIFO_POLICY);
    }

    public ObjectCachePool(final int size, final int policy) {
        switch (policy) {
        
        case FIFO_POLICY:
            cacheObjects = new LinkedHashMap<K, V>(size) { // <=> (size, 0.75f, false) 
                /**
                 * 
                 */
                private static final long serialVersionUID = 1L;

                protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                    return size() > size;
                }
            };
            break;

        case LRU_POLICY:
            cacheObjects = new LinkedHashMap<K, V>(size, 0.75f, true) {
                /**
                 * 
                 */
                private static final long serialVersionUID = 1L;

                protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                    return size() > size;
                }
            };
            break;

        default:
            throw new IllegalArgumentException("Unknown policy: " + policy);

        }
    }

    public void put(K key, V value) {
        cacheObjects.put(key, value);
    }

    public void get(K key) {
        cacheObjects.get(key);
    }

    public void remove(K key) {
        cacheObjects.remove(key);
    }

    public void clear() {
        cacheObjects.clear();
    }
    
    public Set<Map.Entry<K, V>> entrySet() {
        return cacheObjects.entrySet();
    }

}

/*
size(10), policy(1) FIFO 
11, 11
12, 12
13, 13
14, 14
15, 15
5, 5
4, 4
3, 3
2, 2
1, 1
size(10), policy(2) LRU 
10, 10
9, 9
8, 8
7, 7
6, 6
5, 5
4, 4
3, 3
2, 2
1, 1

*/

