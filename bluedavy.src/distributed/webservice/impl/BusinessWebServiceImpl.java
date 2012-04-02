/**
 * 
 */
package distributed.webservice.impl;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import distributed.webservice.BusinessWebService;


/**
 * 业务功能实现类
 * 
 * @author yangwm Jul 16, 2010 10:07:42 AM
 */
@WebService(name="Business",serviceName="BusinessService",targetNamespace="http://webservice.distributed/client")
@SOAPBinding(style=SOAPBinding.Style.RPC)
public class BusinessWebServiceImpl implements BusinessWebService {

    /* (non-Javadoc)
     * @see distributed.rmi.BusinessRmi#echo(java.lang.String)
     */
    public String echo(String message) {
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
