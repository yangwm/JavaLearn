/**
 * 
 */
package distributed.multicast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * 基于多播方式实现网络通讯的Client 端
 * 
 * @author yangwm in Mar 23, 2010 5:11:22 PM
 */
public class ClientMulticast {

    /**
     * create by yangwm in Mar 23, 2010 5:11:22 PM
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        String host = "224.1.1.1";
        int port=9527;
        int aport=9528;
        
        InetAddress groupAddress = InetAddress.getByName(host);
        MulticastSocket serverSocket = new MulticastSocket(aport);
        serverSocket.joinGroup(groupAddress);
        
        MulticastSocket socket = new MulticastSocket();
        socket.joinGroup(groupAddress);
        
        byte[] buffer = new byte[65507];
        DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
        
        BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));
        boolean flag = true;
        while (flag) {
            String command = systemIn.readLine();
            byte[] datas = command.getBytes("UTF-8");
            DatagramPacket packet = new DatagramPacket(datas, datas.length, groupAddress, port);
            socket.send(packet);
            
            if (command == null || "quit".equalsIgnoreCase(command.trim())) {
                System.out.println("Client quit!");
                flag = false;
                
                socket.leaveGroup(groupAddress);
                socket.close();
                serverSocket.leaveGroup(groupAddress);
                serverSocket.close();
                continue;
            }
            
            serverSocket.receive(receivePacket);
            String receiveResponse = new String(receivePacket.getData(), 0, receivePacket.getLength(), "UTF-8");
            System.out.println(receiveResponse);
        }
    }

}

/*
hello, yangwm, multicast
Server response：hello, yangwm, multicast
what are you doing?
Server response：what are you doing?
quit
Client quit!

*/
