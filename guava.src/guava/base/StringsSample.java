/**
 * 
 */
package guava.base;

import java.util.Arrays;

import com.google.common.base.CaseFormat;
import com.google.common.base.CharMatcher;
import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

/**
 * 
 * 
 * @author yangwm Mar 19, 2012 12:36:43 AM
 */
public class StringsSample {

    /**
     * @param args
     */
    public static void main(String[] args) {
        String value = Joiner.on(",").join(Arrays.asList(1, 5, 7)); // returns "1,5,7"
        System.out.println(value);
        
        Iterable<String> iter = 
        Splitter.on(',')
        .trimResults()
        .omitEmptyStrings()
        .split("foo,bar,,   qux");
        System.out.println(iter);
        
        String string = "abc\n123";
        String noControl = CharMatcher.JAVA_ISO_CONTROL.removeFrom(string); // remove control characters
        String theDigits = CharMatcher.DIGIT.retainFrom(string); // only the digits
        System.out.println(noControl);
        System.out.println(theDigits);
        
        byte[] bytes = string.getBytes(Charsets.UTF_8);
        System.out.println(Arrays.toString(bytes));
        
        value = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, "CONSTANT_NAME"); // returns "constantName"
        System.out.println(value);
    }

}
