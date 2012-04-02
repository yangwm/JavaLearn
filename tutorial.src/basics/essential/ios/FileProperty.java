package basics.essential.ios;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class FileProperty {
    
    /**
     * @param args
     * @throws IOException 
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        File a = new File("xanadu.txt");
        FileStuff.listFile(a);
        Thread.sleep(2000);
        a.setLastModified(new Date().getTime());
        FileStuff.listFile(a);
    }
    
}
