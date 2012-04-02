/**
 * 
 */
package nio.echo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * 
 * 
 * @author yangwm Jun 15, 2010 10:26:47 AM
 */
public class TCPEchoClient {

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        if ((args.length < 2) || (args.length > 3)) { // Test for correct # of args 
            
            throw new IllegalArgumentException("Parameter(s): <Server> <Word> [<Port>]");
        }

        String server = args[0];// Server name or IP address
        // Convert input String to bytes using the default charset
        byte[] argument = args[1].getBytes();
        int servPort = (args.length == 3) ? Integer.parseInt(args[2]) : 7;
        // Create channel and set to nonblocking
        SocketChannel clntChan = SocketChannel.open();
        clntChan.configureBlocking(false);

        // Initiate connection to server and repeatedly poll until complete
        if (!clntChan.connect(new InetSocketAddress(server, servPort))) {
            while (!clntChan.finishConnect()) {
                System.out.print(".");// Do something else
            }
        }

        ByteBuffer writeBuf = ByteBuffer.wrap(argument);
        ByteBuffer readBuf = ByteBuffer.allocate(argument.length);
        int totalBytesRcvd = 0;// Total bytes received sofar
        int bytesRcvd;// Bytes received in last read

        while (totalBytesRcvd < argument.length) {
            if (writeBuf.hasRemaining()) {
                clntChan.write(writeBuf);
            }

            if ((bytesRcvd = clntChan.read(readBuf)) == -1) {
                throw new SocketException("Connection closed prematurely");
            }

            totalBytesRcvd += bytesRcvd;
            System.out.print(".");// Do something else
        }

        System.out.println("Received:" + // convert to String per default charset
                new String(readBuf.array(), 0, totalBytesRcvd));

        clntChan.close();
    }

}

/*
java nio.echo.TCPEchoClient localhost abc 9527
..................................................................................Received:abc

*/

