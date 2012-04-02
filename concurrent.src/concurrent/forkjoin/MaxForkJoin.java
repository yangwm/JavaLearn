/**
 * 
 */
package concurrent.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * Solving the select-max problem with the fork-join framework(a parallelizable algorithm)
 * 
 * @author yangwm Aug 11, 2010 6:10:01 PM
 */
public class MaxForkJoin extends RecursiveAction {
    /**
     * 
     */
    private static final long serialVersionUID = 4401943143483920511L;
    
    private final int threshold;
    private final SelectMaxProblem problem;
    public int result;

    public MaxForkJoin(SelectMaxProblem problem, int threshold) {
        this.problem = problem;
        this.threshold = threshold;
    }

    @Override
    protected void compute() {
        if (problem.size < threshold) {
            result = problem.solveSequentially();
        } else {
            int midpoint = problem.size / 2;
            MaxForkJoin leftForkJoin = new MaxForkJoin(problem.subproblem(0, midpoint), threshold);
            MaxForkJoin rightForkJoin = new MaxForkJoin(problem.subproblem(midpoint + 1, problem.size), threshold);
            invokeAll(leftForkJoin, rightForkJoin); // coInvoke(leftForkJoin, rightForkJoin); 
            result = Math.max(leftForkJoin.result, rightForkJoin.result);
        }
    }
    
    public static void main(String[] args) {
        int[] numbers = {4, 2, 1, 2, 7, 3, 9, 0, 0, 11, 10, 6, 15, 8};
        
        SelectMaxProblem problem = new SelectMaxProblem(numbers, 0, numbers.length);
        int threshold = 3;
        int nThreads = 2;
        MaxForkJoin maxForkJoin = new MaxForkJoin(problem, threshold);
        ForkJoinPool forkJoinPool = new ForkJoinPool(nThreads);

        forkJoinPool.invoke(maxForkJoin);
        System.out.println(maxForkJoin.result);
    }

}

/*
15

*/

