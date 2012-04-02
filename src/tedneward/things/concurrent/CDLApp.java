/**
 * 
 */
package tedneward.things.concurrent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch: Let's go to the races!
 * 
 * @author yangwm Jul 26, 2010 5:31:53 PM
 */
public class CDLApp {
    public static void main(String[] args) throws InterruptedException, java.io.IOException {
        System.out.println("Prepping...");

        Race r = new Race(
            "Beverly Takes a Bath",
            "RockerHorse",
            "Phineas",
            "Ferb",
            "Tin Cup",
            "I'm Faster Than a Monkey",
            "Glue Factory Reject"
            );


        System.out.println("It's a race of " + r.getDistance() + " lengths");

        System.out.println("Press Enter to run the race....");
        System.in.read();

        r.raceRun();
    }
}

class Race {
    private Random rand = new Random();

    private int distance = rand.nextInt(250);
    private CountDownLatch start;
    private CountDownLatch finish;

    private List<String> horses = new ArrayList<String>();

    public Race(String... names) {
        this.horses.addAll(Arrays.asList(names));
    }

    public void raceRun() throws InterruptedException {
        System.out.println("And the horses are stepping up to the gate...");
        final CountDownLatch start = new CountDownLatch(1);
        final CountDownLatch finish = new CountDownLatch(horses.size());
        final List<String> places = Collections.synchronizedList(new ArrayList<String>());

        for (final String h : horses) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        System.out.println(h + " stepping up to the gate...");
                        start.await();

                        int traveled = 0;
                        while (traveled < distance) {
                            // In a 0-2 second period of time....
                            Thread.sleep(rand.nextInt(3) * 1000);

                            // ... a horse travels 0-14 lengths
                            traveled += rand.nextInt(15);
                            System.out.println(h + " advanced to " + traveled + "!");
                        }
                        finish.countDown();
                        System.out.println(h + " crossed the finish!");
                        places.add(h);
                    } catch (InterruptedException intEx) {
                        System.out.println("ABORTING RACE!!!");
                        intEx.printStackTrace();
                    }
                }
            }).start();
        }

        System.out.println("And... they're off!");
        start.countDown();

        finish.await();
        System.out.println("And we have our winners!");
        System.out.println(places.get(0) + " took the gold...");
        System.out.println(places.get(1) + " got the silver...");
        System.out.println("and " + places.get(2) + " took home the bronze.");
    }

    public int getDistance() {
        return distance;
    }
    
}

/*
Prepping...
It's a race of 39 lengths
Press Enter to run the race....

And the horses are stepping up to the gate...
Beverly Takes a Bath stepping up to the gate...
And... they're off!
Phineas stepping up to the gate...
RockerHorse stepping up to the gate...
Tin Cup stepping up to the gate...
I'm Faster Than a Monkey stepping up to the gate...
Glue Factory Reject stepping up to the gate...
Ferb stepping up to the gate...
I'm Faster Than a Monkey advanced to 3!
I'm Faster Than a Monkey advanced to 14!
I'm Faster Than a Monkey advanced to 24!
Phineas advanced to 5!
Glue Factory Reject advanced to 5!
Glue Factory Reject advanced to 10!
Ferb advanced to 9!
RockerHorse advanced to 12!
Phineas advanced to 18!
Beverly Takes a Bath advanced to 14!
I'm Faster Than a Monkey advanced to 24!
Tin Cup advanced to 2!
I'm Faster Than a Monkey advanced to 26!
RockerHorse advanced to 21!
RockerHorse advanced to 23!
Ferb advanced to 14!
Ferb advanced to 18!
Ferb advanced to 30!
I'm Faster Than a Monkey advanced to 29!
Glue Factory Reject advanced to 24!
Beverly Takes a Bath advanced to 14!
RockerHorse advanced to 25!
Phineas advanced to 28!
Tin Cup advanced to 9!
Glue Factory Reject advanced to 37!
I'm Faster Than a Monkey advanced to 43!
I'm Faster Than a Monkey crossed the finish!
Tin Cup advanced to 22!
Glue Factory Reject advanced to 40!
Glue Factory Reject crossed the finish!
Phineas advanced to 36!
Ferb advanced to 34!
Tin Cup advanced to 28!
RockerHorse advanced to 38!
Beverly Takes a Bath advanced to 14!
Ferb advanced to 39!
Ferb crossed the finish!
Tin Cup advanced to 35!
Phineas advanced to 42!
Phineas crossed the finish!
Beverly Takes a Bath advanced to 17!
Tin Cup advanced to 38!
Beverly Takes a Bath advanced to 30!
RockerHorse advanced to 46!
RockerHorse crossed the finish!
Beverly Takes a Bath advanced to 37!
Beverly Takes a Bath advanced to 47!
Beverly Takes a Bath crossed the finish!
Tin Cup advanced to 50!
Tin Cup crossed the finish!
And we have our winners!
I'm Faster Than a Monkey took the gold...
Glue Factory Reject got the silver...
and Ferb took home the bronze.

*/
