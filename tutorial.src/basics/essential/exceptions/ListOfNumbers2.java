package basics.essential.exceptions;

import java.io.*;
import java.util.Vector;

public class ListOfNumbers2 {
    private Vector<Integer> victor;
    private static final int SIZE = 10;

    public ListOfNumbers2() {
        victor = new Vector<Integer>(SIZE);
        for (int i = 0; i < SIZE; i++)
            victor.addElement(new Integer(i));

        this.readList("infile.txt");
        this.writeList();
    }

    public void readList(String fileName) {
        
        String line = null;
        try {
            System.out.println("file=" + new File(fileName).getAbsoluteFile());
            RandomAccessFile raf = new RandomAccessFile(fileName, "r");
            while ((line = raf.readLine()) != null) {
                Integer i = new Integer(Integer.parseInt(line));
                System.out.println(i);
                victor.addElement(i);
            }
        } catch(FileNotFoundException fnf) {
            System.err.println("File: " + fileName + " not found.");
        } catch (IOException io) {
            System.err.println(io.toString());
        }
    }

    public void writeList() {
        PrintWriter out = null;

        try {
            System.out.println("file=" + new File("outfile.txt").getAbsoluteFile());
            out = new PrintWriter(new FileWriter("outfile.txt"));
        
            for (int i = 0; i < victor.size(); i++)
                out.println("Value at: " + i + " = " + victor.elementAt(i));
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Caught ArrayIndexOutOfBoundsException: " +
                                 e.getMessage());
        } catch (IOException e) {
            System.err.println("Caught IOException: " + e.getMessage());
        } finally {
            if (out != null) {
                System.out.println("Closing PrintWriter");
                out.close();
            } else {
                System.out.println("PrintWriter not open");
            }
        }
    }
    
    public static void cat(File file) {
        RandomAccessFile input = null;
        String line = null;

        try {
            input = new RandomAccessFile(file, "r");
            while ((line = input.readLine()) != null) {
                System.out.println(line);
            }
            return;
        } catch(FileNotFoundException e){
            System.err.format("File: %s not found%n", file);
            System.err.println(e.toString());

        } catch(IOException e){
            System.err.println(e.toString());

        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch(IOException e) {
                    System.err.println(e.toString());

                }
            }
        }
    }



    public static void main(String[] args) {
        new ListOfNumbers2();
    }
}
