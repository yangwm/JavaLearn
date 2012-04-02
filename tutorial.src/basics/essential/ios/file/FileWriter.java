package basics.essential.ios.file;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileWriter {

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            long dataPosition = 0; // to be terminaed later
            int data = 123;
            RandomAccessFile raf = new RandomAccessFile("datafile", "rw");
            
            // Write the file
            raf.writeLong(0); // placeholder
            raf.writeChars("blahblahblah");
            dataPosition = raf.getFilePointer();
            System.out.println("FileWriter dataPosition=" + dataPosition);
            raf.writeInt(data);
            raf.writeUTF("yangwmyangwm");
            
            // Rewrite the first byte to reflect updated data position.
            raf.seek(0);
            raf.writeLong(dataPosition);
            raf.close();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}
