/**
 * 
 */
package benchmark;

import java.util.Arrays;
import java.util.Random;

/**
 * Benchmark Util
 * 
 * @author yangwm Nov 10, 2011 6:32:16 PM
 */
public class BenchmarkUtil {

    public static int DEFAULT_RETURN_COUNT = 20;
    public static int MAX_FRIEND_COUNT = 2000;
    private static Random rand = new Random();
    
    public static final long[] getRandom200() {
        long[] _values = new long[200];
        for (int i = 0; i < 200; i++)
            _values[i] = rand.nextInt(Integer.MAX_VALUE);
        
        return _values;
        
    }
    
    public static long[] getIdsFrmVector(int friendCount) {     
        long[] dest = new long[200 * friendCount];
        int pos = 0;
        for (int i = 0; i < friendCount; i++) {
            try {
                long[] vitem = getRandom200();
                System.arraycopy(vitem, 0, dest, pos, vitem.length);               
                pos += vitem.length;
            } catch (Exception ex) {
                ex.printStackTrace();
                continue;
            }
        }
        long[] totalIds = new long[pos];
        System.arraycopy(dest, 0, totalIds, 0, pos);
        return totalIds;
    }
    
    /**
     * ids需要为升序排列
     * @param ids
     * @param idsLen
     * @param sinceId
     * @param maxId
     * @param count2
     * @param page
     * @return
     */
    public static long[] paginationAndReverse(long[] ids, int idsLen, long sinceId, long maxId, int count, int page){
        int[] pos = pagination(ids, idsLen, sinceId, maxId, count, page);
        if (pos == null) {
            return new long[]{}; 
        }

        int offset = pos[0];
        int limit = pos[1];
        if (limit <= 0) {
            return new long[]{}; 
        }
        
        long[] results = new long[limit];
        System.arraycopy(ids, offset, results, 0, limit);
        reverse(results);
        return results;
    }
    
    /**
     * 分页, ids需要是升序排列的，不包含sinceId，包含maxId
     * @param ids： id数组
     * @param sinceId：查询的起始id 
     * @param maxId：查询的最大id
     * @param pageSize 每页数量
     * @param page：查询的页数
     * @return 数组, 0- offset, 1- limit count, 类似mysql分页参数
     */
    public static int[] pagination(long[] ids, int idsLen, long sinceId, long maxId, int pageSize, int page) {
        // TODO, verify the algorithm
        int count = pageSize;
        if (count == 0) count = DEFAULT_RETURN_COUNT;
        
        int[] results = new int[2];
        int pos = idsLen;
        
        int startPos = 0;
        int endPos = pos;
        
        // src从小到大排序
        if (sinceId != 0) { // 比如src=101-110, since=102,max=108
            startPos = -1;
            for (int i = 0; i < idsLen; i++) {
                if (ids[i] > sinceId) {
                    startPos = i;
                    break;
                }
            }
            if (startPos == -1) { // not found?             
                //ApiLogger.warn("Illegal startPos, since/maxid: " + sinceId + "/" + maxId);
                return null;
            }
        }
        if (maxId != 0) {           
            for (int i = 0; i < idsLen; i++) {
                if (ids[i] > maxId) {
                    endPos = i;
                    break;
                }
            }
            if (endPos == 0) { // not found?
                return null;
            }
        }
        
        if (startPos >= endPos) {
            if(startPos != 0){
            }           
            return null;
        }

        // 由于ids现在是从小到大排列，但是业务需要从大到小取，所以需要反着取，注意startPos计算。
        int trueCount = endPos - startPos;
        if (count > trueCount)
            count = trueCount;
        if (page > 0) {
            int pageCount = (trueCount - 1) / count + 1;// 页数
            if (page > pageCount)
                return null;
            
            // startPos = (page - 1) * count; // 正序取法
            // 反序，比如 endPos = 100, trueCount = 10, count = 2, page = 2, page = 5, startPos = 96 (page1: 98,99, P2: 96,97)
            int pageStartPos = endPos - page * count;
            if(pageStartPos < 0){
                //pageStartPos < 0, 说明起始点需要从0开始，而count也小于pageSize
                pageStartPos = 0;
                count = endPos - (page - 1) * count;
            }
            //如果取最后一页，则startPos不能从pageStartPos取，而要从第一个不小于sinceId的id位置取, fish 2010.2.24
            if(startPos < pageStartPos){
                startPos = pageStartPos;
            }else{
                count = count - (startPos - pageStartPos);
            }           
            //count = endPos - (page - 1) * count;
            // endPos = startPos + count;
        } else{
            startPos = endPos - count;
            if(startPos < 0){
                startPos = 0;
                count = endPos;
            }
        }
        
        results[0] = startPos;
        results[1] = count;     
        return results;
    }
    
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
    
    public static void main(String[] args) throws Exception {
        long[] ids = getIdsFrmVector(MAX_FRIEND_COUNT);
        Arrays.sort(ids);
        long[] pageIds = paginationAndReverse(ids, ids.length, 0, 0, 20, 1);
        System.out.println(Arrays.toString(pageIds));
    }
    
}
