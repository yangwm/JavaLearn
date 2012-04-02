package basics.learning.strings;

public class StringBuilderDemo {
    public static void main(String[] args) {
        String palindrome = "Dot saw I was Tod";
         
        StringBuilder sb = new StringBuilder(palindrome);
        
        sb.reverse();  // reverse it
        
        System.out.println("sb.length()=" + sb.length() 
                + ",sb.capacity()=" + sb.capacity());
        System.out.println(sb);

        sb.append("Greetings");  // adds 9 character string at beginning
        System.out.println("sb.length()=" + sb.length() 
                + ",sb.capacity()=" + sb.capacity());
    }
}
