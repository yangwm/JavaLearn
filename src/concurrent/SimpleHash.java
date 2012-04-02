/**
 * 
 */
package concurrent;

/**
 * 
 * 
 * @author yangwm Mar 21, 2011 12:44:58 AM
 */
public class SimpleHash {

    /**
     * @param args
     */
    public static void main(String[] args) {
        /*
         * if length is 2^n(power of 2) then hashcode & (length-1) == hashcode % length
         */
        for (int i = 0; i < 50; i++) {
            System.out.println("(" + i + " % 16):" + (i % 16));
            System.out.println("(" + i + " & (16-1)):" + (i & (16-1)));
        }

    }

}
