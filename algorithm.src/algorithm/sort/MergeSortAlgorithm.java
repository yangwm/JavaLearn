/**
 * 
 */
package algorithm.sort;


/**
 * A merge sort demonstration algorithm
 * 
 * http://en.wikipedia.org/wiki/Merge_sort
 * http://www.itl.nist.gov/div897/sqg/dads/HTML/mergesort.html
 * 
 * @author yangwm Aug 1, 2010 12:03:23 PM
 */
public class MergeSortAlgorithm {

    public static long[] mergeSort(long[] number) {
        long[] result = null;
        
        if (number.length <= 1) {
            result = number;
        } else {
            int middle = number.length / 2;

            /*
             * Partition the list into two lists and sort them recursively
             */
            long[] left = new long[middle];
            for (int i = 0; i < middle; i++) {
                left[i] = number[i];
            }
            long[] right = new long[number.length - middle];
            for (int i = 0; i < number.length - middle; i++) {
                right[i] = number[middle + i];
            }

            /*
             * Merge the two sorted lists
             */
            left = mergeSort(left);
            right = mergeSort(right);
            result = merge(left, right);
        }

        return result;
    }
    
    public static long[] merge(long left[], long right[]) {
        return merge(left, right, left.length + right.length);
    }

    public static long[] merge(long[] left, long[] right, int n) {
        int len = (left.length + right.length) < n ? (left.length + right.length) : n;
        long[] result = new long[len];

        int leftPos = 0;
        int rightPos = 0;
        int resultPos = 0;
        while (resultPos < result.length && leftPos < left.length && rightPos < right.length) {
            /* Merge sorted sublists */
            if (left[leftPos] <= right[rightPos]) {
                result[resultPos++] = left[leftPos++];
            } else {
                result[resultPos++] = right[rightPos++];
            }
        }
        
        if (resultPos < result.length) {
            if (leftPos < left.length) {
                /* append left to result */
                while (resultPos < result.length && leftPos < left.length) {
                    result[resultPos++] = left[leftPos++];
                }
            } else {
                /* append right to result */
                while (resultPos < result.length && rightPos < right.length) {
                    result[resultPos++] = right[rightPos++];
                }
            }
        }

        return result;
    }
    
}

/*
[0, 0, 1, 2, 2, 3, 4, 6, 7, 8, 9, 10, 11, 15]
[0, 0, 1, 2, 2, 3, 4, 6, 7, 8, 9, 10, 11, 15]

*/

