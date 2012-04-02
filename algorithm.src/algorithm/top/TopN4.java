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
public class TopN4 {
    
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
        Map<Long, RowColumn> heapMap = new HashMap<Long, RowColumn>(inputsLen);
        
        long cosumeTime = System.nanoTime() - beginTime;
        System.out.println("testTopN cosume time " + (cosumeTime/1000000));
        
        for (int i = 0; i < inputsLen; i++) {
            heap.add(inputs[i][0]);
            heapMap.put(inputs[i][0], new RowColumn(i, 0));
        }
        
        cosumeTime = System.nanoTime() - beginTime;
        System.out.println("testTopN cosume time " + (cosumeTime/1000000));
        
        long[] result = new long[n];
        int resultNum = 0;
        while (resultNum < n) {
            //System.out.println("heap.size():" + heap.size());
            //System.out.println("heapMap():" + heapMap.size());
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
            RowColumn rowColumn = heapMap.remove(max); // 所在数据行列 并 从map中干掉老的值  O(lg*m)
            int rowIdx = rowColumn.rowIdx;
            int columnIdx = rowColumn.columnIdx;
            
            /*
             * 当前heap中最大值，对应行号的列号指针后移 
             * 并保存新的最大值到heap中 
             */
            if (columnIdx < inputs[rowIdx].length - 1) {
                columnIdx = columnIdx + 1;
                rowColumn.columnIdx = columnIdx; // 指针后移 
                
                long next = inputs[rowIdx][columnIdx]; // 将指针后移之后的数据加入到堆中，待比较
                if (next > 0) {
                    heap.add(next); // O(lg*m) 
                    heapMap.put(next, rowColumn); // map中增加新的值 O(lg*m)
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
    
    
    static class RowColumn implements Comparable<RowColumn> {
        long value;
        int rowIdx;
        int columnIdx;
        
        public RowColumn(long value, int rowIdx, int columnIdx) {
            this.value = value;
            this.rowIdx = rowIdx;
            this.columnIdx = columnIdx;
        }
        
        public RowColumn(int rowIdx, int columnIdx) {
            this.rowIdx = rowIdx;
            this.columnIdx = columnIdx;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("RowColumn{")
            .append(value)
            .append(",")
            .append(rowIdx)
            .append(",")
            .append(columnIdx)
            .append("}");
            return sb.toString();
        }
        
        @Override
        public int compareTo(RowColumn o) {
            if (value > o.value) {
                return 1;
            } else if (value < o.value) {
                return -1;
            }
            return 0;
        }
    }

}
