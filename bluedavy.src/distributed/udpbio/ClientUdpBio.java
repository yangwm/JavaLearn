/**
 * 
 */
package distributed.udpbio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * 实现UDP/IP+BIO 方式的系统间通讯的代码，客户端：
 * 
 * @author yangwm in Mar 23, 2010 10:11:24 AM
 */
public class ClientUdpBio {

    /**
     * create by yangwm in Mar 23, 2010 10:11:24 AM
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        String host = "localhost";
        int port = 9527;
        int aport = 9528;
        
        DatagramSocket serverSocket = new DatagramSocket(aport);
        InetAddress serverAddress = InetAddress.getByName(host);
        BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));
        
        DatagramSocket socket = new DatagramSocket();
        
        byte[] buffer = new byte[65507];
        DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
        boolean flag = true;
        while (flag) {
            String command = systemIn.readLine();
            byte[] datas = command.getBytes("UTF-8");
            DatagramPacket packet = new DatagramPacket(datas, datas.length, serverAddress, port);
            socket.send(packet);
            
            if (command == null || "quit".equalsIgnoreCase(command.trim())) {
                System.out.println("Client quit!");
                flag = false;
                
                systemIn.close();
                socket.close();
                serverSocket.close();
                continue;
            }
            
            serverSocket.receive(receivePacket);
            String response = new String(receivePacket.getData(), 0, receivePacket.getLength(), "UTF-8");
            System.out.println(response);
        }
        
    }

}

/*
hello yangwm, udp bio
Server response：hello yangwm, udp bio
what are you dong?
Server response：what are you dong?
quit
Client quit!

*/
