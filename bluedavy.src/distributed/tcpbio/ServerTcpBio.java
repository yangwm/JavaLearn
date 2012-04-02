/**
 * 
 */
package distributed.tcpbio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * 实现TCP/IP+BIO 方式的系统间通讯的代码，服务器端：
 * 
 * @author yangwm in Mar 22, 2010 4:18:47 PM
 */
public class ServerTcpBio {

    /**
     * create by yangwm in Mar 22, 2010 4:18:51 PM
     * @param args
     * @throws IOException 
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 9527;
        System.out.println("Server listen on port: " + port);
        
        ServerSocket serverSocket = new ServerSocket(port);
        Socket socket = serverSocket.accept();
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        while (true) {
            String line = in.readLine();
            System.out.println("Message from client: " + line);
            if (line == null) {
                Thread.sleep(100);
                continue;
            }
            
            if ("quit".equalsIgnoreCase(line.trim())) {
                System.out.println("Client has been quit!");
                
                in.close();
                out.close();
                socket.close();
            } else if ("serverquit".equalsIgnoreCase(line.trim())) {
                System.out.println("Server has been shutdown!");
                
                in.close();
                out.close();
                socket.close();
                serverSocket.close();
                System.exit(0);
            } else {
                out.println("Server response：" + line);
                Thread.sleep(100);
            }
        }
        
    }

}

/*
Server listen on port: 9527
Message from client: hello, yangwm, see bio
Message from client: what are you doing?
Message from client: quit
Server has been shutdown!

*/
