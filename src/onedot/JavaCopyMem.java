/**
 * 
 */
package onedot;

/**
 * 
 * 
 * @author yangwm Jun 6, 2010 5:29:14 PM
 */
public class JavaCopyMem {

    /**
     * @param args
     */
    public static void main(String[] args) {
        byte[] array = new byte[640*1024*1024];
    }

}

/*
D:\study\tempProject\JavaLearn\classes>java -Xmx690m onedot.JavaCopyMem
Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
        at onedot.JavaCopyMem.main(JavaCopyMem.java:17)

D:\study\tempProject\JavaLearn\classes>java -Xmx700m onedot.JavaCopyMem

D:\study\tempProject\JavaLearn\classes>

PF使用率： 991M涨到1.61G。
*/
