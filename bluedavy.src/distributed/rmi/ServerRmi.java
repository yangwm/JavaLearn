/**
 * 
 */
package distributed.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import distributed.rmi.impl.BusinessRmiImpl;

/**
 * 基于RMI实现的服务器端
 * 
 * @author yangwm Jul 16, 2010 10:09:20 AM
 */
public class ServerRmi {
    
    public static void main(String[] args) throws Exception{
        int port = 9527;
        String name = "BusinessDemo";
        BusinessRmi business = new BusinessRmiImpl();
        UnicastRemoteObject.exportObject(business, port);
        Registry registry = LocateRegistry.createRegistry(1099);
        registry.rebind(name, business);
    }
    
}

/*
Server listen on port: 9527
Message from client: abc
Message from client: efg
Message from client: serverquit
Server will be shutdown!

*/
