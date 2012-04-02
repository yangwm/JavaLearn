package basics.essential.concurrencys;

public class SleepMessages {

    /**
     * @param args
     */
    public static void main(String args[]) throws InterruptedException {
        String importantInfo[] = {
            "Mares eat oats",
            "Does eat oats",
            "Little lambs eat ivy",
            "A kid will eat ivy too"
        };

        for (int i = 0; i < importantInfo.length; i++) {
            //Pause for 4 seconds
            Thread.sleep(4000);
            if (Thread.interrupted()) {
                //We've been interrupted: no more crunching.
                return;
            }
            //Print a message
            System.out.println(importantInfo[i]);
        }
    }

}
