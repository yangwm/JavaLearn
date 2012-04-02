/**
 * 
 */
package algorithm.top;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

/**
 * 
 * 
 * @author yangwm Mar 27, 2012 9:12:34 PM
 */
public class TopN3 {
    
    public static long getDeleteMax(TreeSet<Long> heap) {
        return heap.pollLast();
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
        TreeSet<Long> heap = new TreeSet<Long>();
        Map<Long, Integer> heapMap = new HashMap<Long, Integer>(inputsLen);
        int[] arrIdxs = new int[inputsLen]; // array index postion  
        
        long cosumeTime = System.nanoTime() - beginTime;
        System.out.println("testTopN cosume time " + (cosumeTime/1000000));
        
        for (int i = 0; i < inputsLen; i++) {
            heap.add(inputs[i][0]);
            heapMap.put(inputs[i][0], i);
        }
        
        cosumeTime = System.nanoTime() - beginTime;
        System.out.println("testTopN cosume time " + (cosumeTime/1000000));
        
        long[] result = new long[n];
        int resultNum = 0;
        while (resultNum < n) {
            System.out.println("heap.size():" + heap.size());
            System.out.println("heapMap():" + heapMap.size());
            if (heap.isEmpty()) {
                break;
            }
            
            /* 
             * 找到最大值并移除 
             */
            long max = getDeleteMax(heap); // O(1)
            
            /* 
             * 最大值所在rowIdx和columnIdx
             */
            int rowIdx = heapMap.remove(max); // 所在数据行号 并 从map中干掉老的值  O(lg*m)
            int columnIdx = arrIdxs[rowIdx]; // 所在数据列号 
            
            /*
             * 当前heap中最大值，对应行号的列号指针后移 
             */
            if (columnIdx < inputs[rowIdx].length - 1) {
                columnIdx = columnIdx + 1;
                arrIdxs[rowIdx] = columnIdx; // 指针后移 
                
                long next = inputs[rowIdx][columnIdx]; // 将指针后移之后的数据加入到堆中，待比较
                if (next > 0) {
                    heap.add(next); // O(lg*m) 
                    heapMap.put(next, rowIdx); // map中增加新的值 O(lg*m)
                }
            } 

            /*
             * save result 
             */
            result[resultNum] = max;
            resultNum = resultNum + 1;
        }
        cosumeTime = System.nanoTime() - beginTime;
        System.out.println("testTopN cosume time " + (cosumeTime/1000000));
        
        return result;
    }

}
