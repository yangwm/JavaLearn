/**
 * 
 */
package concurrent.forkjoin;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import algorithm.sort.MergeSortAlgorithm;

/**
 * A merge sort demonstration algorithm with the fork-join framework(a parallelizable algorithm)
 * 
 * 数组归并排序: http://blog.csdn.net/yang_net/archive/2010/08/01/5781012.aspx
 * 
 * @author yangwm Aug 12, 2010 11:15:47 AM
 */
public class MergeSortForkJoin extends RecursiveAction {
    /**
     * 
     */
    private static final long serialVersionUID = -7490351302704617127L;
    
    private static final int THRESHOLD = 4;
    private final MergeSortProblem problem;
    private int[] result;
    
    public MergeSortForkJoin(MergeSortProblem problem) {
        this.problem = problem;
    }


    @Override
    protected void compute() {
        int[] number = problem.number;
        if (number.length <= THRESHOLD) {
            result = problem.mergeSort(number);
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
            MergeSortForkJoin leftForkJoin = new MergeSortForkJoin(new MergeSortProblem(left));
            MergeSortForkJoin rightForkJoin = new MergeSortForkJoin(new MergeSortProblem(right));
            invokeAll(leftForkJoin, rightForkJoin); // coInvoke(leftForkJoin, rightForkJoin); 
            result = problem.merge(leftForkJoin.result, rightForkJoin.result);
        }
    }

    public static void main(String[] args) {
        int[] number = { 4, 2, 1, 2, 7, 3, 9, 0, 0, 11, 10, 6, 15, 8 };
        MergeSortProblem problem = new MergeSortProblem(number);

        MergeSortForkJoin mergeSortForkJoin = new MergeSortForkJoin(problem);
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        forkJoinPool.invoke(mergeSortForkJoin);
        System.out.println(Arrays.toString(mergeSortForkJoin.result));
    }

}

/*
[0, 0, 1, 2, 2, 3, 4, 6, 7, 8, 9, 10, 11, 15]

*/

