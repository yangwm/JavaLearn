/**
 * 
 */
package net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * 
 * 
 * @author yangwm Jan 9, 2011 12:56:26 AM
 */
public class CreateSocketTest {

    /**
     * @param args
     * @throws InterruptedException 
     * @throws IOException 
     */
    public static void main(String[] args) throws InterruptedException, IOException {
        for (int i = 0; i < 1000; i++) {
            Thread.sleep(3000);
            newSocketConnect();
        }
    }

    

    public static void newSocketConnect() throws IOException {
        InetSocketAddress socketAddress = new InetSocketAddress("192.168.18.128", 6379);

        Socket socket = new Socket();
        socket.connect(socketAddress);
    }

}

/*
if server shutdown may be:

Exception in thread "main" java.net.ConnectException: Connection refused: connect
    at java.net.TwoStacksPlainSocketImpl.socketConnect(Native Method)
    at java.net.AbstractPlainSocketImpl.doConnect(Unknown Source)
    at java.net.AbstractPlainSocketImpl.connectToAddress(Unknown Source)
    at java.net.AbstractPlainSocketImpl.connect(Unknown Source)
    at java.net.PlainSocketImpl.connect(Unknown Source)
    at java.net.SocksSocketImpl.connect(Unknown Source)
    at java.net.Socket.connect(Unknown Source)
    at java.net.Socket.connect(Unknown Source)
    at net.CreateSocketTest.newSocketConnect(CreateSocketTest.java:35)
    at net.CreateSocketTest.main(CreateSocketTest.java:25)
    
*/

