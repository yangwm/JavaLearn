/**
 * 
 */
package jvm.base.management;

import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

import sun.management.ManagementFactory;

/**
 * memory usage 
 * 
 * @author yangwm Nov 20, 2011 4:31:45 PM
 */
public class MemoryUsageTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage memoryUsage = memoryMXBean.getHeapMemoryUsage();
        
        System.out.println(
                "memory init: " + memoryUsage.getInit() / (1024 * 1024) + "MB, used: " 
                + memoryUsage.getUsed() / (1024 * 1024) + "MB, max: " 
                + memoryUsage.getMax() / (1024 * 1024) + "MB." 
                );
    }

}
