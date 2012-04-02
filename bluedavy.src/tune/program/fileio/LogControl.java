/**
 * 
 */
package tune.program.fileio;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 日志控制：采用简单策略为统计一段时间内日志输出频率， 当超出这个频率时，一段时间内不再写log 
 * 
 * @author yangwm Aug 24, 2010 10:41:43 AM
 */
public class LogControl {
    
    public static void main(String[] args) {
        for (int i = 1; i <= 1000; i++) {
            if (LogControl.isLog()) {
                //logger.error(errorInfo, throwable);
                System.out.println("errorInfo " + i);
            }
            
            //  
            if (i % 100 == 0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    
    private static final long INTERVAL = 1000;
    private static final long PUNISH_TIME = 5000;
    private static final int ERROR_THRESHOLD = 100;
    private static AtomicInteger count = new AtomicInteger(0);
    private static long beginTime;
    private static long punishTimeEnd;
    
    // 由于控制不用非常精确， 因此忽略此处的并发问题
    public static boolean isLog() {
        //System.out.println(count.get() + ", " + beginTime + ", " + punishTimeEnd + ", " + System.currentTimeMillis());
        
        // 不写日志阶段 
        if (punishTimeEnd > 0 && punishTimeEnd > System.currentTimeMillis()) {
            return false;
        }
        
        // 重新计数
        if (count.getAndIncrement() == 0) {
            beginTime = System.currentTimeMillis();
            return true;
        } else { // 已在计数
            // 超过阀门值， 设置count为0并设置一段时间内不写日志
            if (count.get() > ERROR_THRESHOLD) {
                count.set(0);
                punishTimeEnd = PUNISH_TIME + System.currentTimeMillis();
                return false;
            } 
            // 没超过阀门值， 且当前时间已超过计数周期，则重新计算 
            else if (System.currentTimeMillis() > (beginTime + INTERVAL)) {
                count.set(0);
            }
            
            return true;
        }
    }
    
}

/*
errorInfo 1
errorInfo 2
......
errorInfo 99
errorInfo 100
errorInfo 601
errorInfo 602
......
errorInfo 699
errorInfo 700

*/
