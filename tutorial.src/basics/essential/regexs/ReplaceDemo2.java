package basics.essential.regexs;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReplaceDemo2 {

    private static String REGEX = "a*b";
    private static String INPUT = "aabfooaabfooabfoob";
    private static String REPLACE = "-";
 
    public static void main(String[] args) {
        Pattern p = Pattern.compile(REGEX);
        Matcher m = p.matcher(INPUT); // get a matcher object
        INPUT = m.replaceAll(REPLACE);
        System.out.println(INPUT);
    }

} /*
-foo-foo-foo-
*///~
