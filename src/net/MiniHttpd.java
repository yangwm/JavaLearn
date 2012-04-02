package net;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

/**
 * jdk6新特性——http server 
 * 
 * @author yangwm Jan 17, 2010 5:16:41 PM
 */
public class MiniHttpd {
    public static void main(String[] args) {
        try {
            HttpServer hs = HttpServer.create(new InetSocketAddress(8080),0);// 设置HttpServer的端口为8088
            hs.createContext("/yangwm", new MiniHttpHandler());// 用MiniHttpHandler类内处理到/yangwm的请求
            hs.setExecutor(null); // creates a default executor
            hs.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class MiniHttpHandler implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
        String response = "welcome yangwm's home\n";
        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}

/*
java net.MiniHttpd

D:\Program Files\curl_720_1>curl http://127.0.0.1:8080/yangwm
welcome yangwm's home\n

*/

