/**
 * 
 */
package strings;


/**
 * 
 * @author yangwm Apr 4, 2012 1:36:54 AM
 */
public class StringFaultDemo {

    /**
     * @param args
     */
    public static void main(String[] args) {
        int t = 1000 * 100;
        String line = "hello yangwm, StringBuilder public String toString() { // Create a copy, don't share the array return new String(value, 0, count); }";
        
        //normal(line, t);
        fault(line, t);
    }
    
    public static String fault(String line, int t) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < t; i++) {
            if (buf.toString().length() > 0) { // buf.toString() is Create a copy, very bad 
                buf.append("\r\n");
            }
            buf.append(line);
        }
        
        return buf.toString();
    }
    
    public static String normal(String line, int t) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < t; i++) {
            if (buf.length() > 0) { // 
                buf.append("\r\n");
            }
            buf.append(line);
        }
        
        return buf.toString();
    }

}

/*
fault(line, t);:

yangwm@yangwuming:~$ jstack -l 26463
2012-04-04 01:55:33
Full thread dump Java HotSpot(TM) Server VM (20.1-b02 mixed mode):
......

"main" prio=10 tid=0x083e8800 nid=0x6764 runnable [0xb6968000]
   java.lang.Thread.State: RUNNABLE
    at java.util.Arrays.copyOfRange(Arrays.java:3209)
    at java.lang.String.<init>(String.java:215)
    at java.lang.StringBuilder.toString(StringBuilder.java:430)
    at strings.StringFaultDemo.fault(StringFaultDemo.java:28)
    at strings.StringFaultDemo.main(StringFaultDemo.java:22)

   Locked ownable synchronizers:
    - None

"VM Thread" prio=10 tid=0x91e96800 nid=0x6769 runnable 

"GC task thread#0 (ParallelGC)" prio=10 tid=0x083ef800 nid=0x6765 runnable 

"GC task thread#1 (ParallelGC)" prio=10 tid=0x91e00800 nid=0x6766 runnable 

"GC task thread#2 (ParallelGC)" prio=10 tid=0x91e01c00 nid=0x6767 runnable 

"GC task thread#3 (ParallelGC)" prio=10 tid=0x91e03400 nid=0x6768 runnable 

"VM Periodic Task Thread" prio=10 tid=0x91eb9400 nid=0x6770 waiting on condition 

JNI global references: 891

yangwm@yangwuming:~$ 


*/

