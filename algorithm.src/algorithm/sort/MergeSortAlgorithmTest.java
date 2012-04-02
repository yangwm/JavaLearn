package algorithm.sort;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

/**
 * Merge Sort Algorithm Test
 * 
 * @author yangwm Mar 8, 2012 2:21:06 PM
 */
public class MergeSortAlgorithmTest {

    @Test
    public void testMerge() {
        long[] inputArray1 = { 2, 2, 4, 6, 8, 15 };
        long[] inputArray2 = { 1, 3, 5, 5, 7, 9, 10, 11 };
        long[] expectArray = new long[] { 1, 2, 2, 3, 4, 5, 5, 6, 7, 8, 9, 10, 11, 15 };
        
        int resultLen = 5;
        long[] result = MergeSortAlgorithm.merge(inputArray1, inputArray2);
        if (result.length > resultLen) {
            result = Arrays.copyOfRange(result, result.length - resultLen, result.length);
        }
        System.out.println(Arrays.toString(result));
        Assert.assertArrayEquals(expectArray, result);
        
        resultLen = inputArray1.length;
        result = MergeSortAlgorithm.merge(inputArray1, inputArray2, resultLen);
        System.out.println(Arrays.toString(result));
        Assert.assertArrayEquals(Arrays.copyOf(result, resultLen), result);
        
        resultLen = inputArray2.length;
        result = MergeSortAlgorithm.merge(inputArray1, inputArray2, resultLen);
        System.out.println(Arrays.toString(result));
        Assert.assertArrayEquals(Arrays.copyOf(result, resultLen), result);


        long[] inputArray = { 1, 2, 2, 3, 4, 5, 5, 6, 7, 8, 9, 10, 11, 15 };
        result = MergeSortAlgorithm.mergeSort(inputArray);
        System.out.println(Arrays.toString(result));
    }

}
