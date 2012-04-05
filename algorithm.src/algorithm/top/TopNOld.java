/**
 * 
 */
package algorithm.top;

import java.util.Arrays;

import algorithm.ArrayUtil;

/**
 * 求inputs中的top n (inputs.length是friendCount为M，inputs[i].length是vectorSize为210) 
 * 算法描述：通过ThreadLocal的大小为2000*210的大数组, 分配得到实际大小为M*210数组并进行sort, 之后在取最大N个  
 * 
 * 时间复杂度：O((M*210)*lg(M*210)) 
 * 空间复杂度：O(M*210) 
 * 性能衰变度：恒定 
 * 
 * bench: 
 * testTopN TopNOld, cosume time 668749.768094 ms, top'n 45 from vectorSize 210, friendCount 2000, times 10000 per time 66.874976 ms.
 * testTopN TopNOld, cosume time 677682.549496 ms, top'n 500 from vectorSize 210, friendCount 2000, times 10000 per time 67.768254 ms
 * testTopN TopNOld, cosume time 671494.445174 ms, top'n 2000 from vectorSize 210, friendCount 2000, times 10000 per time 67.149444 ms.
 * 
 * @author yangwm Mar 27, 2012 9:12:34 PM
 */
public class TopNOld {
    
    protected static ThreadLocal<long[]> localBuf = new ThreadLocal<long[]>() {
        protected long[] initialValue() {
            return new long[(210) * 2000];
        }
    };
    
    /**
     * top n 
     * 
     * @param inputs must order desc 
     * @param n
     * @return result's length <= n 
     */
    public static long[] top(long[][] inputs, int n) {
        int inputsLen = inputs.length;
        
        long[] dest = localBuf.get();
        int pos = 0;
        for (int i = 0; i < inputsLen; i++) {
            long[] vector = inputs[i];
            System.arraycopy(vector, 0, dest, pos, vector.length);
            pos += vector.length;
        }
        long[] totalIds = new long[pos];
        System.arraycopy(dest, 0, totalIds, 0, pos);

        Arrays.sort(totalIds);
        long[] result = ArrayUtil.reverseCopy(totalIds, n); // 线上版没有进行只大小为n数组操作,而是放回所有数据   
        //System.out.println(result.length);
        
        return result;
    }

}
