package basics.essential.regexs;

import java.util.regex.Pattern;

public class SplitDemo {

    private static final String REGEX = ":";
    private static final String INPUT = "one:two:three:four:five";
    
    public static void main(String[] args) {
        System.out.println("1:---------------");
        Pattern p = Pattern.compile(REGEX);
        String[] items = p.split(INPUT);
        for(String s : items) {
            System.out.println(s);
        }
        
        System.out.println("2:---------------");
        String[] items2 = INPUT.split(REGEX);
        for(String s : items2) {
            System.out.println(s);
        }
        
        System.out.println("3:---------------");
        String item = Pattern.quote(INPUT);
        System.out.println(item);
    }

}
