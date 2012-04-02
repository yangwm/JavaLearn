/**
 * 
 */
package algorithm.sort;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

import algorithm.ArrayUtil;

/**
 * 
 * @author yangwm Mar 4, 2012 2:27:15 PM
 */
public class MergeSortAlgorithmBench {
    
    private static Random rand = new Random();
    private static long[] getRandom200() {
        long[] _values = new long[200];
        long randValue = rand.nextInt(Integer.MAX_VALUE);
        for (int i = 0; i < 200; i++) {
            _values[i] = randValue + i;
        }
        Arrays.sort(_values);
        return _values;
    }
    private static long[][] getAllIds() {
        long[][] allIds = new long[2000][];
        for (int i = 0; i < 2000; i++) {
            long[] values = getRandom200();
            allIds[i] = values; 
        }
        //System.out.println(allIds.length);
        return allIds;
    }
    private static final long[][] allIds = getAllIds();
    
    /*
     * testSort cosume time 34
     */
    @Test
    public void testMerge() {
        long[] inputArray1 = getRandom200();
        long[] inputArray2 = getRandom200();
        
        long beginTime = System.nanoTime();
        for (int i = 0; i < 2000; i++) {
            long[] result = MergeSortAlgorithm.merge(inputArray1, inputArray2);
            int resultLength = result.length;
        }
        long cosumeTime = System.nanoTime() - beginTime;
        System.out.println("testSort cosume time " + (cosumeTime/1000000));
    }

    protected static ThreadLocal<long[]> localBuf = new ThreadLocal<long[]>() {
        protected long[] initialValue() {
            return new long[(210) * 2000];
        }
    };
    
    /*
     * get totalIds cosume time 9
     * testGetSortIds cosume time 106, totalIds length:400000,
     */
    @Test
    public void testGetSortIds() {
        long beginTime = System.nanoTime();
        long[] dest = localBuf.get();
        int pos = 0;
        for (int i = 0; i < 2000; i++) {
            long[] values = allIds[i];
            System.arraycopy(values, 0, dest, pos, values.length);               
            pos += values.length;
        }
        long[] totalIds = new long[pos];
        System.arraycopy(dest, 0, totalIds, 0, pos);
        long cosumeTime = System.nanoTime() - beginTime;
        System.out.println("get totalIds cosume time " + (cosumeTime/1000000));
        Arrays.sort(totalIds);
        
        ArrayUtil.reverse(totalIds);
        cosumeTime = System.nanoTime() - beginTime;
        System.out.println("testGetSortIds cosume time " + (cosumeTime/1000000) + ", totalIds length:" + totalIds.length 
                + ", result's max 200:" + Arrays.toString(Arrays.copyOf(totalIds, 200)));
    }

    /*
     * get merge totalIds cosume time 111
     * testGetMergeIds cosume time 112, totalIds length:2000, ...
     */
    @Test
    public void testGetMergeIds() {
        long beginTime = System.nanoTime();
        long[] totalIds = new long[]{};
        int resultLen = 2000;
        for (int i = 0; i < 2000; i++) {
            long[] values = allIds[i];
            totalIds = MergeSortAlgorithm.merge(totalIds, values);
            if (totalIds.length > resultLen) {
                totalIds = Arrays.copyOfRange(totalIds, totalIds.length - resultLen, totalIds.length);
            }
        }
        long cosumeTime = System.nanoTime() - beginTime;
        System.out.println("get merge totalIds cosume time " + (cosumeTime/1000000));
        
        ArrayUtil.reverse(totalIds);
        cosumeTime = System.nanoTime() - beginTime;
        System.out.println("testGetMergeIds cosume time " + (cosumeTime/1000000) + ", totalIds length:" + totalIds.length 
                + ", totalIds's max 200:" + Arrays.toString(Arrays.copyOf(totalIds, 200)));
    }
    
}
