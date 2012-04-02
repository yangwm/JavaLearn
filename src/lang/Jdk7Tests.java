package lang;

import java.util.Set;
import java.util.TreeSet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

/**
 * jdk 7 study
 */
public class Jdk7Tests {  
   
    public static void main(String[] args) {  
        Jdk7Tests jdk7Tests = new Jdk7Tests();  
        jdk7Tests.integersWithUnderscores();  
        jdk7Tests.stringSwitch();  
        jdk7Tests.binaryLiteral();  
        jdk7Tests.diamond();  
    }  
   
    private void integersWithUnderscores()  
    {  
        int i = 1_2;  
        System.out.println(i);  
        i*=10;  
        System.out.println(i);  
        int j=2_0;  
        System.out.println(i-j);         
    }  
   
    private void stringSwitch()  
    {  
        String key = "akey";  
        switch (key)  
        {  
        case "":  
        {  
            System.out.println("Nothing");  
            break;  
        }  
   
        case "akey":  
        {  
            System.out.println("Matched akey");  
            break;  
        }  
        default:  
        break;  
        }  
    }  
   
    private void binaryLiteral()  
    {  
        byte aByte = (byte)0b001;  
        short aShort = (short)0b010;  
        System.out.println(aByte + " " + aShort);  
    }  
   
    private void diamond()  
    {  
        Set set = new TreeSet<>();  
        set.add("c");  
        set.add("b");  
        set.add("a");  
        for (Object val : set)  
        {  
            System.out.println(val);  
        }  
    }  

    /*
    private void collectionLang() {
        List list = ["item"];  
        String item = list[0];   
        Set set = {"item"};   
        Map map = {"key" : 1};  
        int value = map["key"]; 
    }
    */

    private String autoResource() {
        /*
        try (BufferedReader br = new BufferedReader(new FileReader(path)) {
            return br.readLine();
        }
        */
        String path = "";
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path));
            return br.readLine();  
        } catch (FileNotFoundException e) { //FirstException | SecondException e
            
        } catch (IOException e) {
            
        } finally {
            try {
                br.close();
            } catch (Exception e) {

            }
        }  
        return null;
    }

}

/*
D:\study\tempProject\JavaLearn\src>javac Jdk7Tests.java
Note: Jdk7Tests.java uses unchecked or unsafe operations.
Note: Recompile with -Xlint:unchecked for details.

D:\study\tempProject\JavaLearn\src>java Jdk7Tests
12
120
100
Matched akey
1 2
a
b
c

*/
