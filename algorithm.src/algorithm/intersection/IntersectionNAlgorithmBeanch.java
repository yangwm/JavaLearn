/**
 * 
 */
package algorithm.intersection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * intersection n algorithm beanch
 * 
 * @author yangwm Oct 21, 2010 9:50:18 PM
 */
public class IntersectionNAlgorithmBeanch {

    /**
     * @param args
     */
    public static void main(String[] args) {
        /*
         * 
         */
        int leftTotal = 2000;
        int rightTotal = 1000000;
        int factor = new Random().nextInt(5) + 1;
        int n = 100;
        
        /*
         * test data
         */
        List<Integer> leftList = new ArrayList<Integer>(leftTotal);
        for (int i = 0; i < leftTotal; i++) {
            leftList.add(i * factor);
        }
        Collections.shuffle(leftList);
        List<Integer> rightList = new ArrayList<Integer>(rightTotal);
        for (int i = 0; i < rightTotal; i++) {
            rightList.add(i);
        }
        Collections.shuffle(rightList);
        System.out.println("test begin leftTotal: " + leftTotal + ", rightTotal: " + rightTotal + ", n: " + n);
        System.out.println("test data leftList.size(): " + leftList.size() + ", rightList.size(): " + rightList.size());

        //-----------------------------------------------------------------

        int[] leftArray = new int[leftList.size()];
        for (int i = 0; i < leftArray.length; i++) {
            leftArray[i] = leftList.get(i);
        }
        int[] rightArray = new int[rightList.size()];
        for (int i = 0; i < rightArray.length; i++) {
            rightArray[i] = rightList.get(i);
        }
        System.out.println("-------IntersectionNAlgorithm Array intersection n-------");
        
        /*
         * intersection and test print
         */
        long begin = System.currentTimeMillis();
        int[] resultArray = IntersectionNAlgorithm.intersection(leftArray, rightArray, n);
        long end = System.currentTimeMillis();
        System.out.println("cosume: " + (end - begin) + " ms");
        System.out.println("resultArray.length: " + resultArray.length);
        System.out.println("resultArray of first ten : " + Arrays.toString(Arrays.copyOf(resultArray, 10)));
        
    }

}

/*
vm args: -server -Xms256m -Xmx256m -XX:PermSize=64m
test begin leftTotal: 2000, rightTotal: 1000000, factor: 1000, n: 100
test data leftList.size(): 2000, rightList.size(): 1000000
-------IntersectionNAlgorithm Array intersection n-------
cosume: 15 ms
resultArray.length: 100
resultArray of first ten : [7628, 7980, 2552, 1716, 4116, 4920, 1700, 4456, 5384, 2308]

*/

