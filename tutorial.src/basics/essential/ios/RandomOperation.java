package basics.essential.ios;

import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomOperation {
    
    public static void main(String[] args) throws IOException {
        String datafile = "xanadu.txt";
        RandomAccessFile file = new RandomAccessFile(datafile, "rw");
        file.skipBytes((int)file.length()); //skip to the end of the file
        file.writeBytes("Add this text to the end of datafile"); //write at the end of the file
        file.close();
    }
}
