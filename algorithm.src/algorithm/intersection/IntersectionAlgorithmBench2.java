/**
 * 
 */
package algorithm.intersection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * sort intersection algorithm bench 2 
 * 
 * @author yangwm Oct 19, 2010 1:59:16 PM
 */
public class IntersectionAlgorithmBench2 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        /*
         * 
         */
        int leftTotal  = 1000000;
        int rightTotal = 1000000;
        int factor = new Random().nextInt(5) + 1;
        
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
        System.out.println("test begin leftTotal: " + leftTotal + ", rightTotal: " + rightTotal + ", factor: " + factor);
        System.out.println("test data leftList.size(): " + leftList.size() + ", rightList.size(): " + rightList.size());

        int[] leftArray = null;
        int[] rightArray = null;
        int[] resultArray = null;
        long begin = 0;
        long end = 0;
        
        //-----------------------------------------------------------------
        System.out.println("------SortSortIntersectionAlgorithm Array intersection-------");
        for (int time = 0; time < 5; time++) {
            leftArray = new int[leftList.size()];
            for (int i = 0; i < leftArray.length; i++) {
                leftArray[i] = leftList.get(i);
            }
            rightArray = new int[rightList.size()];
            for (int i = 0; i < rightArray.length; i++) {
                rightArray[i] = rightList.get(i);
            }
            
            /*
             * intersection and test print
             */
            begin = System.currentTimeMillis();
            resultArray = SortSortIntersectionAlgorithm.intersection(leftArray, rightArray);
            end = System.currentTimeMillis();
            System.out.println("cosume: " + (end - begin) + " ms");
            System.out.println("resultArray.length: " + resultArray.length);
        }
        
        
        //-----------------------------------------------------------------
        System.out.println("-------SortIntersectionAlgorithm Array intersection-------");
        for (int time = 0; time < 5; time++) {
            leftArray = new int[leftList.size()];
            for (int i = 0; i < leftArray.length; i++) {
                leftArray[i] = leftList.get(i);
            }
            rightArray = new int[rightList.size()];
            for (int i = 0; i < rightArray.length; i++) {
                rightArray[i] = rightList.get(i);
            }
            
            /*
             * intersection and test print
             */
            begin = System.currentTimeMillis();
            resultArray = SortIntersectionAlgorithm.intersection(leftArray, rightArray);
            end = System.currentTimeMillis();
            System.out.println("cosume: " + (end - begin) + " ms");
            System.out.println("resultArray.length: " + resultArray.length);
        }
        
    }

}

/*

vm args: -server -Xms256m -Xmx256m -XX:PermSize=64m(未使用left列表移除元素)
test begin leftTotal: 1000000, rightTotal: 1000000, factor: 3
test data leftList.size(): 1000000, rightList.size(): 1000000
------SortSortIntersectionAlgorithm Array intersection-------
cosume: 406 ms
resultArray.length: 333334
cosume: 454 ms
resultArray.length: 333334
cosume: 313 ms
resultArray.length: 333334
cosume: 313 ms
resultArray.length: 333334
cosume: 422 ms
resultArray.length: 333334
-------SortIntersectionAlgorithm Array intersection-------
1000000, 1000000
cosume: 421 ms
resultArray.length: 333334
1000000, 1000000
cosume: 422 ms
resultArray.length: 333334
1000000, 1000000
cosume: 437 ms
resultArray.length: 333334
1000000, 1000000
cosume: 391 ms
resultArray.length: 333334
1000000, 1000000
cosume: 375 ms
resultArray.length: 333334

*/
