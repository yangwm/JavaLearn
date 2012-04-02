/**
 * 
 */
package algorithm.max;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * max n algorithm beanch
 * 
 * @author yangwm Oct 23, 2010 11:58:58 PM
 */
public class MaxNAlgorithmBeanch {

    /**
     * @param args
     */
    public static void main(String[] args) {
        /*
         * 
         */
        int inputTotal = 1000000;
        int n = 100;
        
        /*
         * test data
         */
        List<Integer> inputList = new ArrayList<Integer>(inputTotal);
        for (int i = 0; i < inputTotal; i++) {
            inputList.add(i);
        }
        Collections.shuffle(inputList);
        System.out.println("test begin inputTotal: " + inputTotal + ", n: " + n);
        System.out.println("test data inputList.size(): " + inputList.size());
        
        
        //-----------------------------------------------------------------

        Integer[] inputArray = new Integer[inputList.size()];
        for (int i = 0; i < inputArray.length; i++) {
            inputArray[i] = inputList.get(i);
        }
        System.out.println("-------JDK Arrays.sort max n-------");
        
        /*
         * max and test print
         */
        long begin = System.currentTimeMillis();
        Arrays.sort(inputArray);
        Integer[] resultArray = (Integer[]) Array.newInstance(Integer.class, n);
        for (int i = inputArray.length - 1, resultIdx = 0; i >= inputArray.length - n; i--) {
            resultArray[resultIdx++] = inputArray[i];
        }
        long end = System.currentTimeMillis();
        System.out.println("cosume: " + (end - begin) + " ms");
        System.out.println("resultArray.length: " + resultArray.length);
        System.out.println("resultArray of first ten : " + Arrays.toString(Arrays.copyOf(resultArray, 10)));


        //-----------------------------------------------------------------

        inputArray = new Integer[inputList.size()];
        for (int i = 0; i < inputArray.length; i++) {
            inputArray[i] = inputList.get(i);
        }
        System.out.println("-------MaxNAlgorithm Array max n-------");
        
        /*
         * max and test print
         */
        begin = System.currentTimeMillis();
        resultArray = MaxNAlgorithm.<Integer>max(inputArray, n);
        end = System.currentTimeMillis();
        System.out.println("cosume: " + (end - begin) + " ms");
        System.out.println("resultArray.length: " + resultArray.length);
        System.out.println("resultArray of first ten : " + Arrays.toString(Arrays.copyOf(resultArray, 10)));

    }

}

/*
test begin inputTotal: 1000000, n: 100
test data inputList.size(): 1000000
-------JDK Arrays.sort max n-------
cosume: 969 ms
resultArray.length: 100
resultArray of first ten : [999999, 999998, 999997, 999996, 999995, 999994, 999993, 999992, 999991, 999990]
-------MaxNAlgorithm Array max n-------
cosume: 62 ms
resultArray.length: 100
resultArray of first ten : [999999, 999998, 999997, 999996, 999995, 999994, 999993, 999992, 999991, 999990]

*/
