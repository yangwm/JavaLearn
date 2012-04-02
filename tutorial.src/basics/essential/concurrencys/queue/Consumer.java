package basics.essential.concurrencys.queue;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {
    private BlockingQueue<String> drop; 
    
    public Consumer(BlockingQueue<String> drop) {
        this.drop = drop;
    }

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        Random random = new Random();
        try {
            for (String message = drop.take(); ! message.equals("DONE");
                    message = drop.take()) {
                System.out.format("MESSAGE RECEIVED: %s%n", message);
                Thread.sleep(random.nextInt(5000));
            }
        } catch (InterruptedException e) {}
    }

}
