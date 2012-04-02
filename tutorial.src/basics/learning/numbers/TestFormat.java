package basics.learning.numbers;

import java.util.*;

public class TestFormat {
    
    public static void main(String[] args) {
      long n = 461012;
      System.out.format("%d%n", n); 
      System.out.format("%08d%n", n);
      System.out.format("%+8d%n", n);
      System.out.format("%,8d%n", n);
      System.out.format("%+,8d%n%n", n);
      
      double pi = Math.PI;
      System.out.format("%f%n", pi);
      System.out.format("%.3f%n", pi);
      System.out.format("%+10.3f%n", pi);  
      System.out.format("%-10.3f%n", pi);  
      System.out.format(Locale.FRANCE, "%-10.4f%n%n", pi);

      Calendar c = Calendar.getInstance();
      System.out.format("%tB %te, %tY%n", c, c, c);
      System.out.format("%tl:%tM %tp%n", c, c, c);
      System.out.format("%tD%n", c);
    }
}
/*
461012
00461012
 +461012
 461,012
+461,012

3.141593
3.142
    +3.142
3.142     
3,1416    

四月 30, 2008
5:24 下午
04/30/08
*///: