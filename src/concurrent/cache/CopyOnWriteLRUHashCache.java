/**
 * 
 */
package concurrent.cache;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;


/**
 * CopyOnWrite LRU Hash Cache 
 * 
 * @param <K>
 * @param <V>
 * 
 * @author yangwm Mar 24, 2011 9:52:33 PM
 */
public class CopyOnWriteLRUHashCache<K, V> implements Map<K, V>, Cloneable {
    

    private final int maxCapacity;
    
    private final long maxLifetime;

    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private volatile Map<K, CacheObject<V>> internalMap;
    

    /**
     * @serial include
     */
    @SuppressWarnings("hiding")
    private final class LRUHashMap<K, V> extends LinkedHashMap<K, V> {
        
        /**
         * 
         */
        private static final long serialVersionUID = -7223685508350988119L;

        public LRUHashMap() {
            super(maxCapacity, DEFAULT_LOAD_FACTOR, true);
        }
        
        public LRUHashMap(Map<? extends K, ? extends V> m) {
            super(m);
        }

        @Override
        protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
            //System.out.println("size():" + size() + ", maxCapacity:" + maxCapacity);
            return size() > maxCapacity;
        }
    }
    

    /**
     * Creates a new instance of CopyOnWriteLRUHashMap with the specified initial size
     *
     * @param initialCapacity
     *  The initial size of the Map.
     */
    public CopyOnWriteLRUHashCache(int size, long lifetime) {
        maxCapacity = size;
        maxLifetime = lifetime;
        Map<K, CacheObject<V>> map = new LRUHashMap<K, CacheObject<V>>();
        
        Map<K, CacheObject<V>> newMap = Collections.unmodifiableMap(map);
        internalMap = newMap;
    }

    /**
     * Adds the provided key and value to this map.
     *
     * @see java.util.Map#put(java.lang.Object, java.lang.Object)
     */
    public V put(K key, V value) {
        Map<K, CacheObject<V>> map = new LRUHashMap<K, CacheObject<V>>(internalMap);
        CacheObject<V> cacheObject = new CacheObject<V>(value, System.currentTimeMillis());
        map.put(key, cacheObject);
        
        Map<K, CacheObject<V>> newMap = Collections.unmodifiableMap(map);
        synchronized (this) {
            internalMap = newMap;
            return cacheObject.object;
        }
    }

    /**
     * Removed the value and key from this map based on the
     * provided key.
     *
     * @see java.util.Map#remove(java.lang.Object)
     */
    public V remove(Object key) {
        Map<K, CacheObject<V>> map = new LRUHashMap<K, CacheObject<V>>(internalMap);
        CacheObject<V> val = map.remove(key);
        
        Map<K, CacheObject<V>> newMap = Collections.unmodifiableMap(map);
        synchronized (this) {
            internalMap = newMap;
            if (val == null) {
                return null;
            }
            return val.object;
        }
    }

    /**
     * Inserts all the keys and values contained in the
     * provided map to this map.
     *
     * @see java.util.Map#putAll(java.util.Map)
     */
    public void putAll(Map<? extends K, ? extends V> newData) {
        Map<K, CacheObject<V>> map = new LRUHashMap<K, CacheObject<V>>(internalMap);
        for (Entry<? extends K, ? extends V> entry : newData.entrySet()) {
            CacheObject<V> cacheObject = new CacheObject<V>(entry.getValue(), System.currentTimeMillis());
            map.put(entry.getKey(), cacheObject);
        }
        
        Map<K, CacheObject<V>> newMap = Collections.unmodifiableMap(map);
        synchronized (this) {
            internalMap = newMap;
        }
    }

    /**
     * Removes all entries in this map.
     *
     * @see java.util.Map#clear()
     */
    public void clear() {
        Map<K, CacheObject<V>> map = new LRUHashMap<K, CacheObject<V>>();
        
        Map<K, CacheObject<V>> newMap = Collections.unmodifiableMap(map);
        synchronized (this) {
            internalMap = newMap;
        }
    }

    // ==============================================
    // ==== Below are methods that do not modify ====
    // ====         the internal Maps            ====
    // ==============================================
    /**
     * Returns the number of key/value pairs in this map.
     *
     * @see java.util.Map#size()
     */
    public int size() {
        return internalMap.size();
    }

    /**
     * Returns true if this map is empty, otherwise false.
     *
     * @see java.util.Map#isEmpty()
     */
    public boolean isEmpty() {
        return internalMap.isEmpty();
    }

    /**
     * Returns true if this map contains the provided key, otherwise
     * this method return false.
     *
     * @see java.util.Map#containsKey(java.lang.Object)
     */
    public boolean containsKey(Object key) {
        return internalMap.containsKey(key);
    }

    /**
     * Returns true if this map contains the provided value, otherwise
     * this method returns false.
     *
     * @see java.util.Map#containsValue(java.lang.Object)
     */
    public boolean containsValue(Object value) {
        return false;
    }

    /**
     * Returns the value associated with the provided key from this
     * map.
     *
     * @see java.util.Map#get(java.lang.Object)
     */
    public V get(Object key) {
        CacheObject<V> val = internalMap.get(key);
        
        if (val == null) {
            return null;
        }
        if (maxLifetime < (System.currentTimeMillis() - val.createTime)) {
            remove(key);
            return null;
        }
        return val.object;
    }

    /**
     * This method will return a read-only {@link Set}.
     */
    public Set<K> keySet() {
        return internalMap.keySet();
    }

    /**
     * This method will return a read-only {@link Collection}.
     */
    public Collection<V> values() {
        return null;
    }

    /**
     * This method will return a read-only {@link Set}.
     */
    public Set<Entry<K, V>> entrySet() {
        return null;
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
    }
    

    /**
     * Wrapper for all objects put into cache. It's primary purpose is to maintain
     * references to the linked lists that maintain the creation time of the object
     * and the ordering of the most used objects.
     */
    private static class CacheObject<V> {

        /**
         * Underlying object wrapped by the CacheObject.
         */
        public V object;

        public long createTime;

        public CacheObject(V object, long createTime) {
            this.object = object;
            this.createTime = createTime;
        }

    }

}
