package net.sslsocket;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.KeyStore;
import java.security.SecureRandom;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

/**
 * SSLsocket client
 * 
 * @author yangwm Apr 16, 2010 10:46:28 AM
 */
public class SSLClient {
    
    
    private String host = "localhost";
//  private String host = "192.168.1.102";
    
    private int port = 9999;
    
    private SSLSocket socket = null;

    String keyStoreFile = "keyStore";
    String pass = "123456";
    
    public SSLClient() throws Exception{
        
        SSLContext context = createSSLContext();
        SSLSocketFactory factory = context.getSocketFactory();
        socket = (SSLSocket) factory.createSocket(host, port);
        String[] support = socket.getSupportedCipherSuites();
        socket.setEnabledCipherSuites(support);
        System.out.println(socket.getUseClientMode() ? "客户模式" : "服务器模式");
        
    }

    public SSLContext createSSLContext() throws Exception{
        
        KeyStore ks = KeyStore.getInstance("JKS");
        char[] pwd = pass.toCharArray(); 
        
        ks.load(new FileInputStream(keyStoreFile), pwd);
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(ks, pwd);
        
        // 初始化信任管理器
        TrustManagerFactory trustManagerFactory = TrustManagerFactory
                .getInstance("SunX509");
        trustManagerFactory.init(ks);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        
        SSLContext context = SSLContext.getInstance("SSL");
        context.init(kmf.getKeyManagers(), trustManagers, new SecureRandom());
        
        return context;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        
        try {
            new SSLClient().talk();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    private PrintWriter getWriter(Socket socket) throws IOException{
        OutputStream out = socket.getOutputStream();
        return new PrintWriter(out, true);
    }
    
    public BufferedReader getReader(Socket socket) throws IOException{
        InputStream in = socket.getInputStream();
        return new BufferedReader(new InputStreamReader(in));
    }

    public void talk(){
        
        try {
            BufferedReader reader = this.getReader(socket);
            PrintWriter writer = this.getWriter(socket);
            
            BufferedReader localReader = new BufferedReader(new InputStreamReader(System.in));
            String msg = null;
            
            while((msg = localReader.readLine()) != null ){
                writer.println(msg);
                System.out.println(reader.readLine());
                
                if("quit".equals(msg)){
                    System.out.println("Client quit!");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
    }

}

/*
客户模式
hello, my name is yangwm
echo:hello, my name is yangwm
haha
echo:haha
quit
echo:quit
Client quit!

*/
