package cmd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PingServer {
    public static boolean ping(String server, int timeout) {
        Runtime r = Runtime.getRuntime();
        server = server.substring(server.indexOf("/") + 2, server
                .lastIndexOf(":"));
        String pingCommand = "ping   " + server + "   -n   1   -w   " + timeout;
        
        BufferedReader in = null;
        try {
            Process p = r.exec(pingCommand);
            if (p == null) {
                return false;
            }
            in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                if (line.startsWith("Reply")) {
                    return true;
                }
            }
        } catch (Exception ex) {
            return false;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return false;
    }


	/**
	 *	test PingServer, but not good , if port is default (80) will throws java.lang.StringIndexOutOfBoundsException 
	 */
	public static void main(String... args) {
		System.out.println(ping("http://127.0.0.1:8080/ams", 5000));

		SecurityManager sm = new SecurityManager();//System.getSecurityManager();
		System.out.println("sm=" + sm);
		if (sm != null) {
			sm.checkConnect("127.0.0.1", 8080);
			//sm.checkConnect("100.9.0.40", 7001);
			System.out.println("checkConnect");
		}
	}
}