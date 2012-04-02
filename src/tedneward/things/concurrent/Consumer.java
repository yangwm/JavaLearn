/**
 * 
 */
package tedneward.things.concurrent;

import java.util.concurrent.BlockingQueue;

/**
 * 
 * 
 * @author yangwm Jul 26, 2010 4:35:31 PM
 */

class Consumer implements Runnable {
    private BlockingQueue<String> drop;

    public Consumer(BlockingQueue<String> d) {
        this.drop = d;
    }

    public void run() {
        try {
            String msg = null;
            while (!((msg = drop.take()).equals("DONE"))) {
                System.out.println(msg);
            }
        } catch (InterruptedException intEx) {
            System.out.println("Interrupted! " + "Last one out, turn out the lights!");
        }
    }
}
