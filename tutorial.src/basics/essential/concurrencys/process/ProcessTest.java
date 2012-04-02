/**
 * 
 */
package basics.essential.concurrencys.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * ProcessBuilder: A process has a self-contained execution environment. 
 * 
 * @author yangwm Nov 21, 2011 12:02:18 AM
 */
public class ProcessTest {

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        ProcessBuilder pb = new ProcessBuilder("ping", "127.0.0.1");
        Map<String, String> env = pb.environment();
        env.put("VAR1", "myValue");
        env.remove("OTHERVAR");
        env.put("VAR2", env.get("VAR1") + "suffix");
        //pb.directory(new File("myDir"));
        File log = new File("log");
        pb.redirectErrorStream(true);
        //pb.redirectOutput(Redirect.appendTo(log));
        Process p = pb.start();
        //assert pb.redirectInput() == Redirect.PIPE;
        //assert pb.redirectOutput().file() == log;
        //assert p.getInputStream().read() == -1;
        
        BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = null;
        while ((line = in.readLine()) != null) {
            System.out.println(line);
        }
        
    }

}

/*
PING 127.0.0.1 (127.0.0.1) 56(84) bytes of data.
64 bytes from 127.0.0.1: icmp_req=1 ttl=64 time=0.043 ms
64 bytes from 127.0.0.1: icmp_req=2 ttl=64 time=0.037 ms
...
*/
