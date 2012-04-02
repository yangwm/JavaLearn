/**
 * 
 */
package tedneward.things.concurrent;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * 
 * 
 * @author yangwm Jul 26, 2010 4:35:04 PM
 */

class Producer implements Runnable {
    private BlockingQueue<String> drop;
    List<String> messages = Arrays.asList(
        "Mares eat oats", 
        "Does eat oats", 
        "Little lambs eat ivy",
        "Wouldn't you eat ivy too?"
        );

    public Producer(BlockingQueue<String> d) {
        this.drop = d;
    }

    public void run() {
        try {
            for (String s : messages) {
                drop.put(s);
            }
            drop.put("DONE");
        } catch (InterruptedException intEx) {
            System.out.println("Interrupted! " + "Last one out, turn out the lights!");
        }
    }
}

