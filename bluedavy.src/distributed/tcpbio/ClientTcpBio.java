/**
 * 
 */
package distributed.tcpbio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 实现TCP/IP+BIO 方式的系统间通讯的代码，客户端：
 * 
 * @author yangwm in Mar 22, 2010 4:44:38 PM
 */
public class ClientTcpBio {

    /**
     * create by yangwm in Mar 22, 2010 4:44:38 PM
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        String host = "127.0.0.1";
        int port = 9527;
        
        Socket socket = new Socket(host, port);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        
        BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));
        boolean flag = true;
        while (flag) {
            String command = systemIn.readLine();
            out.println(command);
            
            if (command == null || "quit".equalsIgnoreCase(command)) {
                System.out.println("Client quit!");
                flag = false;

                systemIn.close();
                out.close();
                in.close();
                socket.close();
                continue;
            }
            
            String response = in.readLine();
            System.out.println(response);
        }
        
    }

}

/*
hello, yangwm, see bio
Server response：hello, yangwm, see bio
what are you doing?
Server response：what are you doing?
quit
Client quit!

*/
