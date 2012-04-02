/**
 * 
 */
package tedneward.things.concurrent;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * Use Semaphore to throttle
 * 
 * @author yangwm Jul 26, 2010 5:16:10 PM
 */
public class SemApp {
    public static void main(String[] args) {
        Runnable limitedCall = new LimitedCall();
        for (int i = 0; i < 10; i++) {
            new Thread(limitedCall).start();
        }
    }
}

class LimitedCall implements Runnable {
    final Random rand = new Random();
    int count = 0;
    final Semaphore available = new Semaphore(3);

    public void run() {
        int time = rand.nextInt(15);
        int num = count++;

        try {
            available.acquire();

            System.out.println("Executing " + System.currentTimeMillis() / 1000 + " long-running action for " + time + " seconds... #" + num);

            Thread.sleep(time * 1000);

            System.out.println("Done with #" + num + "!");

            available.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

/*
Executing 1280136560 long-running action for 7 seconds... #0
Executing 1280136560 long-running action for 3 seconds... #2
Executing 1280136560 long-running action for 9 seconds... #1
Done with #2!
Executing 1280136563 long-running action for 6 seconds... #3
Done with #0!
Executing 1280136567 long-running action for 12 seconds... #4
Done with #3!
Executing 1280136569 long-running action for 6 seconds... #5
Done with #1!
Executing 1280136569 long-running action for 8 seconds... #6
Done with #5!
Executing 1280136575 long-running action for 7 seconds... #7
Done with #6!
Executing 1280136577 long-running action for 4 seconds... #8
Done with #4!
Executing 1280136579 long-running action for 12 seconds... #9
Done with #8!
Done with #7!
Done with #9!

*/
