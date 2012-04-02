/**
 * 
 */
package distributed.udpbio;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * 实现UDP/IP+BIO 方式的系统间通讯的代码，服务器端：
 * 
 * @author yangwm in Mar 23, 2010 10:11:24 AM
 */
public class ServerUdpBio {

    /**
     * create by yangwm in Mar 23, 2010 10:11:24 AM
     * @param args
     * @throws IOException 
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        String host = "localhost";
        int port = 9527;
        int aport = 9528;
        
        DatagramSocket serverSocket = new DatagramSocket(port);
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress serverAddress = InetAddress.getByName(host);
        byte[] buffer = new byte[65507];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        while (true) {
            serverSocket.receive(packet);
            String line = new String(packet.getData(), 0, packet.getLength(), "UTF-8");
            System.out.println("Message from client: "+ line);
            
            if ("quit".equalsIgnoreCase(line.trim())) {
                System.out.println("Server has been shutdown!");
                
                serverSocket.close();
                System.exit(0);
            } else {
                packet.setLength(buffer.length);
                
                String response = "Server response：" + line;
                byte[] datas = response.getBytes("UTF-8");
                DatagramPacket responsePacket = new DatagramPacket(datas, datas.length, serverAddress, aport);
                clientSocket.send(responsePacket);
                Thread.sleep(100);
            }
        }
        
    }

}

/*
Message from client: hello yangwm, udp bio
Message from client: what are you dong?
Message from client: quit
Server has been shutdown!

*/

