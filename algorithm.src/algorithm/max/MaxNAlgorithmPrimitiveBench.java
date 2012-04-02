/**
 * 
 */
package algorithm.max;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import algorithm.ArrayUtil;

/**
 * Max N Algorithm Primitive Bench
 * 
 * @author yangwm Nov 1, 2010 9:25:14 PM
 */
public class MaxNAlgorithmPrimitiveBench {

    /**
     * @param args
     */
    public static void main(String[] args) {
        /*
         * 
         */
        int inputTotal = 2000 * 200;
        int n = 1 * 2000;
        
        /*
         * test data
         */
        List<Long> inputList = new ArrayList<Long>(inputTotal);
        for (int i = 0; i < inputTotal; i++) {
            inputList.add(new Long(i));
        }
        Collections.shuffle(inputList);
        System.out.println("test begin inputTotal: " + inputTotal + ", n: " + n);
        System.out.println("test data inputList.size(): " + inputList.size());
        
        
        //-----------------------------------------------------------------

        long[] inputArray = new long[inputList.size()];
        for (int i = 0; i < inputArray.length; i++) {
            inputArray[i] = inputList.get(i);
        }
        System.out.println("-------JDK Arrays.sort max n-------");
        
        /*
         * max and test print
         */
        long begin = System.currentTimeMillis();
        Arrays.sort(inputArray);
        long[] resultArray = inputArray;
        ArrayUtil.reverse(resultArray);
        long end = System.currentTimeMillis();
        System.out.println("cosume: " + (end - begin) + " ms");
        System.out.println("resultArray.length: " + resultArray.length);
        System.out.println("resultArray of first ten : " + Arrays.toString(Arrays.copyOf(resultArray, 10)));


        //-----------------------------------------------------------------

        inputArray = new long[inputList.size()];
        for (int i = 0; i < inputArray.length; i++) {
            inputArray[i] = inputList.get(i);
        }
        System.out.println("-------MaxNAlgorithmPrimitiveBench Array top n-------");
        
        /*
         * max and test print
         */
        begin = System.currentTimeMillis();
        resultArray = MaxNAlgorithmPrimitive.max(inputArray, n);
        end = System.currentTimeMillis();
        System.out.println("cosume: " + (end - begin) + " ms");
        System.out.println("resultArray.length: " + resultArray.length);
        System.out.println("resultArray of first ten : " + Arrays.toString(Arrays.copyOf(resultArray, 10)));


        //-----------------------------------------------------------------

        inputArray = new long[inputList.size()];
        for (int i = 0; i < inputArray.length; i++) {
            inputArray[i] = inputList.get(i);
        }
        System.out.println("-------TopNAlgorithmBench Array top n-------");
        
        /*
         * max and test print
         */
        begin = System.currentTimeMillis();
        resultArray = TopNAlgorithm.findTopNOrder(inputArray, n);
        end = System.currentTimeMillis();
        System.out.println("cosume: " + (end - begin) + " ms");
        System.out.println("resultArray.length: " + resultArray.length);
        System.out.println("resultArray of first ten : " + Arrays.toString(Arrays.copyOf(resultArray, 10)));

    }
    
}

/*
1.1 
test begin inputTotal: 400000, n: 2000
test data inputList.size(): 400000
-------JDK Arrays.sort max n-------
cosume: 128 ms
resultArray.length: 400000
resultArray of first ten : [399999, 399998, 399997, 399996, 399995, 399994, 399993, 399992, 399991, 399990]
-------MaxNAlgorithmPrimitiveBench Array top n-------
cosume: 40 ms
resultArray.length: 2000
resultArray of first ten : [399999, 399998, 399997, 399996, 399995, 399994, 399993, 399992, 399991, 399990]
-------TopNAlgorithmBench Array top n-------
cosume: 17 ms
resultArray.length: 2000
resultArray of first ten : [399999, 399998, 399997, 399996, 399995, 399994, 399993, 399992, 399991, 399990]

1.2
test begin inputTotal: 400000, n: 20000
test data inputList.size(): 400000
-------JDK Arrays.sort max n-------
cosume: 111 ms
resultArray.length: 400000
resultArray of first ten : [399999, 399998, 399997, 399996, 399995, 399994, 399993, 399992, 399991, 399990]
-------MaxNAlgorithmPrimitiveBench Array top n-------
cosume: 687 ms
resultArray.length: 20000
resultArray of first ten : [399999, 399998, 399997, 399996, 399995, 399994, 399993, 399992, 399991, 399990]
-------TopNAlgorithmBench Array top n-------
cosume: 15 ms
resultArray.length: 20000
resultArray of first ten : [399999, 399998, 399997, 399996, 399995, 399994, 399993, 399992, 399991, 399990]


2.1 
test begin inputTotal: 10000000, n: 10
test data inputList.size(): 10000000
-------JDK Arrays.sort max n-------
cosume: 1781 ms
resultArray.length: 10
resultArray of first ten : [9999999, 9999998, 9999997, 9999996, 9999995, 9999994, 9999993, 9999992, 9999991, 9999990]
-------MaxNAlgorithmPrimitiveBench Array top n-------
cosume: 78 ms
resultArray.length: 10
resultArray of first ten : [9999999, 9999998, 9999997, 9999996, 9999995, 9999994, 9999993, 9999992, 9999991, 9999990]
-------TopNAlgorithmBench Array top n-------
cosume: 141 ms
resultArray.length: 10
resultArray of first ten : [9999996, 9999998, 9999999, 9999997, 9999991, 9999993, 9999992, 9999994, 9999995, 9999990]

2.2 
test begin inputTotal: 10000000, n: 1000
test data inputList.size(): 10000000
-------JDK Arrays.sort max n-------
cosume: 2438 ms
resultArray.length: 1000
resultArray of first ten : [9999999, 9999998, 9999997, 9999996, 9999995, 9999994, 9999993, 9999992, 9999991, 9999990]
-------MaxNAlgorithmPrimitiveBench Array top n-------
cosume: 98 ms
resultArray.length: 1000
resultArray of first ten : [9999999, 9999998, 9999997, 9999996, 9999995, 9999994, 9999993, 9999992, 9999991, 9999990]
-------TopNAlgorithmBench Array top n-------
cosume: 173 ms
resultArray.length: 1000
resultArray of first ten : [9999261, 9999801, 9999628, 9999277, 9999765, 9999702, 9999619, 9999883, 9999468, 9999263]

2.3 
test begin inputTotal: 10000000, n: 10000
test data inputList.size(): 10000000
-------JDK Arrays.sort max n-------
cosume: 1927 ms
resultArray.length: 10000
resultArray of first ten : [9999999, 9999998, 9999997, 9999996, 9999995, 9999994, 9999993, 9999992, 9999991, 9999990]
-------MaxNAlgorithmPrimitiveBench Array top n-------
cosume: 454 ms
resultArray.length: 10000
resultArray of first ten : [9999999, 9999998, 9999997, 9999996, 9999995, 9999994, 9999993, 9999992, 9999991, 9999990]
-------TopNAlgorithmBench Array top n-------
cosume: 144 ms
resultArray.length: 10000
resultArray of first ten : [9990400, 9996025, 9997419, 9990962, 9994948, 9992219, 9994479, 9998738, 9999679, 9996839]

*/

