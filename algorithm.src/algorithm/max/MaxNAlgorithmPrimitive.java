/**
 * 
 */
package algorithm.max;

import java.util.Arrays;

import algorithm.ArrayUtil;

/**
 * Max N Primitive Algorithm
 * 
 * @author yangwm Nov 1, 2010 9:25:02 PM
 */
public class MaxNAlgorithmPrimitive {

    /**
     * max + n 
     * 
     * @param <int>
     * @param input
     * @param n
     * @return
     */
    public static long[] max(long[] input, int n) {
        if (n > input.length) {
            n = input.length;
        }
        
        long[] result = new long[n];
        long[] temp = Arrays.copyOf(input, n);
        Arrays.sort(temp);
        for (int i = temp.length - 1, resultIdx = 0; i >= temp.length - n; i--) {
            result[resultIdx++] = temp[i];
        }
        //System.out.println(Arrays.toString(result));
        
        int swapNum = 0;
        for (int i = n; i < input.length; i++) {
            long x = input[i];
            boolean needSwap = ArrayUtil.swapOne(result, x);
            if (needSwap) {
                swapNum++;
            }
        }
        //System.out.println("testTopN swapNum " + swapNum);
        return result;
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        long[] input = new long[] { 2, 7, 6, 8, 9, 18, 11, 5, 3, 20 };
        int n = 3;
        System.out.println("------MaxNPrimitiveAlgorithm Array max n-------");

        long[] result = max(input, n);
        System.out.println(Arrays.toString(result));
    }

}
