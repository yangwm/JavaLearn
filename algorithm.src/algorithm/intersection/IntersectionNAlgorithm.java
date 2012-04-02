/**
 * 
 */
package algorithm.intersection;

import java.util.Arrays;

/**
 * intersection n algorithm 
 * 
 * @author yangwm Oct 21, 2010 9:35:14 PM
 */
public class IntersectionNAlgorithm {
    
    /**
     * sort + intersection + n 
     * 
     * @param left
     * @param right
     * @return
     */
    public static int[] intersection(int[] left, int[] right, int n) {
        /*
         * if left size greater than right size then swap of address 
         * if n greater than left size, set left size to n 
         */
        if (left.length > right.length) {
            int[] swap = left;
            left = right;
            right = swap;
        }
        if (n > left.length) {
            n = left.length;
        }
        //System.out.println(left.length + ", " + right.length + ", " + n);
        
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
        while (rightIdx < right.length && resultIdx < n) {
            int rightValue = right[rightIdx];
            rightIdx++;
            
            int resultValue = Arrays.binarySearch(left, rightValue);
            if (resultValue > -1) {
                result[resultIdx] = rightValue;
                resultIdx++;
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
        int[] left = { 5, 9, 2, 8, 11 };//{ 5, 9, 2, 8, 11, 7, 18, 10, 12, 3 };
        int[] right = { 2, 7, 6, 8, 9, 18, 11, 5, 3, 20 };
        int n = 3;
        System.out.println("------IntersectionNAlgorithm Array intersection n-------");

        /*
         * intersection and test print
         */
        long begin = System.nanoTime();
        int[] result = intersection(left, right, n);
        long end = System.nanoTime();
        System.out.println("cosume: " + (end - begin) + " ns");
        System.out.println(Arrays.toString(result));
        
    }

}

/*
------IntersectionNAlgorithm Array intersection n-------
cosume: 247518 ns
[2, 8, 9]

*/
