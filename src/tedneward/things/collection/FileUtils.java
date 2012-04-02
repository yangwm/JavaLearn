/**
 * 
 */
package tedneward.things.collection;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

/**
 * 
 * 
 * @author yangwm Jul 4, 2010 3:56:37 PM
 */
public class FileUtils {
    
    public static Iterable<String> readlines(String filename) throws IOException {
        final FileReader fr = new FileReader(filename);
        final BufferedReader br = new BufferedReader(fr);
        
        return new Iterable<String>() {
            public Iterator<String> iterator() {
                return new Iterator<String>() {
                    public boolean hasNext() {
                        return line != null;
                    }
                    public String next() {
                        String retval = line;
                        line = getLine();
                        return retval;
                    }
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                    
                    String getLine() {
                        String line = null;
                        try {
                            line = br.readLine();
                        }
                        catch (IOException ioEx) {
                            line = null;
                        }
                        return line;
                    }
                    String line = getLine();
                };
            }   
        };
    }

}
