package basics.essential.regexs;

import java.util.regex.Pattern;

public class SplitDemo2 {

    private static final String REGEX = "\\d";
    private static final String INPUT = "one9two4three7four1five";

    public static void main(String[] args) {
        System.out.println("1:---------------");
        Pattern p = Pattern.compile(REGEX);
        String[] items = p.split(INPUT, 2);
        for(String s : items) {
            System.out.println(s);
        }
        
        System.out.println("2:---------------");
        String[] items2 = INPUT.split(REGEX);
        for(String s : items2) {
            System.out.println(s);
        }
    }

}
