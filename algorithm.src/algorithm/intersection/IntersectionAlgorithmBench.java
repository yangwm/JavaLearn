/**
 * 
 */
package algorithm.intersection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * sort intersection algorithm bench 
 * 
 * @author yangwm Oct 19, 2010 1:59:16 PM
 */
public class IntersectionAlgorithmBench {

    /**
     * @param args
     */
    public static void main(String[] args) {
        /*
         * 
         */
        int leftTotal = 2000;
        int rightTotal = 1000000;
        int factor = (rightTotal / leftTotal) * (new Random().nextInt(5) + 1);
        
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
        

        //---------------------------------------------------------
        
        List<Integer> left = new ArrayList<Integer>(leftList);
        List<Integer> right = new ArrayList<Integer>(rightList);
        System.out.println("-----------------JDK List retainAll-----------------");
        
        /*
         * intersection and test print
         */
        long begin = System.currentTimeMillis();
        left.retainAll(right);
        long end = System.currentTimeMillis();
        System.out.println("cosume: " + (end - begin) + " ms");
        System.out.println("left.size(): " + left.size());
        
        
        //-----------------------------------------------------------------

        left = new ArrayList<Integer>(leftList);
        right = new ArrayList<Integer>(rightList);
        System.out.println("------SortSortIntersectionAlgorithm List intersection-------");
        
        /*
         * intersection and test print
         */
        begin = System.currentTimeMillis();
        List<Integer> result = SortSortIntersectionAlgorithm.intersection(left, right);
        end = System.currentTimeMillis();
        System.out.println("cosume: " + (end - begin) + " ms");
        System.out.println("result.size(): " + result.size());
        
        
        //-----------------------------------------------------------------

        int[] leftArray = new int[leftList.size()];
        for (int i = 0; i < leftArray.length; i++) {
            leftArray[i] = leftList.get(i);
        }
        int[] rightArray = new int[rightList.size()];
        for (int i = 0; i < rightArray.length; i++) {
            rightArray[i] = rightList.get(i);
        }
        System.out.println("------SortSortIntersectionAlgorithm Array intersection-------");
        
        /*
         * intersection and test print
         */
        begin = System.currentTimeMillis();
        int[] resultArray = SortSortIntersectionAlgorithm.intersection(leftArray, rightArray);
        end = System.currentTimeMillis();
        System.out.println("cosume: " + (end - begin) + " ms");
        System.out.println("resultArray.length: " + resultArray.length);
        
        
        //-----------------------------------------------------------------

        left = new ArrayList<Integer>(leftList);
        right = new ArrayList<Integer>(rightList);
        System.out.println("------SortIntersectionAlgorithm List intersection-------");
        
        /*
         * intersection and test print
         */
        begin = System.currentTimeMillis();
        result = SortIntersectionAlgorithm.intersection(left, right);
        end = System.currentTimeMillis();
        System.out.println("cosume: " + (end - begin) + " ms");
        System.out.println("result.size(): " + result.size());
        

        //-----------------------------------------------------------------

        leftArray = new int[leftList.size()];
        for (int i = 0; i < leftArray.length; i++) {
            leftArray[i] = leftList.get(i);
        }
        rightArray = new int[rightList.size()];
        for (int i = 0; i < rightArray.length; i++) {
            rightArray[i] = rightList.get(i);
        }
        System.out.println("-------SortIntersectionAlgorithm Array intersection-------");
        
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

/*

vm args: -server -Xms256m -Xmx256m -XX:PermSize=64m(使用left列表移除元素)
test begin leftTotal: 2000, rightTotal: 1000000, factor: 1500
test data leftList.size(): 2000, rightList.size(): 1000000
-----------------JDK List retainAll-----------------
cosume: 31640 ms
left.size(): 667
------SortSortIntersectionAlgorithm List intersection-------
cosume: 687 ms
result.size(): 667
------SortSortIntersectionAlgorithm Array intersection-------
cosume: 234 ms
resultArray.length: 667
------SortIntersectionAlgorithm List intersection-------
1333, 1000000, 667
cosume: 313 ms
result.size(): 667
-------SortIntersectionAlgorithm Array intersection-------
cosume: 125 ms
resultArray.length: 667


vm args: -server -Xms256m -Xmx256m -XX:PermSize=64m(未使用left列表移除元素)
test begin leftTotal: 2000, rightTotal: 1000000, factor: 1500
test data leftList.size(): 2000, rightList.size(): 1000000
-----------------JDK List retainAll-----------------
cosume: 31797 ms
left.size(): 667
------SortSortIntersectionAlgorithm List intersection-------
cosume: 828 ms
result.size(): 667
------SortSortIntersectionAlgorithm Array intersection-------
cosume: 219 ms
resultArray.length: 667
------SortIntersectionAlgorithm List intersection-------
cosume: 312 ms
result.size(): 667
-------SortIntersectionAlgorithm Array intersection-------
cosume: 125 ms
resultArray.length: 667

*/
