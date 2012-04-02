/**
 * 
 */
package jdyn.javassist;

/**
 * 清单 1. 需要计时的方法
 * 
 * @author yangwm in Dec 7, 2009 5:39:13 PM
 */
public class StringBuilder {
    
    private String buildString(int length) {
        String result = "";
        for (int i = 0; i < length; i++) {
            result += (char)(i%26 + 'a');
        }
        return result;
    }
    
    /*清单 2. 带有计时的方法
    private String buildString(int length) {
        long start = System.currentTimeMillis();
        String result = "";
        for (int i = 0; i < length; i++) {
            result += (char)(i%26 + 'a');
        }
        System.out.println("Call to buildString took " +
            (System.currentTimeMillis()-start) + " ms.");
        return result;
    }
    */
    
    
    public static void main(String[] argv) {
        StringBuilder inst = new StringBuilder();
        for (int i = 0; i < argv.length; i++) {
            String result = inst.buildString(Integer.parseInt(argv[i]));
            System.out.println("Constructed string of length " +
                result.length());
        }
    }
    
}

/*
java jdyn.javassist.StringBuilder 1000 2000 4000 8000 16000
Constructed string of length 1000
Constructed string of length 2000
Constructed string of length 4000
Constructed string of length 8000
Constructed string of length 16000

*/

