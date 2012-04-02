/**
 * 
 */
package algorithm.top;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 
 * @author yangwm Mar 28, 2012 2:31:07 AM
 */
public class TopN2 {

    public static int findMax(long[] heap) {
        long max = 0;
        int maxIdx = -1;
        for (int i = 0; i < heap.length; i++) {
            if (heap[i] > max) {
                max = heap[i];
                maxIdx = i;
            }
        }
        return maxIdx;
    }
    
    /**
     * top n 
     * 
     * @param <int>
     * @param input
     * @param n
     * @return
     */
    public static long[] top(long[][] inputs, int n) {
        long beginTime = System.nanoTime();
        
        int inputsLen = inputs.length;
        long[] heap = new long[inputsLen]; 
        int[] arrIdxs = new int[inputsLen]; // array index postion         
        Map<Long, Integer> heapMap = new HashMap<Long, Integer>();
        
        for (int i = 0; i < inputsLen; i++) {
            heap[i] = inputs[i][0];
            heapMap.put(inputs[i][0], i);
        }
        System.out.println(heapMap);

        long[] result = new long[n];
        int resultNum = 0;
        while (resultNum < n) {
            int rowIdx = findMax(heap);
            if (rowIdx == -1) {
                break;
            }
            int curResultNum = resultNum;
            result[resultNum++] = heap[rowIdx];
            int columIdx = heapMap.get(heap[rowIdx]); // 所在数据序号
            if (arrIdxs[columIdx] < inputs[columIdx].length - 1) { // 指针后移
                heap[rowIdx] = inputs[columIdx][arrIdxs[columIdx]+1]; // 将指针后移之后的数据加入到堆中，待比较
                heapMap.put(inputs[columIdx][arrIdxs[columIdx]+1], rowIdx); // map中增加新的值
                heapMap.remove(result[curResultNum]); // 从map中干掉老的值
                arrIdxs[columIdx]++;
            } else if (arrIdxs[columIdx] == inputs[columIdx].length - 1) { // 如果指针到结尾
                heap[rowIdx] = -1;
            }
            
            
        }
        
        
        long cosumeTime = System.nanoTime() - beginTime;
        System.out.println("testTopN cosume time " + (cosumeTime/1000000));
        
        return result;
    }
        
}
