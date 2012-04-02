/**
 * 
 */
package net;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Network Interface Test
 * 
 * @author yangwm Nov 25, 2010 11:03:49 AM
 */
public class NetworkInterfaceTest {

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        String localhostAddr = InetAddress.getLocalHost().getHostAddress();
        System.out.println("ip:" + localhostAddr);

        // Get the Local interfaces using the NetworkInterface class
        List<String> interfaces = new ArrayList<String>();
        Enumeration<NetworkInterface> ifEnum = NetworkInterface.getNetworkInterfaces();
        if (ifEnum != null) {
            // get the enum
            while (ifEnum.hasMoreElements()) {
                NetworkInterface localIf = (NetworkInterface) (ifEnum.nextElement());
                // get the addresses of this interface
                Enumeration<InetAddress> ifAddrEnum = localIf.getInetAddresses();
                while (ifAddrEnum.hasMoreElements()) {
                    InetAddress ifAddr = (InetAddress) (ifAddrEnum.nextElement());
                    if (!ifAddr.getHostAddress().contains(":"))
                        interfaces.add(ifAddr.getHostAddress());
                }
            }
        } else {
            throw new UnknownHostException("Can't find network interfaces.");
        }

        System.out.println("interfaces:" + interfaces);
    }

}

/*
ip:10.218.31.122
interfaces:[127.0.0.1, 192.168.18.1, 192.168.235.1, 10.218.31.122]

*/

