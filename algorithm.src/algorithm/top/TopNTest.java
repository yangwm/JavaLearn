/**
 * 
 */
package algorithm.top;

import java.util.Arrays;

import org.junit.Test;

/**
 * TopN Test
 * 
 * @author yangwm Mar 28, 2012 10:16:36 PM
 */
public class TopNTest {
    
    @Test
    public void testTopN() {
        long[][] values = getValues();
        
        long[] result = TopN.top(values, 2000);
        System.out.println("result length:" + result.length + ", result" + Arrays.toString(result));
        
        result = TopN.top(values, 30);
        System.out.println("result length:" + result.length + ", result" + Arrays.toString(result));
        
        result = TopN.top(values, 20);
        System.out.println("result length:" + result.length + ", result" + Arrays.toString(result));
    }
    
    private static long[][] getValues() {
        long[] input1 = new long[] { 120, 98, 95, 9, 8, 7, 5, 4, 3, 2 };
        long[] input2 = new long[] { 166, 45, 35, 34, 33, 25, 24, 23, 22, 21 };
        long[] input3 = new long[] { 150, 145, 135, 134, 17, 15, 14, 13, 12, 11 };
        long[][] inputs = new long[3][];
        inputs[0] = input1;
        inputs[1] = input2;
        inputs[2] = input3;
        
        return inputs;
    }

}
