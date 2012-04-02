/**
 * 
 */
package timers;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * SystemTimer.currentTimeMillis()直接返回缓存值，独立线程定期更新时间缓存，精度取决于定期间隔。
 * 
 * @author yangwm Dec 5, 2010 2:41:06 PM
 */
public class SystemTimer {
    private final static ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    
    private static final long tickUnit = Long.parseLong(System.getProperty("notify.systimer.tick", "10"));
    private static volatile long time = System.currentTimeMillis();

    private static class TimerTicker implements Runnable {
        public void run() {
            time = System.currentTimeMillis();
        }
    }

    public static long currentTimeMillis() {
        return time;
    }

    static {
        executor.scheduleAtFixedRate(new TimerTicker(), tickUnit, tickUnit, TimeUnit.MILLISECONDS);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                executor.shutdown();
            }
        });
    }
}
