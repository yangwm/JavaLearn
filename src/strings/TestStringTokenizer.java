package strings;


import java.util.StringTokenizer;

/**
 * 
 * @author yangwm in Dec 23, 2008 11:40:52 AM
 */
public class TestStringTokenizer {

    /**
     * create by yangwm in Dec 23, 2008 11:37:33 AM
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("StringTokenizer-----------");
        StringTokenizer st = new StringTokenizer("this is a test");  // <=> StringTokenizer("this is a test", " \t\n\r\f") 
        while (st.hasMoreTokens()) {
            System.out.println(st.nextToken());
        }
        
        System.out.println("String-----------");
        String[] result = "this is a test".split("\\s");
        for (int x=0; x<result.length; x++) {
            System.out.println(result[x]);
        }
    }

}
/*
StringTokenizer-----------
this
is
a
test
String-----------
this
is
a
test

*/
