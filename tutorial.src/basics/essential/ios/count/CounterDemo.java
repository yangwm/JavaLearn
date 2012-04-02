package basics.essential.ios.count;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CounterDemo {

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        CountReader cr = new CountReader(new FileReader("xanadu.txt"), 'e');
        CountWriter cw = new CountWriter(new FileWriter("outagain.txt"), 'e');
        int c = 0;
        while ((c = cr.read()) != -1) {
            cw.write(c);
        }
        System.out.println(cr.getCount());   
        System.out.println(cw.getCount());   
        cr.close();
        cw.close();
    }

}
