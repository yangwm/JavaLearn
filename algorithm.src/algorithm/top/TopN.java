/**
 * 
 */
package algorithm.top;

import java.util.TreeSet;

/**
 * 求inputs中的top n (inputs.length是friendCount为M，inputs[i].length是vectorSize为210) 
 * 算法描述： 先构建一个M*pos的heap（TreeSet）, 后续比较每个vector剩余数据，大于heap中最小值则需替换heap中数据，否则直接跳过该vector   
 * 算法描述： 
 *  取每个vector第1个值来构建一个大小接近M的heap(TreeSet)并做为比较并做为为比较参照，分配一个大小为N数组并做为最终结果，构建循环进行一下动作直到得到top n  
 *      取出heap中最大值放入最终结果数组  
 *      后续比较每个vector剩余数据，大于heap中最小值则需替换heap中数据，否则直接跳过该vector并从heap移除对该vector比较资格     
 * 
 * 时间复杂度：O(N+lg(M)*lg(M)*lg(210)) 
 * 空间复杂度：O(M + N) 
 * 性能衰变度：随N增大性能有下降微略
 * 
 * bench: 
 * testTopN TopN, cosume time 4851.307342 ms, top'n 45 from vectorSize 210, friendCount 2000, times 10000 per time 0.48513 ms.
 * testTopN TopN, cosume time 6126.567053 ms, top'n 500 from vectorSize 210, friendCount 2000, times 10000 per time 0.612656 ms.
 * testTopN TopN, cosume time 7908.936487 ms, top'n 2000 from vectorSize 210, friendCount 2000, times 10000 per time 0.790893 ms.
 * 
 * @author yangwm Mar 27, 2012 9:12:34 PM
 */
public class TopN {

    /**
     * top n 
     * 
     * @param inputs must order desc 
     * @param n
     * @return result's length <= n 
     */
    public static long[] top(long[][] inputs, int n) {
        int inputsLen = inputs.length;
        
        TreeSet<RowColumn> heap = new TreeSet<RowColumn>();
        for (int i = 0; i < inputsLen; i++) {
            long val = inputs[i][0];
            if (val > 0) { // 值需大于0  
                heap.add(new RowColumn(val, i, 0));
            }
        }

        long[] result = new long[n];
        int resultNum = 0;
        while (resultNum < n) {
            //System.out.println("heap.size():" + heap.size());
            if (heap.isEmpty()) {
                break;
            }
            
            /* 
             * 找到最大值以及所在数据行列 并移除,  
             */
            RowColumn rowColumn = getDeleteMax(heap); // O(1)
            
            long value = rowColumn.value;
            int rowIdx = rowColumn.rowIdx;
            int columnIdx = rowColumn.columnIdx;
            
            /*
             * 当前heap中最大值，对应行号的列号指针后移 
             * 并保存新的最大值到heap中 
             */
            if (columnIdx < inputs[rowIdx].length - 1) {
                columnIdx++; // 指针后移 
                
                long val = inputs[rowIdx][columnIdx]; // 将指针后移之后的数据加入到堆中，待比较
                if (val > 0) {
                    heap.add(new RowColumn(val, rowIdx, columnIdx)); // O(lg*m) 
                }
            } 

            /*
             * save result 
             */
            result[resultNum] = value;
            resultNum++;
        }
        
        return result;
    }
    
    private static RowColumn getDeleteMax(TreeSet<RowColumn> heap) {
        return heap.pollLast();
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
