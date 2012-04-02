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

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

/**
 * SSLsocket echo Server
 * 
 * @author yangwm Apr 16, 2010 10:46:02 AM
 */
public class SSLServer {
    
    private int port = 9999;
    
    private SSLServerSocket serverSocket;
    
    String keyStoreFile = "keyStore";
    String pass = "123456";
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            new SSLServer().service();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public SSLServer() throws Exception{
        
        SSLContext context = createSSLContext();
        SSLServerSocketFactory factory = context.getServerSocketFactory();
        serverSocket = (SSLServerSocket) factory.createServerSocket(port);
        serverSocket.setNeedClientAuth(true);
        
        System.out.println("SSLSOCKET服务器启动");
        System.out.println(serverSocket.getUseClientMode() ? 
                "客户模式" : "服务器模式");
        System.out.println(serverSocket.getNeedClientAuth() ?
                "需要验证对方身份" : "不需要验证对方身份");
        
        String[] supported = serverSocket.getSupportedCipherSuites();
        serverSocket.setEnabledCipherSuites(supported);
    }
    
    public SSLContext createSSLContext() throws Exception{
        
        KeyStore ks = KeyStore.getInstance("JKS");
        char[] pwd = pass.toCharArray(); 
        
        ks.load(new FileInputStream(keyStoreFile), pwd);
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(ks, pwd);

        // 初始化密钥管理器
        KeyManagerFactory keyManagerFactory = KeyManagerFactory
                .getInstance("SunX509");
        keyManagerFactory.init(ks, pwd);
        KeyManager[] keyManagers = keyManagerFactory.getKeyManagers();

        // 初始化信任管理器
        TrustManagerFactory trustManagerFactory = TrustManagerFactory
                .getInstance("SunX509");
        trustManagerFactory.init(ks);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();

        // 初始化SSL上下文
        SSLContext context = SSLContext.getInstance("SSL");
        context.init(keyManagers, trustManagers, new SecureRandom());
        
        return context;
    }
    
    
    public String echo(String msg){
        return "echo:" + msg;
    }
    
    private PrintWriter getWriter(Socket socket) throws IOException{
        OutputStream out = socket.getOutputStream();
        return new PrintWriter(out, true);
    }
    
    public BufferedReader getReader(Socket socket) throws IOException{
        InputStream in = socket.getInputStream();
        return new BufferedReader(new InputStreamReader(in));
    }
    
    public void service(){
        while(true){
            Socket socket = null;
            
            try {
                socket = serverSocket.accept();
                System.out.println("new connection accepted"
                        + socket.getInetAddress() + ":" + socket.getPort());
                
                BufferedReader reader = this.getReader(socket);
                PrintWriter writer = this.getWriter(socket);
                
                String msg = null;
                
                while((msg = reader.readLine()) != null){
                    System.out.println("recived msg : " + msg);
                    writer.println(this.echo(msg));
                    if("quit".equals(msg)){
                        System.out.println("Client quit, so this socket may be close!");
                        break;
                    }
                }
                
            } catch (IOException e) {
                // TODO Auto-generated catch block
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
    
}

/*
SSLSOCKET服务器启动
服务器模式
需要验证对方身份
new connection accepted/127.0.0.1:1293
recived msg : hello, my name is yangwm
recived msg : haha
recived msg : quit
Client quit, so this socket may be close!

*/
