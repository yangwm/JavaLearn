/**
 * 
 */
package onedot;

/**
 * 
 * 
 * @author yangwm Jun 6, 2010 6:00:57 PM
 */
public class JavaLoopTime {

    /**
     * @param args
     */
    public static void main(String[] args) {
        long startTime = System.nanoTime();
        for (int i = 1; i < 100000; i++) {
            for (int i2 = 1; i2 < 100000; i2++) {
                
            }
        }
        long totalTime = System.nanoTime() - startTime;
        
        System.out.println(totalTime);
    }

}

/*
D:\study\tempProject\JavaLearn\classes>java onedot.JavaTest
7367697291

*/

