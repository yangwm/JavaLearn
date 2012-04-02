package basics.learning.strings;

public class ToStringDemo {
    
    public static void main(String[] args) {
        double d = 858.48;
        String s = Double.toString(d);
        
        int dot = s.indexOf('.');
        
        System.out.println(dot + " digits before decimal point.");
        System.out.println( (s.length() - dot - 1) + 
        " digits after decimal point.");
    }
}
