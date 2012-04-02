package basics.learning.numbers;

//1.1 and later

import java.text.DecimalFormat;

public class FPAdder {
    public static void main(String[] args) {

        int numArgs = args.length;
    
        //this program requires at least two arguments on the command line
        if (numArgs < 2) {
            System.out.println("This program requires two command-line arguments.");
        } else {
            double sum = 0.0;
    
            for (int i = 0; i < numArgs; i++) {
                    sum += Double.valueOf(args[i]).doubleValue();
            }
    
            //format the sum
            DecimalFormat myFormatter = new DecimalFormat("###,###.##");
            String output = myFormatter.format(sum);
    
            //print the sum
            System.out.println(output);
        }
    }
}
