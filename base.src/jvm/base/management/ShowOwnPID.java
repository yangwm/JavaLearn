package jvm.base.management;
/**
 * 
 */

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/**
 * Sun的JDK里获取当前进程 ID的方法（hack）
 * http://rednaxelafx.javaeye.com/blog/716918
 * 
 * @author yangwm Jul 21, 2010 2:49:35 PM
 */
public class ShowOwnPID {

    public static void main(String[] args) throws Exception {  
        int pid = getPid();  
        System.out.println("pid: " + pid);  
        System.in.read(); // block the program so that we can do some probing on it  
    }  
      
    private static int getPid() {  
        RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();  
        String name = runtime.getName(); // format: "pid@hostname"  
        try {  
            return Integer.parseInt(name.substring(0, name.indexOf('@')));  
        } catch (Exception e) {  
            return -1;  
        }  
    }  

}

