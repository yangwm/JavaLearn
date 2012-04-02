/**
 * 
 */
package distributed.rmi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * 基于RMI实现的客户端
 * 
 * @author yangwm Jul 16, 2010 10:10:24 AM
 */
public class ClientRmi {

    public static void main(String[] args) throws Exception{
        Registry registry = LocateRegistry.getRegistry("localhost");
        String name = "BusinessDemo";
        BusinessRmi business = (BusinessRmi) registry.lookup(name);
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
abc
Server response：abc
efg
Server response：efg
serverquit
Client quit!

*/

