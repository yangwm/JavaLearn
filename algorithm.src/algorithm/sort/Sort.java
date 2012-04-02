/**
 * 
 */
package algorithm.sort;

import java.util.Arrays;  
import java.util.Comparator;  
import java.util.Random;  
  
/** 
 * 排序接口，所有的排序算法都要继承该抽象类，并且要求数组中的 
 * 元素要具有比较能力，即数组元素已实现了Comparable接口 
 * 
 * @author yangwm Mar 29, 2012 5:34:02 PM
 * @param <E> 
 */  
public abstract class Sort<E extends Comparable<E>> {  
  
    public final Comparator<E> DEFAULT_ORDER = new DefaultComparator();  
    public final Comparator<E> REVERSE_ORDER = new ReverseComparator();  
  
    /** 
     * 排序算法，需实现，对数组中指定的元素进行排序 
     * @param array 待排序数组 
     * @param from 从哪里 
     * @param end 排到哪里 
     * @param c 
     */  
    public abstract void sort(E[] array, int from, int end, Comparator<E> c);  
  
    /** 
     * 对数组中指定部分进行排序 
     * @param from 从哪里 
     * @param len 排到哪里 
     * @param array 待排序数组 
     * @param c 比较器 
     */  
    public void sort(int from, int len, E[] array, Comparator<E> c) {  
        sort(array, 0, array.length - 1, c);  
    }  
  
    /** 
     * 对整个数组进行排序，可以使用自己的排序比较器，也可使用该类提供的两个比较器 
     * @param array 待排序数组 
     * @param c 比较器 
     */  
    public final void sort(E[] array, Comparator<E> c) {  
        sort(0, array.length, array, c);  
    }  
  
    /** 
     * 对整个数组进行排序，采用默认排序比较器 
     * @param array 待排序数组 
     */  
    public final void sort(E[] array) {  
        sort(0, array.length, array, this.DEFAULT_ORDER);  
    }  
  
    //默认比较器（一般为升序，但是否真真是升序还得看E是怎样实现Comparable接口的）  
    private class DefaultComparator implements Comparator<E> {  
        public int compare(E o1, E o2) {  
            return o1.compareTo(o2);  
        }  
    }  
  
    //反序比较器，排序刚好与默认比较器相反  
    private class ReverseComparator implements Comparator<E> {  
        public int compare(E o1, E o2) {  
            return o2.compareTo(o1);  
        }  
    }  
  
    /** 
     * 交换数组中的两个元素的位置 
     * @param array 待交换的数组 
     * @param i 第一个元素 
     * @param j 第二个元素 
     */  
    protected final void swap(E[] array, int i, int j) {  
        if (i != j) {//只有不是同一位置时才需交换  
            E tmp = array[i];  
            array[i] = array[j];  
            array[j] = tmp;  
        }  
    }  
  
    /** 
     * 数组元素后移 
     * @param array 待移动的数组 
     * @param startIndex 从哪个开始移 
     * @param endIndex 到哪个元素止 
     */  
    protected final void move(E[] array, int startIndex, int endIndex) {  
        for (int i = endIndex; i >= startIndex; i--) {  
            array[i + 1] = array[i];  
        }  
    }  
  
    /** 
     * 以指定的步长将数组元素后移，步长指定每个元素间的间隔 
     * @param array 待排序数组 
     * @param startIndex 从哪里开始移 
     * @param endIndex 到哪个元素止 
     * @param step 步长 
     */  
    protected final void move(E[] array, int startIndex, int endIndex, int step) {  
        for (int i = endIndex; i >= startIndex; i -= step) {  
            array[i + step] = array[i];  
        }  
    }  
  
    //测试方法  
    @SuppressWarnings("unchecked")  
    public static final <E extends Comparable<E>> void testSort(Sort<E> sorter, E[] array) {  
  
        if (array == null) {  
            array = randomArray();  
        }  
        //为了第二次排序，需拷贝一份  
        E[] tmpArr = (E[]) new Comparable[array.length];  
        System.arraycopy(array, 0, tmpArr, 0, array.length);  
  
        System.out.println("源 - " + Arrays.toString(tmpArr));  
  
        sorter.sort(array, sorter.REVERSE_ORDER);  
        System.out.println("降 - " + Arrays.toString(array));  
  
        sorter.sort(tmpArr, sorter.DEFAULT_ORDER);  
        System.out.println("升 - " + Arrays.toString(tmpArr));  
    }  
  
    //生成随机数组  
    @SuppressWarnings("unchecked")  
    private static <E extends Comparable<E>> E[] randomArray() {  
        Random r = new Random(System.currentTimeMillis());  
        Integer[] a = new Integer[r.nextInt(30)];  
        for (int i = 0; i < a.length; i++) {  
            a[i] = new Integer(r.nextInt(100));  
        }  
        return (E[]) a;  
    }  
}
