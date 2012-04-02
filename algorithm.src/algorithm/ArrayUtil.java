/**
 * 
 */
package algorithm;

/**
 * 
 * 
 * @author yangwm Mar 30, 2012 12:40:32 PM
 */
public class ArrayUtil {

    /**
     * @param b
     */
    public final static void reverse(long[] b) {
        int left = 0; // index of leftmost element
        int right = b.length - 1; // index of rightmost element

        while (left < right) {
            // exchange the left and right elements
            long temp = b[left];
            b[left] = b[right];
            b[right] = temp;

            // move the bounds toward the center
            left++;
            right--;
        }
    }
    
    /**
     * @param b
     * @param n
     * @return
     */
    public final static long[] reverseCopy(long[] b, int n) {
        //System.out.println("temp length:" + temp.length);
        long[] result = new long[n];
        int bLimit = b.length - n;
        for (int i = b.length - 1, resultIdx = 0; i >= bLimit; i--) {
            result[resultIdx++] = b[i];
        }
        //System.out.println("result length:" + result.length + ", result" + Arrays.toString(result));
        return result;
    }

    /**
     * 如果查找到比数组如何一个值大, 那么需要交换出最小的（数组最后一个） 
     *
     * 使用orderPosition效率高于binaryPosition
     * 
     * @param result
     * @param x
     * @return need swap return true, else return false 
     */
    public static boolean swapOne(long[] result, long x) {
        int k = orderPosition(result, x); //binaryPosition(result, x); // 
        if (k < result.length - 1) { // 根据返回的数组下标交换位置 （让x元素排在比它的小的元素前面） 
            int srcIdx = k + 1;
            if (srcIdx != result.length - 1) { // 已经是最后一位不需要移动整个数组 
                int destIdx = srcIdx + 1; // 后移一位  
                System.arraycopy(result, srcIdx, result, destIdx, result.length - destIdx);
            }
            result[srcIdx] = x;
            
            return true;
        }
        return false;
    }
    /**
     * 由小到大定位：找出大于等于x元素的数组下标，没有大于等于x元素则返回-1 
     * 
     * @param result order by desc 
     * @param x
     * @return
     */
    static int orderPosition(long[] result, long x) {
        //System.out.println("orderPosition:" + x + ", System.out.println("temp length:" + temp.length);" + Arrays.toString(result));
        int k = result.length - 1;

        while (k >= 0) {
            long e = result[k];
            
            if (x >= e) {
                k--;
            } else {
                break;
            }
        }
        
        //System.out.println("k:" + k);
        return k;
    }
    /**
     * 二分定位：找出大于等于x元素的数组下标，没有大于等于x元素则返回-1 
     * Arrays.binarySearch的变体
     * 
     * @param result order by desc 
     * @param x
     * @return
     */
    static int binaryPosition(long[] result, long x) {
        //System.out.println("binaryPosition:" + x + ", " + Arrays.toString(result));
        int k = -1;
        
        int left = 0;
        int right = result.length - 1;
        while (left <= right) {
            int mid = (left + right) >>> 1;
            long midVal = result[mid];

            if (x < midVal) {
                left = mid + 1;
            } else if (x > midVal) {
                right = mid - 1;
            } else {
                break;
            }
        }
        
        k = left - 1;
        //System.out.println("k:" + k);
        return k;
    }

}
