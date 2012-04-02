package lang;

import org.apache.commons.lang.StringUtils;

public class StringUtilsAndWordUtilsUsage {
    
    public static void main(String[] args) {
        // data setup
        String str1 = "";
        String str2 = " ";
        String str3 = "\t";
        String str4 = null;
        String str5 = "123";
        String str6 = "ABCDEFG";
        String str7 = "It feels good to use Jakarta Commons.\r\n";
        
        // check for empty strings and blank strings
        System.out.println("==============================");
        System.out.println("Is \"null\" empty? " + StringUtils.isEmpty("null"));
        System.out.println("Is \"" + str1 + "\" empty? " + StringUtils.isEmpty(str1));
        System.out.println("Is \"" + str2 + "\" empty? " + StringUtils.isEmpty(str2));
        System.out.println("Is " + str3 + " empty? " + StringUtils.isEmpty(str3));
        System.out.println("Is " + str4 + " empty? " + StringUtils.isEmpty(str4));

        // check for blank strings
        System.out.println("==============================");
        System.out.println("Is \"null\" blank? " + StringUtils.isBlank("null"));
        System.out.println("Is \"" + str1 + "\" blank? " + StringUtils.isBlank(str1));
        System.out.println("Is \"" + str2 + "\" blank? " + StringUtils.isBlank(str2));
        System.out.println("Is \"" + str3 + "\" blank? " + StringUtils.isBlank(str3));
        System.out.println("Is \"" + str4 + "\" blank? " + StringUtils.isBlank(str4));

        // check for numerics
        System.out.println("==============================");
        System.out.println("Is str5 numeric? " + StringUtils.isNumeric(str5));
        System.out.println("Is str6 numeric? " + StringUtils.isNumeric(str6));

        // reverse strings / whole words
        System.out.println("==============================");
        System.out.println("str6: " + str6);
        System.out.println("str6 reversed: " + StringUtils.reverse(str6));
        
        System.out.println("str7: " + str7);
        String str8 = StringUtils.chomp(str7);
        System.out.println("str8: " + str8);
        str8 = StringUtils.reverseDelimited(str8, ' ');
        System.out.println("str7 reversed whole words : \r\n" + str8);
        
        // build header (useful to print log messages that are easy to locate)
        System.out.println("==============================");
        System.out.println("print header:");
        String padding = StringUtils.repeat("=", 50);
        String msg = StringUtils.center(" Customised Header ", 50, "%");
        Object[] raw = new Object[]{padding, msg, padding};
        String header = StringUtils.join(raw, "\r\n");
        System.out.println(header);
        
    }

}

/*
==============================
Is str1 blank? true
Is str2 blank? true
Is str3 blank? true
Is str4 blank? true
==============================
Is str5 numeric? true
Is str6 numeric? false
==============================
str6: ABCDEFG
str6 reversed: GFEDCBA
str7: It feels good to use Jakarta Commons.

str8: It feels good to use Jakarta Commons.
str7 reversed whole words : 
Commons. Jakarta use to good feels It
==============================
print header:
==================================================
%%%%%%%%%%%%%%% Customised Header %%%%%%%%%%%%%%%%
==================================================

*/