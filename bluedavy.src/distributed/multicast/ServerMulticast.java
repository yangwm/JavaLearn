/**
 * 
 */
package distributed.multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * 基于多播方式实现网络通讯的Server 端
 * 
 * @author yangwm in Mar 23, 2010 4:51:03 PM
 */
public class ServerMulticast {

    /**
     * create by yangwm in Mar 23, 2010 4:51:03 PM
     * @param args
     * @throws IOException 
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        String host = "224.1.1.1";
        int port = 9527;
        int aport = 9528;
        
        InetAddress groupAddress = InetAddress.getByName(host);
        MulticastSocket serverSocket = new MulticastSocket(port);
        serverSocket.joinGroup(groupAddress);
        
        MulticastSocket clientSocket = new MulticastSocket();
        clientSocket.joinGroup(groupAddress);
        
        byte[] buffer = new byte[65507];
        DatagramPacket packet = new DatagramPacket(buffer,buffer.length);
        while (true) {
            serverSocket.receive(packet);
            String line = new String(packet.getData(), 0, packet.getLength(), "UTF-8");
            System.out.println("Message from client: " + line);
            
            if (line == null || "quit".equalsIgnoreCase(line.trim())) {
                System.out.println("Server has been shutdown!");
                
                serverSocket.leaveGroup(groupAddress);
                serverSocket.close();
                System.exit(0);
            } else {
                packet.setLength(buffer.length);
                String response = "Server response：" + line;
                byte[] datas = response.getBytes("UTF-8");
                
                DatagramPacket responsePacket = new DatagramPacket(datas, datas.length, groupAddress, aport);
                clientSocket.send(responsePacket);
                Thread.sleep(100);
            }
        }
    }

}

/*
Message from client: hello, yangwm, multicast
Message from client: what are you doing?
Message from client: quit
Server has been shutdown!

*/
