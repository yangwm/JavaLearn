/**
 * 
 */
package distributed.webservice;

import javax.xml.ws.Endpoint;

import distributed.webservice.impl.BusinessWebServiceImpl;

/**
 * 基于WebService实现的服务器端
 * 
 * @author yangwm Aug 3, 2010 2:10:23 PM
 */
public class ServerWebService {

    public static void main(String[] args) {
        Endpoint.publish("http://localhost:9527/BusinessService", new BusinessWebServiceImpl());
        System.out.println("Server has beed started");
    }

}

/*
Server has beed started
Message from client: webservice abc
Message from client: jdk7 ok?
Message from client: serverquit
Server will be shutdown!

*/
