package basics.essential.regexs;

import java.io.Console;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class RegexTestHarness {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Console console = System.console();
        if (console == null) {
            System.err.println("No console.");
            System.exit(1);
        }
        while (true) {
            Pattern pattern = 
                Pattern.compile( console.readLine("%nEnter your regex: ") );
            
            /*Pattern pattern = 
                Pattern.compile(
                    console.readLine("%nEnter your regex: "),
                    Pattern.CASE_INSENSITIVE
                );*/
            
            /*Pattern pattern = 
                Pattern.compile("[az]$", Pattern.MULTILINE | Pattern.UNIX_LINES);*/
            
            /*final int flags = Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE;
            Pattern pattern = Pattern.compile("aa", flags);*/

            Matcher matcher = 
                pattern.matcher(console.readLine("Enter input string to search: "));

            boolean found = false;
            while (matcher.find()) {
                console.format("I found the text \"%s\" starting at " +
                   "index %d and ending at index %d.%n",
                    matcher.group(), matcher.start(), matcher.end());
                found = true;
            }
            if(!found){
                console.format("No match found.%n");
            }
        }
    }

}
