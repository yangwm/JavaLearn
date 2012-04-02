
// {args:log4j.properties 5}
package basics.collections.implementations;

import java.util.*;
import java.io.*;

public class FileList {
    public static void main(String[] args) {
        final int assumedLineLength = 50;
        File file = new File(args[0]);
        List<String> fileList = 
            new ArrayList<String>((int)(file.length() / assumedLineLength) * 2);
        BufferedReader reader = null;
        int lineCount = 0;
        try {
            reader = new BufferedReader(new FileReader(file));
            for (String line = reader.readLine(); line != null;
                    line = reader.readLine()) {
                fileList.add(line);
                lineCount++;
            }
        } catch (IOException e) {
            System.err.format("Could not read %s: %s%n", file, e);
            System.exit(1);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {}
            }
        }
        int repeats = Integer.parseInt(args[1]);
        Random random = new Random();
        for (int i = 0; i < repeats; i++) {
            System.out.format("%d: %s%n", i,
                    fileList.get(random.nextInt(lineCount - 1)));
        }
    }
}/*
0: #log4j.appender.CONSOLE.layout.ConversionPattern=[%-5p] %37c %3x %m%n
1:   
2: #log4j.appender.SIZE_ROLLING_FILE.MaxFileSize=100KB                                                                         
3: #log4j.appender.SIZE_ROLLING_FILE.File=erp.log                                        
4: log4j.rootLogger=DEBUG, CONSOLE, DAILY_ROLLING_FILE
*///~
