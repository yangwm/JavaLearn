/**
 * 
 */
package distributed.rmi.impl;

import java.rmi.RemoteException;

import distributed.rmi.BusinessRmi;

/**
 * 业务功能实现类
 * 
 * @author yangwm Jul 16, 2010 10:07:42 AM
 */
public class BusinessRmiImpl implements BusinessRmi {

    /* (non-Javadoc)
     * @see distributed.rmi.Business#echo(java.lang.String)
     */
    public String echo(String message) throws RemoteException {
        System.out.println("Message from client: "+message);
        if("quit".equalsIgnoreCase(message.toString())){
            System.out.println("Client will be quit!");
        } else if("serverquit".equalsIgnoreCase(message.toString())){
            System.out.println("Server will be shutdown!");
            System.exit(0);
        }
        return "Server response："+message;
    }

}
