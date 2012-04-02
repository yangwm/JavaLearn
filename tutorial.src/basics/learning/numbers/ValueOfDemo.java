package basics.learning.numbers;

public class ValueOfDemo {
    public static void main(String[] args) {

        //this program requires two arguments on the command line  
        if (args.length == 2) {
            //convert strings to numbers
            float a = (Float.valueOf(args[0]) ).floatValue();  
            float b = (Float.valueOf(args[1]) ).floatValue();

            //do some arithmetic
            System.out.println("a + b = " + (a + b) );
            System.out.println("a - b = " + (a - b) );
            System.out.println("a * b = " + (a * b) );
            System.out.println("a / b = " + (a / b) );
            System.out.println("a % b = " + (a % b) );
        } else {
           System.out.println("This program requires two command-line arguments.");
        }
    }
}
