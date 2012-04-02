/**
 * 
 */
package guice.print;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * @author yangwm in Jan 3, 2010 8:58:59 PM
 */
public class ClientTest {

    /**
     * create by yangwm in Jan 3, 2010 8:58:59 PM
     * @param args
     */
    public static void main(String[] args) {
        new ClientTest().testPrintString();
    }


    /**
     * Client client = new Client();
     * inject.injectMembers(client);
     *   
     * create by yangwm in Jan 3, 2010 9:56:03 PM
     */
    public void testPrintString() {
        PrintModule module = new PrintModule();
        Injector inject = Guice.createInjector(module);
        
        for (int i = 0; i < 3; i++) {
            Client client = inject.getInstance(Client.class);
            System.out.println("client.getPrintService(" + i + "):" + client.getPrintService());
            client.printString();
        }
    }
    
}

/*
1. PrintModule中未将PrintServiceImpl绑定为单实例对象
client.getPrintService(0):guice.print.PrintServiceImpl@1e893df
Hello world!
client.getPrintService(1):guice.print.PrintServiceImpl@443226
Hello world!
client.getPrintService(2):guice.print.PrintServiceImpl@1386000
Hello world!

2. PrintModule中将PrintServiceImpl绑定为单实例对象
client.getPrintService(0):guice.print.PrintServiceImpl@1662dc8
Hello world!
client.getPrintService(1):guice.print.PrintServiceImpl@1662dc8
Hello world!
client.getPrintService(2):guice.print.PrintServiceImpl@1662dc8
Hello world!
*/
