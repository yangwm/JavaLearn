/**
 * 
 */
package algorithm.vector;

import java.util.Arrays;

/**
 * Ordered Vector
 * 
 * @author yangwm
 */
public class OrderedVector {
    
    private int limit;
    private long[] items;
    private int len;
    
    /**
     * empty long array (flyweight) 
     */
    public static final long[] EMPTY_LONG_ARRAY = new long[0];
    private final static int VECTOR_LIMIT = 200;
    private final static int VECTOR_THRESHOLD = 10;

    public OrderedVector() {
        this(VECTOR_LIMIT);
    }
    public OrderedVector(int limit){
        this.limit = limit;
        this.items = new long[this.limit + VECTOR_THRESHOLD];
        this.len = 0;
    }

    public OrderedVector(long[] items) {
        this(VECTOR_LIMIT, items);
    }
    public OrderedVector(int limit, long[] items) {
        this.limit = limit;
        this.items = new long[limit + VECTOR_THRESHOLD];
        this.len = items.length <= limit ? items.length : limit;
        
        Arrays.sort(items);
        int startPos = this.len > limit ? this.len - limit : 0;
        System.arraycopy(items, startPos, this.items, 0, len);
    }

    // -------------- base api ----------------------
    
    /**
     * add by asc
     * 
     * @param id 
     * @return
     */
    public boolean add(long id){
        if (len <= 0) {
            items[0] = id;
            len = 1;
            return true;
        }
        
        limitCapacity();
        
        /*
         * index >= 0, find the value in items, so do nothing
         * index < 0 is finded, so add position：(-index) - 1, 
         */
        int index = binarySearch(id);
        if (index < 0) {
            int addPos = (-index) - 1;
            if (addPos < len) { // if add position not the last(end), will move array start add position 
                System.arraycopy(items, addPos, items, (-index), len - addPos);
            }
            //System.out.println("addPos:" + addPos + ", len:" + len);
            items[addPos] = id;
            len++;
        }
        return true;
    }
    private void limitCapacity() {
        /*
         * (len + 1) more than (limit + threshold), will trim oldest the number(threshold) of items 
         */
        if ((len + 1) > (limit + VECTOR_THRESHOLD)) {
            System.arraycopy(items, VECTOR_THRESHOLD, items, 0, limit);
            len = limit;
        }
    }

    public boolean delete(long id){
        if (len <= 0) {
            return true;
        }

        int index = binarySearch(id);
        if (index >= len - 1) {
            len--;
        } else if (index >= 0) {
            int delPos = index + 1;
            System.arraycopy(items, delPos, items, index, len - delPos);
            len--;
        }
        return true;
    }
    
    public boolean contains(long id){
        if (items.length > 0 && len > 0) {
            int index = binarySearch(id);
            return index >= 0;
        }
        return false;
    }
    
    public int binarySearch(long key) {
        if (key == items[len - 1]) { // may be the last, because delete the latest 
            return len - 1;
        }
        return Arrays.binarySearch(items, 0, len, key);
    }
    
    
    // -------------- extend api ----------------------
    
    public int getLimit() {
        return limit;
    }
    public long[] getItems() {
        return items;
    }
    public int getLen() {
        return len;
    }

    public long[] getActualItems(){
        if (len <= 0) {
            return EMPTY_LONG_ARRAY;
        } else if (len >= items.length) {
            return items;
        }
        return Arrays.copyOf(items, len);
    }
    
    public String toString() {
        if (len == 0) {
            return "{OrderedVector:len=0}";
        }
        StringBuilder sb = new StringBuilder(15 * len).append("{OrderedVector:len=" + len + ",items:[");
        for (int i = 0; i < len; i++) {
            sb.append(items[i]).append(",");
        }
        sb.append("]}");
        return sb.toString();
    }

}
