package basics.essential.regexs;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class RegexDemo2 {

    private static String REGEX = "*b";
    private static String INPUT = "aabfooaabfooabfoob";
    private static String REPLACE = "-";
 
    public static void main(String[] args) {
        
        try {
            Pattern p = Pattern.compile(REGEX);
            Matcher m = p.matcher(INPUT); // get a matcher object
            StringBuffer sb = new StringBuffer();
            while(m.find()){
                m.appendReplacement(sb,REPLACE);
            }
            m.appendTail(sb);
            System.out.println(sb.toString());
        } catch(PatternSyntaxException pse){
            System.out.format("There is a problem with the regular expression!%n");
            System.out.format("The pattern in question is: %s%n",pse.getPattern());
            System.out.format("The description is: %s%n",pse.getDescription());
            System.out.format("The message is: %s%n",pse.getMessage());
            System.out.format("The index is: %s%n",pse.getIndex());
            System.exit(0);
        }
        
    }

} /*
There is a problem with the regular expression!
The pattern in question is: *b
The description is: Dangling meta character '*'
The message is: Dangling meta character '*' near index 0
*b
^
The index is: 0
*///~
