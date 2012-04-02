/**
 * 
 */
package algorithm.intersection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * sort intersection algorithm
 * 
 * @author yangwm Oct 19, 2010 1:09:12 PM
 */
public class SortIntersectionAlgorithm {
    
    /**
     * sort + sort + intersection
     * 
     * @param left
     * @param right
     * @return
     */
    public static List<Integer> intersection(List<Integer> left, List<Integer> right) {
        /*
         * if left size greater than right size then swap of address 
         */
        if (left.size() > right.size()) {
            List<Integer> swap = left;
            left = right;
            right = swap;
        }
        
        int leftSize = left.size();
        int rightSize = right.size();
        //System.out.println(leftSize + ", " + rightSize);

        /*
         * sort 
         */
        Collections.sort(left);
        
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
        int rightIdx = 0;
        while (left.size() > 0 && rightIdx < rightSize) { // 
            int rightValue = right.get(rightIdx);
            rightIdx++;
            
            int resultValue = Collections.binarySearch(left, rightValue);
            if (resultValue > -1) {
                result.add(rightValue);
                
                /* reduce left 
                left.remove(resultValue); */
            }
        }
        //System.out.println(left.size() + ", " + rightIdx + ", " + result.size());

        /*
         * actual size of result 
         */
        return result;
    }
    
    /**
     * sort + intersection
     * 
     * @param left
     * @param right
     * @return
     */
    public static int[] intersection(int[] left, int[] right) {
        /*
         * if left size greater than right size then swap of address 
         */
        if (left.length > right.length) {
            int[] swap = left;
            left = right;
            right = swap;
        }
        //System.out.println(left.length + ", " + right.length);
        
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
        
        /*
         * intersection calculate 
         */
        int rightIdx = 0;
        int resultIdx = 0;
        while (left.length > 0 && rightIdx < right.length) { // 
            int rightValue = right[rightIdx];
            rightIdx++;
            
            int resultValue = Arrays.binarySearch(left, rightValue);
            if (resultValue > -1) {
                result[resultIdx] = rightValue;
                resultIdx++;
                
                /* reduce left
                int numMoved = left.length - resultValue - 1;
                //System.out.println(left.length + ", " + resultValue + ", " + numMoved);
                if (numMoved > 0) {
                    System.arraycopy(left, resultValue+1, left, resultValue, numMoved);
                    
                    int[] leftTemp = new int[left.length - 1];
                    System.arraycopy(left, 0, leftTemp, 0, leftTemp.length);
                    left = leftTemp;
                } else if(numMoved == 0) {
                    left = new int[0];
                } */
            }
        }
        //System.out.println(left.length + ", " + rightIdx + ", " + resultIdx);

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
0, 8, 5
cosume: 1096787 ns
[2, 8, 9, 11, 5]
------SortSortIntersectionAlgorithm Array intersection-------
cosume: 220978 ns
[2, 8, 9, 11]

*/
