package basics.essential.ios;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class FileSeparator {
    
    /**
     * @param args
     * @throws IOException 
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        //File a = FileStuff/listFile("xanadu.txt");
        File b = FileStuff.listFile(".." + File.separator  
            + "examples" + File.separator + "xanadu.txt");
        FileStuff.listFile(b);
        Thread.sleep(2000);
        b.setLastModified(new Date().getTime());
        FileStuff.listFile(b);
    }

}
