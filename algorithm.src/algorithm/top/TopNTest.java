/**
 * 
 */
package algorithm.top;

import java.util.Arrays;

/**
 * 
 * 
 * @author yangwm Mar 28, 2012 10:16:36 PM
 */
public class TopNTest {
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        long[] input1 = new long[] { 120, 98, 11, 9, 8, 7, 5, 0, 0, 0 };
        long[] input2 = new long[] { 166, 45, 35, 34, 33, 25, 24, 23, 22, 0 };
        long[] input3 = new long[] { 150, 145, 135, 134, 0, 0, 0, 0, 0, 0 };
        long[][] inputs = new long[3][];
        inputs[0] = input1;
        inputs[1] = input2;
        inputs[2] = input3;
        
        long[] result = TopN.top(inputs, 20);
        System.out.println("result length:" + result.length + ", result" + Arrays.toString(result));
    }

}
