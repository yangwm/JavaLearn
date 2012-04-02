/**
 * 
 */
package net;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import org.apache.commons.net.telnet.TelnetClient;

/**
 * @author yangwm in Feb 25, 2010 4:08:34 PM
 */
public class CommonsTelnetTest {
    private final String ip = "127.0.0.1"; // 要telnet的IP地址
    private final String port = "23"; //端口号，默认23
    private final String user = "yangwm";//用户名
    private final String pwd = "yangwm"; //用户密码
    private final String osTag = ">";// 系统标示符号
    private final TelnetClient tc = new TelnetClient(); //新建一个 TelnetClient对象，此对象是 commons-net-2.0.jar包提供
    private InputStream in; // 输入流，接收返回信息
    private PrintStream out; //像 服务器写入 命令

    public void connect() {
           try {
                  tc.connect(ip, Integer.parseInt(port));
                  in = tc.getInputStream();
                  out = new PrintStream(tc.getOutputStream());
                  
           } catch (Exception e) {
                  System.out.println("connect error !");
           }
    }

    public String execute(String command) {
        System.out.println(command);
           connect();
           System.out.println(command);
           out.println(command);
           out.flush();

           StringBuffer sb = new StringBuffer();
           try {
                  char ch = (char) in.read();
                  while (true) {
                         sb.append(ch);
                         if (ch == osTag.charAt(osTag.length() - 1)) {
                                if (sb.toString().endsWith(osTag)) {
                                       return sb.toString();
                                }
                         }
                         ch = (char) in.read();
                  }
           } catch (IOException e) {
                  e.printStackTrace();
           }
           return "error! when the program execute";
    }

    /**
     * create by yangwm in Feb 25, 2010 4:08:35 PM
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(new CommonsTelnetTest().execute("ipconfig"));
    }

}
