package algorithm.max;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;

/**
 * max n algorithm
 * @param <E>
 * 
 * @author yangwm Oct 31, 2010 10:18:40 PM
 */
public class MaxNAlgorithm<E> {

    /**
     * max + n + comparator 
     * 
     * @param <E>
     * @param input
     * @param n
     * @param comparator
     * @return
     */
    public static <E> E[] max(E[] input, int n, Comparator<? super E> comparator) {
        if (n > input.length) {
            n = input.length;
        }
        
        E[] result = newGenericArray(input[0], n);
        E[] temp = Arrays.copyOf(input, n);
        Arrays.sort(temp, comparator);
        for (int i = temp.length - 1, resultIdx = 0; i >= temp.length - n; i--) {
            result[resultIdx++] = temp[i];
        }
        //System.out.println(Arrays.toString(result));
        
        for (int i = n; i < input.length; i++) {
            add(input[i], result, comparator);
        }
        return result;
    }

    /**
     * max + n 
     * 
     * @param <E>
     * @param input
     * @param n
     * @return
     */
    public static <E> E[] max(E[] input, int n) {
        if (n > input.length) {
            n = input.length;
        }
        
        E[] result = newGenericArray(input[0], n);
        E[] temp = Arrays.copyOf(input, n);
        Arrays.sort(temp);
        for (int i = temp.length - 1, resultIdx = 0; i >= temp.length - n; i--) {
            result[resultIdx++] = temp[i];
        }
        //System.out.println(Arrays.toString(result));
        
        for (int i = n; i < input.length; i++) {
            add(input[i], result);
        }
        return result;
    }
    
    @SuppressWarnings("unchecked")
    static <E> E[] newGenericArray(E e, int size) {
        Class<E> type = (Class<E>) e.getClass();
        return (E[]) Array.newInstance(type, size);
    }

    /**
     * add 
     * 
     * @param <E>
     * @param x
     * @param result
     */
    private static <E> void add(E x, E[] result) {
        add(x, result, null);
    }

    /**
     * add 
     * 
     * 使用orderPosition效率高于binaryPosition 
     * 
     * @param <E>
     * @param x
     * @param result
     * @param comparator
     */
    private static <E> void add(E x, E[] result, Comparator<? super E> comparator) {
        int k = orderPosition(x, result, comparator); // binaryPosition(x, result, comparator); 

        /*
         * 根据返回的数组下标交换位置 （让x元素排在比它的小的元素前面） 
         */
        if (k < result.length - 1) {
            int srcIdx = k + 1;
            if (srcIdx < result.length) { // 不是插入到最后需要移动数组 
                int destIdx = srcIdx + 1; // 后移一位  
                System.arraycopy(result, srcIdx, result, destIdx, result.length - destIdx);
            }
            result[srcIdx] = x;
        }
    }

    /**
     * 由小到大定位：找出大于等于x元素的数组下标，没有大于等于x元素则返回-1 
     * 
     * @param <E>
     * @param x
     * @param result order by desc 
     * @param comparator
     * @return
     */
    private static <E> int orderPosition(E x, E[] result, Comparator<? super E> comparator) {
        //System.out.println("binaryPosition:" + x + ", " + Arrays.toString(result));
        
        int k = result.length - 1;
        if (comparator != null) {
            while (k >= 0) {
                E e = result[k];
                //System.out.println("result[" + k + "]=" + e);
                if (comparator.compare(x, (E) e) >= 0) {
                    k--;
                } else {
                    break;
                }
            }
        } else {
            @SuppressWarnings("unchecked")
            Comparable<? super E> key = (Comparable<? super E>) x;
            while (k >= 0) {
                E e = result[k];
                //System.out.println("result[" + k + "]=" + e);
                if (key.compareTo((E) e) >= 0) {
                    k--;
                } else {
                    break;
                }
            }
        }
        
        //System.out.println("k:" + k);
        return k;
    }

    /**
     * 二分定位：找出大于等于x元素的数组下标，没有大于等于x元素则返回-1 
     * Arrays.binarySearch的变体
     * 
     * @param <E>
     * @param x
     * @param result
     * @param comparator
     * @return
     */
    private static <E> int binaryPosition(E x, E[] result, Comparator<? super E> comparator) {
        //System.out.println("binaryPosition:" + x + ", " + Arrays.toString(result));
        
        int k = -1;
        
        int left = 0;
        int right = result.length - 1;
        if (comparator != null) {
            while (left <= right) {
                int mid = (left + right) >>> 1;
                E midVal = result[mid];

                if (comparator.compare(x, midVal) < 0) {
                    left = mid + 1;
                } else if (comparator.compare(x, midVal) > 0) {
                    right = mid - 1;
                } else {
                    break;
                }
            }
        } else {
            @SuppressWarnings("unchecked")
            Comparable<? super E> key = (Comparable<? super E>) x;
            while (left <= right) {
                int mid = (left + right) >>> 1;
                E midVal = result[mid];

                if (key.compareTo(midVal) < 0) {
                    left = mid + 1;
                } else if (key.compareTo(midVal) > 0) {
                    right = mid - 1;
                } else {
                    break;
                }
            }
        }
        
        k = left - 1;
        //System.out.println("k:" + k);
        return k;  // key not found.
    }
    

    /**
     * @param args
     */
    public static void main(String[] args) {
        Integer[] input = new Integer[] { 2, 7, 6, 8, 9, 18, 11, 5, 3, 20 };
        int n = 3;
        System.out.println("------MaxNAlgorithm Array max n-------");

        Integer[] result = max(input, n);
        System.out.println(Arrays.toString(result));
    }

}

/*
------MaxNAlgorithm Array max n-------
[20, 18, 11]

*/
