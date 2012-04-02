/**
 * 
 */
package net.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * input
 * 
 * @author yangwm Jan 17, 2012 6:36:09 PM
 */
public class Client {
    
    public static void main(String[] args) throws IOException {
        String host = "127.0.0.1";
        int port = 9527;
        
        Socket socket = new Socket(host, port);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        
        BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));

        String command = systemIn.readLine();
        out.println(command);

        String response = in.readLine();
        System.out.println(response);
    }
    
}
