/**
 * 
 */
package algorithm.vector;

import java.util.Arrays;

/**
 * 
 * @author yangwm Jun 9, 2012 12:33:08 AM
 */
public class OrderedVectorObject {
    
    private int limit;
    private Object[] items;
    private int len;
    
    /**
     * empty Object array (flyweight) 
     */
    public static final Object[] EMPTY_LONG_ARRAY = new Object[0];
    private final static int VECTOR_LIMIT = 200;
    private final static int VECTOR_THRESHOLD = 10;

    public OrderedVectorObject() {
        this(VECTOR_LIMIT);
    }
    public OrderedVectorObject(int limit){
        this.limit = limit;
        this.items = new Object[this.limit + VECTOR_THRESHOLD];
        this.len = 0;
    }

    public OrderedVectorObject(Object[] items) {
        this(VECTOR_LIMIT, items);
    }
    public OrderedVectorObject(int limit, Object[] items) {
        this.limit = limit;
        this.items = new Object[limit + VECTOR_THRESHOLD];
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
    public boolean add(Object id){
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

    public boolean delete(Object id){
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
    
    public boolean contains(Object id){
        if (items.length > 0 && len > 0) {
            int index = binarySearch(id);
            return index >= 0;
        }
        return false;
    }
    
    public int binarySearch(Object key) {
        if (key == items[len - 1]) { // may be the last, because delete the latest 
            return len - 1;
        }
        return Arrays.binarySearch(items, 0, len, key);
    }
    
    
    // -------------- extend api ----------------------
    
    public int getLimit() {
        return limit;
    }
    public Object[] getItems() {
        return items;
    }
    public int getLen() {
        return len;
    }

    public Object[] getActualItems(){
        if (len <= 0) {
            return EMPTY_LONG_ARRAY;
        } else if (len >= items.length) {
            return items;
        }
        return Arrays.copyOf(items, len);
    }
    
    public String toString() {
        if (len == 0) {
            return "{OrderedVectorObject:len=0}";
        }
        StringBuilder sb = new StringBuilder(15 * len).append("{OrderedVectorObject:len=" + len + ",items:[");
        for (int i = 0; i < len; i++) {
            sb.append(items[i]).append(",");
        }
        sb.append("]}");
        return sb.toString();
    }

}
