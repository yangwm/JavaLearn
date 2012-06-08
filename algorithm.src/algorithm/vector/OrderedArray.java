/**
 * 
 */
package algorithm.vector;

/**
 * 
 * 
 * http://zhangshixi.iteye.com/blog/632928
 * @author yangwm Jun 7, 2012 9:45:05 PM
 */
public class OrderedArray {

    private long[] array;  
    private int size;  
  
    /** 
     * 获取数组的元素数目。 
     * @return 元素数目 
     */  
    public int size() {  
        return size;  
    }  
  
    /** 
     * 向数组中插入一个元素。 
     * 插入后，数组中的元素仍是按关键字有序排列的。 
     * @param value 要插入的元素 
     */  
    public void insert(long value) {  
        int index;  
        for (index = 0; index < size; index++) {  
            if (array[index] > value) {  
                break;  
            }  
        }  
        for (int count = index; count < size; count++) {  
            array[count + 1] = array[count];  
        }  
        array[index] = value;  
        size++;  
    }  
  
    /** 
     * 查找数组中的指定元素。 
     * 采用二分法查找实现。 
     * @param value 要查找的元素的关键字 
     * @return 要查找的元素在数组中的下标 
     */  
    public int find(long value) {  
        int start = 0;  
        int end = size - 1;  
        int index;  
  
        while (true) {  
            index = (start + end) / 2;  
            if (array[index] == value) {  
                return index;  
            } else if (end >= start) {  
                if (array[index] < value) {  
                    start = index + 1;  
                } else {  
                    end = index - 1;  
                }  
            } else {  
                return size;  
            }  
        }  
    }  
      
    /** 
     * 查找数组中指定下下标的元素。 
     * @param index 要查找的元素的下标 
     * @return 要查找的元素 
     */  
    public long get(int index) {  
        return array[index];  
    }  
  
    /** 
     * 删除数组中的指定元素。删除元素后，后序元素将逐个前移。 
     * @param value 要删除的元素的关键字 
     * @return 如果删除成功，返回true；反之，返回false。 
     */  
    public boolean delete(long value) {  
        int index = find(value);  
        if (index == size) {  
            return false;  
        } else {  
            for (int count = index; count < size; count++) {  
                array[count] = array[count + 1];  
            }  
            size--;  
            return true;  
        }  
    }
    
}
