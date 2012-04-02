/**
 * 
 */
package jthread;

/**
 * Creates ten threads to search for the maximum value of a large matrix.
 * Each thread searches one portion of the matrix.
 * 
 * @author yangwm in Nov 20, 2009 10:04:50 AM
 */
public class TenThreads {

    private static class WorkerThread extends Thread {
        int max = Integer.MIN_VALUE;
        int[] ourArray;

        public WorkerThread(int[] ourArray) {
            this.ourArray = ourArray;
        }

        // Find the maximum value in our particular piece of the array
        public void run() {
            for (int i = 0; i < ourArray.length; i++) 
                max = Math.max(max, ourArray[i]);                
        }

        public int getMax() {
            return max;
        }
    }

    private static int[][] getBigHairyMatrix() {
        int[][] bigMatrix = 
            {
                {61, 62, 63, 64, 65, 66, 67, 68, 69, 70},
                {71, 72, 73, 74, 75, 76, 77, 78, 79, 80},
                {100, 91, 92, 93, 94, 95, 96, 97, 98, 99},
                {80, 81, 82, 83, 84, 85, 86, 87, 88, 89},
                {1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
                
                {31, 32, 33, 34, 35, 36, 37, 38, 39, 40},
                {51, 52, 53, 54, 55, 56, 57, 58, 59, 50},
                {41, 42, 43, 44, 45, 46, 47, 48, 49, 50},
                {21, 22, 23, 24, 25, 26, 27, 28, 29, 40},
                {11, 12, 13, 14, 15, 16, 17, 18, 19, 20}
            };
        return bigMatrix;
    }
    
    public static void main(String[] args) {
        WorkerThread[] threads = new WorkerThread[10];
        int[][] bigMatrix = getBigHairyMatrix();
        int max = Integer.MIN_VALUE;
        
        // Give each thread a slice of the matrix to work with
        for (int i=0; i < 10; i++) {
            threads[i] = new WorkerThread(bigMatrix[i]);
            threads[i].start();
        }

        // Wait for each thread to finish
        try {
            for (int i=0; i < 10; i++) {
                threads[i].join();
                max = Math.max(max, threads[i].getMax());
            }
        } catch (InterruptedException e) {
            // fall through
        }

        System.out.println("Maximum value was " + max);
    }

}

/*
Maximum value was 100
*/
