/**
 * 
 */
package algorithm.intersection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * sort sort intersection algorithm 
 * 
 * @author yangwm Oct 19, 2010 1:09:12 PM
 */
public class SortSortIntersectionAlgorithm {
    
    /**
     * sort + sort + intersection
     * 
     * @param left
     * @param right
     * @return
     */
    public static List<Integer> intersection(List<Integer> left, List<Integer> right) {
        int leftSize = left.size();
        int rightSize = right.size();

        /*
         * sort 
         */
        Collections.sort(left);
        Collections.sort(right);
        
        /*
         * allocation maximum space for result 
         */
        List<Integer> result = null;
        if (leftSize < rightSize) {
            result = new ArrayList<Integer>(leftSize);
        } else {
            result = new ArrayList<Integer>(rightSize);
        }
        
        /*
         * intersection calculate 
         */
        int leftIdx = 0;
        int rightIdx = 0;
        while (leftIdx < leftSize && rightIdx < rightSize) {
            if (left.get(leftIdx) < right.get(rightIdx)) {
                leftIdx++;
            } else if (left.get(leftIdx) > right.get(rightIdx)) {
                rightIdx++;
            } else { // == 
                result.add(left.get(leftIdx)); //result.set(resultIdx, left.get(leftIdx)); 
                leftIdx++;
                rightIdx++;
            }
        }

        /*
         * actual size of result 
         */
        return result;
    }
    
    
    /**
     * sort + sort + intersection
     * 
     * @param left
     * @param right
     * @return
     */
    public static int[] intersection(int[] left, int[] right) {
        /*
         * allocation maximum space for result 
         */
        int[] result = null;
        if (left.length < right.length) {
            result = new int[left.length];
        } else {
            result = new int[right.length];
        }
        
        /*
         * sort 
         */
        Arrays.sort(left);
        Arrays.sort(right);
        
        /*
         * intersection calculate 
         */
        int leftIdx = 0;
        int rightIdx = 0;
        int resultIdx = 0;
        while (leftIdx < left.length && rightIdx < right.length) {
            if (left[leftIdx] < right[rightIdx]) {
                leftIdx++;
            } else if (left[leftIdx] > right[rightIdx]) {
                rightIdx++;
            } else { // == 
                result[resultIdx] = left[leftIdx];
                leftIdx++;
                rightIdx++;
                resultIdx++;
            }
        }

        /*
         * actual size of result 
         */
        return Arrays.copyOf(result, resultIdx);
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        List<Integer> left = new ArrayList<Integer>(Arrays.<Integer>asList(new Integer[] { 5, 9, 2, 8, 11 }));
        List<Integer> right = new ArrayList<Integer>(Arrays.<Integer>asList(new Integer[] { 2, 7, 6, 8, 9, 18, 11, 5, 3, 20 }));
        System.out.println("------SortSortIntersectionAlgorithm List intersection-------");

        /*
         * intersection and test print
         */
        long begin = System.nanoTime();
        List<Integer> result = intersection(left, right);
        long end = System.nanoTime();
        System.out.println("cosume: " + (end - begin) + " ns");
        System.out.println(result);
        

        //---------------------------------------------------------
        
        int[] leftArray = { 5, 9, 2, 8, 11 };
        int[] rightArray = { 2, 7, 6, 8, 9, 18, 11, 5, 3, 20 };
        System.out.println("------SortSortIntersectionAlgorithm Array intersection-------");

        /*
         * intersection and test print
         */
        begin = System.nanoTime();
        int[] resultArray = intersection(leftArray, rightArray);
        end = System.nanoTime();
        System.out.println("cosume: " + (end - begin) + " ns");
        System.out.println(Arrays.toString(resultArray));
        
    }
}

/*
------SortSortIntersectionAlgorithm List intersection-------
cosume: 18086935 ns
[2, 5, 8, 9, 11]
------SortSortIntersectionAlgorithm Array intersection-------
cosume: 389994 ns
[2, 5, 8, 9, 11]

*/
