/**
 * 
 */
package distributed.webservice;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import distributed.webservice.client.BusinessWebService;
import distributed.webservice.client.BusinessServiceWebService;

/**
 * 基于WebService实现的客户端
 * 
 * @author yangwm Aug 3, 2010 2:11:23 PM
 */
public class ClientWebService {

    public static void main(String[] args) throws Exception {
        BusinessServiceWebService businessService = new BusinessServiceWebService();
        BusinessWebService business = businessService.getBusinessPort();
        BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            String command = systemIn.readLine();
            try{
                System.out.println(business.echo(command));
            } catch(Exception e){
                // IGNORE
            }
            if(command == null 
                    || "quit".equalsIgnoreCase(command.trim()) 
                    || "serverquit".equalsIgnoreCase(command.trim())){
                System.out.println("Client quit!");
                System.exit(0);
            }
        }
    }

}

/*
webservice abc
Server response：webservice abc
jdk7 ok?
Server response：jdk7 ok?
serverquit
Client quit!

*/
