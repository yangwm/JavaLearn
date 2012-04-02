/**
 * 
 */
package algorithm.top;

import java.util.Arrays;

import algorithm.ArrayUtil;

/**
 * 求inputs中的top n (inputs.length是friendCount为M，inputs[i].length是vectorSize为210) 
 * 
 * 时间复杂度：O(N*lg(M)*lg(210)) 
 * 空间复杂度：O(N) 
 * 性能衰变度：随N增大性能有下降太快
 * 
 * bench: 
 * TopNArray, cosume time 417.270399 ms, top'n 45, times 10000 per time 0.041727 ms.
 * TopNArray, cosume time 139951.791546 ms, top'n 2000, times 10000 per time 13.995102 ns.
 * 
 * @author yangwm Mar 27, 2012 9:12:34 PM
 */
public class TopNArray {

    private static final int FACTOR = 0;

    /**
     * top n 
     * 
     * @param inputs must order desc 
     * @param n
     * @return result's length <= n 
     */
    public static long[] top(long[][] inputs, int n) {
        int inputsLen = inputs.length;
        int pos = (n % inputsLen) == 0 ? n / inputsLen + FACTOR : n / inputsLen + 1 + FACTOR;
        
        long beginTime = System.nanoTime();
        
        long[] init = new long[inputsLen * pos]; // new long[n];
        int initIdx = 0;
        for (int i = 0; i < inputsLen; i++) {
            long[] vector = inputs[i];
            int vectorLen = vector.length;
            for (int j = 0; j < pos && j < vectorLen; j++) {
                long val = vector[j];
                if (val > 0) { // 值需大于0  
                    init[initIdx++] = val;
                }
            }
        }
        Arrays.sort(init);
        long[] result = ArrayUtil.reverseCopy(init, n);
        
        long cosumeTime = System.nanoTime() - beginTime;
//        System.out.println("testTopN inputsLen " + inputsLen + ", pos " + pos + ", cosume time " + (cosumeTime/1000000));

        int swapNum = 0;
        for (int i = 0; i < inputsLen; i++) {
            long[] vector = inputs[i];
            int vectorLen = vector.length;
            for (int j = pos; j < vectorLen; j++) {
                long val = vector[j];
                if (val > 0) { // 值需大于0  
                    boolean needSwap = ArrayUtil.swapOne(result, val);
                    if (needSwap) {
                        swapNum++;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        
        cosumeTime = System.nanoTime() - beginTime;
//        System.out.println("testTopN swapNum " + swapNum + ", cosume time " + (cosumeTime/1000000));
        
        return result;
    }

}
