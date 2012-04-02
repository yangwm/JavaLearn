/**
 * 
 */
package tedneward.things.concurrent;

import java.util.concurrent.*;

/**
 * ScheduledExecutorService 'pings' on schedule
 * 
 * @author yangwm Jul 26, 2010 6:03:09 PM
 */
public class Ping {
    public static void main(String[] args) {
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
        Runnable pinger = new Runnable() {
            public void run() {
                System.out.println("PING!");
            }
        };
        ses.scheduleAtFixedRate(pinger, 5, 5, TimeUnit.SECONDS);
    }
}

/*
PING!
PING!
PING!
......

*/
