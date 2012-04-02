/**
 * 
 */
package onedot;

import java.io.IOException;

import sun.net.TelnetInputStream;
import sun.net.ftp.FtpClient;

/**
 * @author yangwm in Feb 13, 2010 8:01:27 PM
 */
public class FTP {

    /**
     * create by yangwm in Feb 13, 2010 8:01:27 PM
     * @param args
     */
    public static void main(String[] args) {
        String server = "127.0.0.1";
        int port = 21;
        String user = "yangwm";
        String passwd = "123";
        String path = "d:/ftpdir";
        
        FtpClient ftpClient = new FtpClient();
        try {
            ftpClient.openServer(server, port);
            ftpClient.login(user, passwd);
            //ftpClient.binary();
            
            if (path.length() != 0) {
                ftpClient.cd(path);
            }
            
            TelnetInputStream is = ftpClient.list();
            int c;
            while ((c = is.read()) != -1) {
                System.out.println((char)c);
            }
            is.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                ftpClient.closeServer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
