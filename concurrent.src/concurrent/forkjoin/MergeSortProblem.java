/**
 * 
 */
package concurrent.forkjoin;

/**
 * 
 * 
 * @author yangwm Mar 24, 2011 11:39:42 PM
 */
public class MergeSortProblem {
    
    public final int[] number;
    
    public MergeSortProblem(int[] number) {
        this.number = number;
    }
    
    public MergeSortProblem subproblem(int subStart, int subEnd) {
        return new MergeSortProblem(number);
    }

    
    public int[] mergeSort(int number[]) {
        int[] result = null;
        
        if (number.length <= 1) {
            result = number;
        } else {
            int middle = number.length / 2;

            /*
             * Partition the list into two lists and sort them recursively
             */
            int left[] = new int[middle];
            for (int i = 0; i < middle; i++) {
                left[i] = number[i];
            }
            int right[] = new int[number.length - middle];
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

    public int[] merge(int left[], int right[]) {
        int[] result = new int[left.length + right.length];

        int leftPos = 0;
        int rightPos = 0;
        int resultPos = 0;
        while (leftPos < left.length && rightPos < right.length) {
            /* Merge sorted sublists */
            if (left[leftPos] <= right[rightPos]) {
                result[resultPos++] = left[leftPos++];
            } else {
                result[resultPos++] = right[rightPos++];
            }
        }

        if (leftPos < left.length) {
            /* append left to result */
            while (leftPos < left.length) {
                result[resultPos++] = left[leftPos++];
            }
        } else {
            /* append right to result */
            while (rightPos < right.length) {
                result[resultPos++] = right[rightPos++];
            }
        }

        return result;
    }

}
