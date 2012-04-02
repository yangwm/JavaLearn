package basics.essential.ios.file;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileReader {

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            long dataPosition = 0;
            int data = -1;
            RandomAccessFile raf = new RandomAccessFile("datafile", "r");
            
            // Get the position of date to read
            dataPosition = raf.readLong();
            
            // Go to that position
            raf.seek(dataPosition);
            System.out.println("FileReader dataPosition=" + dataPosition);
            
            // Read the data
            data = raf.readInt();
            raf.close();
            
            // Tell the data
            System.out.println("The data is : " + data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
