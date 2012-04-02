package basics.essential.exceptions;

//Note: This class won't compile by design!
import java.io.*;
import java.util.Vector;

public class ListOfNumbers {

    private Vector vector;
    private static final int SIZE = 10;

    public ListOfNumbers () {
        vector = new Vector(SIZE);
        for (int i = 0; i < SIZE; i++) {
            vector.addElement(new Integer(i));
        }
    }

    /*public void writeList() {
        PrintWriter out = null;
        try {
            out = new PrintWriter(new FileWriter("OutFile.txt"));
    
            for (int i = 0; i < SIZE; i++) {
                out.println("Value at: " + i + " = " +
                             vector.elementAt(i));
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Caught " 
                    + "ArrayIndexOutOfBoundsException: " 
                    +   e.getMessage());
                 
        } catch(IOException e) {
            System.err.println("Caught IOException: " 
                    +  e.getMessage());

        } finally {
            if (out != null) {
                System.out.println("Closing PrintWriter");
                out.close();
        
            } else {
                System.out.println("PrintWriter not open");
            }

        }

        out.close();
    }*/
    
    public void writeList() throws IOException, 
    ArrayIndexOutOfBoundsException {
        PrintWriter out = 
                   new PrintWriter(new FileWriter("OutFile.txt"));
        for (int i = 0; i < SIZE; i++) {
            out.println("Value at: " + i + " = " 
                         + vector.elementAt(i));
        }
        out.close();
    }

}
