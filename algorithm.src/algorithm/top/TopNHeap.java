/**
 * 
 */
package algorithm.top;

import java.util.Collection;
import java.util.TreeSet;

import algorithm.ArrayUtil;

/**
 * 求inputs中的top n (inputs.length是friendCount为M，inputs[i]是vector,vectorSize为210) 
 * 算法描述： 
 *  取每个vector前pos个值来构建一个大小接近N(M*pos, pos为M/N)的heap(TreeSet)并做为比较参照与最终结果, 循环进行一下动作直到得到top n  
 *      1. 后续比较每个vector剩余数据，大于heap中最小值则需替换heap中数据，否则直接跳过该vector   
 * 
 * 时间复杂度：O(N*lg(M)*lg(210)) 
 * 空间复杂度：O(N) 
 * 性能衰变度：随N增大性能有下降快
 *   
 * bench: 
 * testTopN TopNHeap, cosume time 3364.160854 ms, top'n 45 from vectorSize 210, friendCount 2000, times 10000 per time 0.336416 ms.
 * testTopN TopNHeap, cosume time 21620.403206 ms, top'n 500 from vectorSize 210, friendCount 2000, times 10000 per time 2.16204 ms.
 * testTopN TopNHeap, cosume time 45305.996404 ms, top'n 2000 from vectorSize 210, friendCount 2000, times 10000 per time 4.530599 ms.
 * 
 * @author yangwm Mar 27, 2012 9:12:34 PM
 */
public class TopNHeap {

    /**
     * top n 
     * 
     * @param inputs must order desc 
     * @param n
     * @return result's length <= n 
     */
    public static long[] top(long[][] inputs, int n) {
        int inputsLen = inputs.length;
        int pos = n / inputsLen;
        
        long beginTime = System.nanoTime();

        TreeSet<Long> heap = new TreeSet<Long>();
        for (int i = 0; i < inputsLen; i++) {
            long[] vector = inputs[i];
            int vectorLen = vector.length;
            for (int j = 0; j < pos && j < vectorLen; j++) {
                long val = vector[j];
                if (val > 0) { // 值需大于0  
                    heap.add(vector[j]);
                }
            }
        }
        
        long cosumeTime = System.nanoTime() - beginTime;
//        System.out.println("testTopN inputsLen " + inputsLen + ", pos " + pos + ", cosume time " + (cosumeTime/1000000));

        int swapNum = 0;
        for (int i = 0; i < inputsLen; i++) {
            long[] vector = inputs[i];
            int vectorLen = vector.length;
            for (int j = pos; j < vectorLen; j++) {
                long val = vector[j];
                if (val > 0) { // 值需大于0  
                    if (heap.size() < n) {
                        heap.add(val);
                        swapNum++;
                    } else {
                        if (heap.lower(val) != null) {
                            heap.pollFirst();
                            heap.add(val);
                            swapNum++;
                        } else {
                            break;
                        }
                    }
                } else {
                    break;
                }
                
            }
        }
        long[] result = toLongArr(heap);
        ArrayUtil.reverse(result);
        
        cosumeTime = System.nanoTime() - beginTime;
//        System.out.println("testTopN swapNum " + swapNum + ", cosume time " + (cosumeTime/1000000));
        
        return result;
    }
    
    public static long[] toLongArr(Collection<Long> ids){
        if(ids == null || ids.size() == 0){
            return new long[0];
        }
        long[] idsArr = new long[ids.size()];
        int idx = 0;
        for(long id : ids){
            idsArr[idx++] = id;
        }
        return idsArr;
    }
    
}
