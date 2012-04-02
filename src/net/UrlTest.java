package net;

import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * url tester 
 * 
 * @author yangwm in Aug 16, 2009 4:40:47 PM
 */
public class UrlTest {
    void display() {
        byte buf[]=new byte[100];
        
        try {
            System.out.print("请输入文件的URL地址: ");
            //读取用户输入的URL
            int count=System.in.read(buf);
            
            String addr=new String
            (buf,0,count);
            
            //将用户输入的URL字符串传入URL类对象
            URL url=new URL (addr);
            //创建URLConnection对象，用URL的openConnection方法将连接返回给URLConnection的对象
            //实际上URL的openConnection的返回值就是一个URLConnection
            URLConnection c = url.openConnection();
            //用URLConnection的connect()方法建立连接
            c.connect();
            // 显示该连接的相关信息，这些都是URLConnection的方法
            System.out.println("内容类型: "+c.getContentType());
            System.out.println("内容编码: "+c.getContentEncoding());
            System.out.println("内容长度: "+c.getContentLength());
            System.out.println("内容: "+c.getContent());
            System.out.println("创建日期: "+new Date(c.getDate()));
            System.out.println("最后修改日期: "+new Date(c.getLastModified()));
            System.out.println("终止日期: "+new Date(c.getExpiration()));
            
        } catch (IOException e) {
            System.out.println(e);
        }
        
    }
    
    public static void main(String[] args) {
        UrlTest app=new UrlTest();
        app.display();
    }

    /**
     * 平台的默认编码形式，所以在不同系统上，它就会产生不同的结果。
     * 所以应该总是用UTF-8，而不是其他什么。 
     * UTF-8比起你选的其他的编码形式来说，它能与新的web浏览器和更多的其他软件相兼容。
     * 
     * @author yangwm in Aug 16, 2009 4:44:49 PM
     */
    static class CaseURI {

        /**
         * create by yangwm in Aug 16, 2009 10:49:01 AM
         * @param args
         * @throws UnsupportedEncodingException 
         */
        public static void main(String[] args) throws UnsupportedEncodingException {
            System.out.println(URLEncoder.encode("ZEh5bE5VSntWVkI+", "UTF-8"));
            System.out.println(URLDecoder.decode("ZEh5bE5VSntWVkI%2B", "UTF-8"));
            
            System.out.println(URLEncoder.encode("URI 测试", "UTF-8"));
            System.out.println(URLDecoder.decode("%20URI+%E6%B5%8B%E8%AF%95", "UTF-8"));
        }

    }
    
}

/*
D:\study\tempProject\JavaLearn\src>java UrlTest
请输入文件的URL地址: file:///D:/study/tempDocs/refman-5.1-en.html-chapter/introduction.html#manual-i
nfo
内容类型: text/html
内容编码: null
内容长度: 245399
内容: java.io.BufferedInputStream@173a10f
创建日期: Thu Jan 01 08:00:00 CST 1970
最后修改日期: Sun Jun 28 14:45:26 CST 2009
终止日期: Thu Jan 01 08:00:00 CST 1970

D:\study\tempProject\JavaLearn\src>java UrlTest
请输入文件的URL地址: http://useway.blog.51cto.com/736087/149175
内容类型: text/html; charset=GB2312
内容编码: null
内容长度: -1
内容: sun.net.www.protocol.http.HttpURLConnection$HttpInputStream@e0e1c6
创建日期: Sun Jul 19 10:29:52 CST 2009
最后修改日期: Thu Jan 01 08:00:00 CST 1970
终止日期: Thu Jan 01 08:00:00 CST 1970


ZEh5bE5VSntWVkI%2B
ZEh5bE5VSntWVkI+
URI+%E6%B5%8B%E8%AF%95
 URI 测试

*/
