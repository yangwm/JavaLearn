package basics.essential.regexs;

import java.util.regex.Pattern;

public class RegexSimpleTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println( "Pattern -----------------" );
        System.out.println( Pattern.matches("\\d","1") );
        
        System.out.println( "String ------------------" );
        System.out.println( "1".matches("\\d") );
    }

} /* Output: (Sample)
Pattern -----------------
true
String ------------------
true
*///:~
