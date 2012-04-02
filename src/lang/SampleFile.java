package lang;
import java.io.File;

/**
 * 
 */

/**
 * @author yangwm in Dec 3, 2009 11:20:10 AM
 */
public class SampleFile {

    /**
     * create by yangwm in Dec 3, 2009 11:20:11 AM
     * @param args
     */
    public static void main(String[] args) {
        File file = null;//new File("testSampleFile.txt");
        if (file != null) {
            file.delete();
        }
    }

}
