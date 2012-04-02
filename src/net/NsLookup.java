/**
 * 
 */
package net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * 域查找  
 * 
 * @author yangwm in Jan 13, 2010 5:02:21 PM
 */
public class NsLookup {

    /**
     * create by yangwm in Jan 13, 2010 5:02:26 PM
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) {
        InetAddress[] addresses = null;
        try {
            addresses = InetAddress.getAllByName("yangwmtestit.com");
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(Arrays.toString(addresses));
        
//        for (int i = 0; i < 10; i++) {
//            System.out.println(getInetAddress("localhost.com"));
//            System.out.println(getInetAddress("testredis1"));
//            
//            System.out.println(newInetSocketAddress("localhost.com"));
//            System.out.println(newInetSocketAddress("testredis1"));
//        }
        
//        System.out.println(Arrays.toString(getAllInetAddress("localhost.com")));
//        System.out.println(Arrays.toString(getAllInetAddress("testredis1")));
//        System.out.println(Arrays.toString(getAllInetAddress("www.sun.com")));
//        System.out.println(Arrays.toString(getAllInetAddress("www.baidu.com")));
//        System.out.println(Arrays.toString(getAllInetAddress("www.google.com")));
//        System.out.println(Arrays.toString(getAllInetAddress("www.baidu.jp")));
    }
    
    
    /**
     * new 通过域名获取ip 
     * 
     * @param name
     * @return
     */
    public static String newInetSocketAddress(String name) {
        String hostAddress = null;
        InetSocketAddress socketAddress = new InetSocketAddress(name, 6379);
        //hostAddress = inetAddress.getHostAddress();
        hostAddress = socketAddress.toString();
        return hostAddress;
    }

    
    /**
     * 通过域名获取ip 
     * 
     * @param name
     * @return
     */
    public static String getInetAddress(String name) {
        String hostAddress = null;
        try {
            InetAddress inetAddress = InetAddress.getByName(name);
            //hostAddress = inetAddress.getHostAddress();
            hostAddress = inetAddress.toString();
        } catch(UnknownHostException uhe) {
            System.err.println("Unable to find: "+name);
        }
        return hostAddress;
    }

    /**
     * 通过域名获取所有ip 
     * 
     * @param name
     * @return
     */
    public static String[] getAllInetAddress(String name) {
        String[] hostAddressArray = null;
        try {
            InetAddress[] inetAddressArray = InetAddress.getAllByName(name);
            hostAddressArray = new String[inetAddressArray.length];
            
            for (int i = 0; i < inetAddressArray.length; i++) {
                //hostAddressArray[i] = inetAddressArray[i].getHostAddress();
                hostAddressArray[i] = inetAddressArray[i].toString();
            }
        } catch(UnknownHostException uhe) {
            System.err.println("Unable to find: "+name);
        }
        return hostAddressArray;
    }

}

/*
100.10.0.150
[Ljava.lang.String;[{100.10.0.150,10.254.249.21,10.253.165.220}]
[Ljava.lang.String;[{72.5.124.61}]
[Ljava.lang.String;[{119.75.213.61,119.75.216.20}]

*/
