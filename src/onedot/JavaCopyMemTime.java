/**
 * 
 */
package onedot;

/**
 * 
 * 
 * @author yangwm Jun 6, 2010 6:19:40 PM
 */
public class JavaCopyMemTime {

    /**
     * @param args
     */
    public static void main(String[] args) {
        long startTime = System.nanoTime();
        byte[] array = new byte[640*1024*1024];
        long totalTime = System.nanoTime() - startTime;
        
        System.out.println(totalTime);
    }

}

/*

D:\study\tempProject\JavaLearn\classes>java -Xmx700m onedot.JavaCopyMemTime
703439683

D:\study\tempProject\JavaLearn\classes>

*/
