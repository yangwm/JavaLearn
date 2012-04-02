/**
 * 
 */
package onedot;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author yangwm in Feb 19, 2010 1:06:26 AM
 */
public class FTPClient {

    /**
     * create by yangwm in Feb 19, 2010 1:06:27 AM
     * @param args
     */
    public static void main(String[] args) {
        Socket socket = null;
        try {
            socket = new Socket("192.168.0.1",21);  
            InputStream is = socket.getInputStream();  
            OutputStream os = socket.getOutputStream();
            //接收初始链接信息
            byte[] buffer = new byte[100];
            int length = is.read(buffer);
            String s = new String(buffer, 0, length);
            System.out.println(s);
            //发送用户名
            String str = "USER useway\n";
            os.write(str.getBytes());
            //得到返回值
            length = is.read(buffer);
            s = new String(buffer, 0, length);
            System.out.println(s);       
            //发送密码
            str = "PASS !@#$%abcd\n";
            os.write(str.getBytes());
            //得到返回值
            length = is.read(buffer);
            s = new String(buffer, 0, length);
            System.out.println(s);
            //发送切换文件夹指令
            str = "CWD /home/useway\n";
            os.write(str.getBytes());
            //得到返回值
            length = is.read(buffer);
            s = new String(buffer, 0, length);
            System.out.println(s);
            //设置模式
            str = "EPSV ALL\n";
            os.write(str.getBytes());
            //得到返回值
            length = is.read(buffer);
            s = new String(buffer, 0, length);
            System.out.println(s);       
            //得到被动监听信息
            str = "EPSV\n";
            os.write(str.getBytes());
            //得到返回值
            length = is.read(buffer);
            s = new String(buffer, 0, length);
            System.out.println(s);
            //取得FTP被动监听的端口号
            String portlist=s.substring(s.indexOf("(|||")+4,s.indexOf("|)"));
            System.out.println(portlist);
            //实例化ShowList线程类，链接FTP被动监听端口号
            ShowList sl=new ShowList();
            sl.port=Integer.parseInt(portlist);
            sl.start();
            //执行LIST命令
            str = "LIST\n";
            os.write(str.getBytes());
            //得到返回值
            length = is.read(buffer);
            s = new String(buffer, 0, length);
            System.out.println(s);
            //关闭链接
            is.close();
            os.close();
        } catch(Exception e) {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        
    }

}

//得到被动链接信息类，这个类是多线程的
class ShowList extends Thread{
    public int port=0;
    
    @Override
    public void run(){
        Socket socket = null;
        try{
            socket = new Socket("192.168.0.1",this.port);
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            byte[] buffer = new byte[10000];
            int length = is.read(buffer);
            String s = new String(buffer, 0, length);
            System.out.println(s);
            //关闭链接
            is.close();
            os.close();
            socket.close();
        } catch(Exception e) {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}

