/**
 * 
 */
package httpclient;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * @author yangwm in Dec 12, 2009 4:03:10 PM
 */
public class HttpClientBegin {

    /**
     * create by yangwm in Dec 12, 2009 4:03:10 PM
     * @param args
     */
    public static void main(String[] args) throws Exception {
        // 初始化，此处构造函数就与3.1中不同
        HttpClient httpclient = new DefaultHttpClient();

        HttpHost targetHost = new HttpHost("www.xinhuanet.com");
        //HttpHost targetHost = new HttpHost("www.google.cn");
        HttpGet httpget = new HttpGet("/");
        //HttpGet httpget = new HttpGet("http://www.apache.org/");

        // 查看默认request头部信息
        System.out.println("Accept-Charset:" + httpget.getFirstHeader("Accept-Charset"));
        // 以下这条如果不加会发现无论你设置Accept-Charset为gbk还是utf-8，他都会默认返回gb2312（本例针对google.cn来说）
        httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1.2)");
        // 用逗号分隔显示可以同时接受多种编码
        httpget.setHeader("Accept-Language", "zh-cn,zh;q=0.5");
        httpget.setHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
        // 验证头部信息设置生效
        System.out.println("Accept-Charset:" + httpget.getFirstHeader("Accept-Charset").getValue());

        // Execute HTTP request
        System.out.println("executing request " + httpget.getURI());
        HttpResponse response = httpclient.execute(targetHost, httpget);
        //HttpResponse response = httpclient.execute(httpget);

        System.out.println("----------------------------------------");
        System.out.println("Location: " + response.getLastHeader("Location"));
        System.out.println(response.getStatusLine().getStatusCode());
        System.out.println(response.getLastHeader("Content-Type"));
        System.out.println(response.getLastHeader("Content-Length"));
        
        System.out.println("----------------------------------------");

        // 判断页面返回状态判断是否进行转向抓取新链接
        int statusCode = response.getStatusLine().getStatusCode();
        if ((statusCode == HttpStatus.SC_MOVED_PERMANENTLY) ||
                (statusCode == HttpStatus.SC_MOVED_TEMPORARILY) ||
                (statusCode == HttpStatus.SC_SEE_OTHER) ||
                (statusCode == HttpStatus.SC_TEMPORARY_REDIRECT)) {
            // 此处重定向处理  此处还未验证
            String newUri = response.getLastHeader("Location").getValue();
            httpclient = new DefaultHttpClient();
            httpget = new HttpGet(newUri);
            response = httpclient.execute(httpget);
        }

        // Get hold of the response entity
        HttpEntity entity = response.getEntity();
        
        // 查看所有返回头部信息
        Header headers[] = response.getAllHeaders();
        int ii = 0;
        while (ii < headers.length) {
            System.out.println(headers[ii].getName() + ": " + headers[ii].getValue());
            ++ii;
        }
        
        // If the response does not enclose an entity, there is no need
        // to bother about connection release
        if (entity != null) {
            // 将源码流保存在一个byte数组当中，因为可能需要两次用到该流，
            byte[] bytes = EntityUtils.toByteArray(entity);
            
            // 如果头部Content-Type中包含了编码信息，那么我们可以直接在此处获取
            String charSet = EntityUtils.getContentCharSet(entity);

            System.out.println("In header charSet: " + charSet);
            // 如果头部中没有，那么我们需要 查看页面源码，这个方法虽然不能说完全正确，因为有些粗糙的网页编码者没有在页面中写头部编码信息
            if (null == charSet || "".equals(charSet)) {
                String regEx="(?=<meta).*?(?<=charset=[\\'|\\\"]?)([[a-z]|[A-Z]|[0-9]|-]*)";
                Pattern p=Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
                Matcher m=p.matcher(new String(bytes));  // 默认编码转成字符串，因为我们的匹配中无中文，所以串中可能的乱码对我们没有影响
                boolean result=m.find();
                if (result == true && m.groupCount() == 1) {
                    charSet = m.group(1);
                } else {
                    charSet = "";
                }
            }
            System.out.println("Last get charSet: " + charSet);
            // 至此，我们可以将原byte数组按照正常编码专成字符串输出（如果找到了编码的话）
            if (null == charSet || "".equals(charSet)) {
                System.out.println("Encoding string is: " + new String(bytes));
            } else {
                System.out.println("Encoding string is: " + new String(bytes, charSet));
            }
            
        }

        httpclient.getConnectionManager().shutdown();        
    }

}

/* www.google.cn / : 
Accept-Charset:null
Accept-Charset:GB2312,utf-8;q=0.7,*;q=0.7
executing request /
----------------------------------------
Location: null
200
Content-Type: text/html; charset=UTF-8
null
----------------------------------------
Date: Sat, 12 Dec 2009 09:43:41 GMT
Expires: -1
Cache-Control: private, max-age=0
Content-Type: text/html; charset=UTF-8
Set-Cookie: PREF=ID=ee8d8f6c1271e78d:NW=1:TM=1260611021:LM=1260611021:S=LXrXe7O8KWRJZ2Hs; expires=Mon, 12-Dec-2011 09:43:41 GMT; path=/; domain=.google.cn
Set-Cookie: NID=29=az96Uxu4xocMoQqJDE28wPnCwIN6eP76v3oBUsX99FMWa26d4LXNnLb2HexrqzVqh3LcIBOP8U8J-4lyN2nAH6BYutLEOtwiJDmu55wTmDgIa7QpdL9FHXuzpoYDj93q; expires=Sun, 13-Jun-2010 09:43:41 GMT; path=/; domain=.google.cn; HttpOnly
Server: gws
X-XSS-Protection: 0
Transfer-Encoding: chunked
In header: UTF-8
Last get: UTF-8
Encoding string is: ...
...... 
*/

/* http://www.apache.org/ : 
Accept-Charset:null
Accept-Charset:GB2312,utf-8;q=0.7,*;q=0.7
executing request http://www.apache.org/
----------------------------------------
Location: null
200
Content-Type: text/html
Content-Length: 27418
----------------------------------------
Date: Sat, 12 Dec 2009 09:53:30 GMT
Server: Apache/2.3.4 (Unix) mod_fcgid/2.3.2-dev
Last-Modified: Mon, 07 Dec 2009 12:37:37 GMT
ETag: "b2eaca-6b1a-47a22b8ef2640"
Accept-Ranges: bytes
Content-Length: 27418
Cache-Control: max-age=86400
Expires: Sun, 13 Dec 2009 09:53:30 GMT
Vary: Accept-Encoding
Keep-Alive: timeout=30, max=100
Connection: Keep-Alive
Content-Type: text/html
In header charSet: null
Last get charSet: utf-8
Encoding string is: ...
......
*/
