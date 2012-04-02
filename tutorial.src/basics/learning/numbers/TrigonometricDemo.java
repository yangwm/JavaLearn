package basics.learning.numbers;

public class TrigonometricDemo {
    public static void main(String[] args) {
        double degrees = 45.0;
        double radians = Math.toRadians(degrees);
        System.out.format("The radians of %f degrees is %f%n", degrees, radians);
        
        System.out.format("The value of pi is %.4f%n", Math.PI);
        System.out.format("The sine of %.1f degrees is %.4f%n", degrees, 
                                                    Math.sin(radians));
        System.out.format("The cosine of %.1f degrees is %.4f%n", degrees, 
                               Math.cos(radians));
        System.out.format("The tangent of %.1f degrees is %.4f%n", degrees, 
                               Math.tan(radians));
        System.out.format("The arcsine of %.4f is %.4f degrees %n", 
                              Math.sin(radians), 
                              Math.toDegrees(Math.asin(Math.sin(radians))));
        System.out.format("The arccosine of %.4f is %.4f degrees %n", 
                              Math.cos(radians),  
                              Math.toDegrees(Math.acos(Math.cos(radians))));
        System.out.format("The arctangent of %.4f is %.4f degrees %n", 
                              Math.tan(radians), 
                              Math.toDegrees(Math.atan(Math.tan(radians))));
     
    }
}
/*
The radians of 45.000000 degrees is 0.785398
The value of pi is 3.1416
The sine of 45.0 degrees is 0.7071
The cosine of 45.0 degrees is 0.7071
The tangent of 45.0 degrees is 1.0000
The arcsine of 0.7071 is 45.0000 degrees 
The arccosine of 0.7071 is 45.0000 degrees 
The arctangent of 1.0000 is 45.0000 degrees
*///:
