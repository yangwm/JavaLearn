/**
 * 
 */
package util;

/**
 * UrlHelper test
 * 
 * @author yangwm in Jan 15, 2010 11:27:17 AM
 */
public class UrlHelperTest {

    /**
     * create by yangwm in Jan 15, 2010 11:27:17 AM
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(UrlHelper.encode(",/?:@&=+$#"));
        System.out.println(UrlHelper.encode("%2C%2F%3F%3A%40%26%3D%2B%24%23"));
        
        System.out.println(UrlHelper.decode(",/?:@&=+$#"));
        System.out.println(UrlHelper.decode("%2C%2F%3F%3A%40%26%3D%2B%24%23"));
        System.out.println(UrlHelper.decode("%252C%252F%253F%253A%2540%2526%253D%252B%2524%2523"));
        
        System.out.println(UrlHelper.detectUrl("http://mail.qq.com/", null, null));
        System.out.println(UrlHelper.detectUrl("https://mail.qq.com/", null, null));
        
        System.out.println(UrlHelper.detectUrl("http://login.taobao.com/member/login.jhtml", null, null));
        System.out.println(UrlHelper.detectUrl("https://login.taobao.com/member/login.jhtml", null, null));
        
        System.out.println(UrlHelper.detectUrl("http://www.google.com/", null, null));
        System.out.println(UrlHelper.detectUrl("https://www.google.com/", null, null));
        
        System.out.println(UrlHelper.detectUrl("http://twitter.com/", null, null));
        System.out.println(UrlHelper.detectUrl("https://twitter.com/", null, null));
        
        System.out.println(UrlHelper.detectUrl("http://userstream.twitter.com/", null, null));
        System.out.println(UrlHelper.detectUrl("https://userstream.twitter.com/", null, null));
    }

}

/*
%2C%2F%3F%3A%40%26%3D%2B%24%23
%252C%252F%253F%253A%2540%2526%253D%252B%2524%2523
,/?:@&= $#
,/?:@&=+$#
%2C%2F%3F%3A%40%26%3D%2B%24%23
15:35:51,390 [main] DEBUG [UrlHelper.detectUrl(66)]  - >>>detectUrl(http://mail.qq.com/, null, null)
15:35:51,921 [main] DEBUG [UrlHelper.detectUrl(91)]  - response.getStatusLine()=HTTP/1.1 200 OK
15:35:51,921 [main] DEBUG [UrlHelper.detectUrl(104)]  - <<<detectUrl(http://mail.qq.com/, null, null) - return value=true
true
15:35:51,921 [main] DEBUG [UrlHelper.detectUrl(66)]  - >>>detectUrl(https://mail.qq.com/, null, null)
15:35:52,484 [main] DEBUG [UrlHelper.detectUrl(91)]  - response.getStatusLine()=HTTP/1.1 200 OK
15:35:52,484 [main] DEBUG [UrlHelper.detectUrl(104)]  - <<<detectUrl(https://mail.qq.com/, null, null) - return value=true
true
15:35:52,484 [main] DEBUG [UrlHelper.detectUrl(66)]  - >>>detectUrl(http://login.taobao.com/member/login.jhtml, null, null)
15:35:52,578 [main] DEBUG [UrlHelper.detectUrl(91)]  - response.getStatusLine()=HTTP/1.1 200 OK
15:35:52,578 [main] DEBUG [UrlHelper.detectUrl(104)]  - <<<detectUrl(http://login.taobao.com/member/login.jhtml, null, null) - return value=true
true
15:35:52,578 [main] DEBUG [UrlHelper.detectUrl(66)]  - >>>detectUrl(https://login.taobao.com/member/login.jhtml, null, null)
15:35:52,828 [main] DEBUG [UrlHelper.detectUrl(91)]  - response.getStatusLine()=HTTP/1.1 200 OK
15:35:52,828 [main] DEBUG [UrlHelper.detectUrl(104)]  - <<<detectUrl(https://login.taobao.com/member/login.jhtml, null, null) - return value=true
true
15:35:52,828 [main] DEBUG [UrlHelper.detectUrl(66)]  - >>>detectUrl(http://www.google.com/, null, null)
15:35:57,234 [main] DEBUG [UrlHelper.detectUrl(91)]  - response.getStatusLine()=HTTP/1.0 200 OK
15:35:57,234 [main] DEBUG [UrlHelper.detectUrl(104)]  - <<<detectUrl(http://www.google.com/, null, null) - return value=true
true
15:35:57,234 [main] DEBUG [UrlHelper.detectUrl(66)]  - >>>detectUrl(https://www.google.com/, null, null)
15:35:57,250 [main] ERROR [UrlHelper.detectUrl(97)]  - 
javax.net.ssl.SSLPeerUnverifiedException: peer not authenticated
    at sun.security.ssl.SSLSessionImpl.getPeerCertificates(Unknown Source)
    at org.apache.http.conn.ssl.AbstractVerifier.verify(AbstractVerifier.java:128)
    at org.apache.http.conn.ssl.SSLSocketFactory.connectSocket(SSLSocketFactory.java:339)
    at org.apache.http.impl.conn.DefaultClientConnectionOperator.openConnection(DefaultClientConnectionOperator.java:123)
    at org.apache.http.impl.conn.AbstractPoolEntry.open(AbstractPoolEntry.java:147)
    at org.apache.http.impl.conn.AbstractPooledConnAdapter.open(AbstractPooledConnAdapter.java:101)
    at org.apache.http.impl.client.DefaultRequestDirector.execute(DefaultRequestDirector.java:381)
    at org.apache.http.impl.client.AbstractHttpClient.execute(AbstractHttpClient.java:641)
    at org.apache.http.impl.client.AbstractHttpClient.execute(AbstractHttpClient.java:576)
    at org.apache.http.impl.client.AbstractHttpClient.execute(AbstractHttpClient.java:554)
    at util.UrlHelper.detectUrl(UrlHelper.java:83)
    at util.UrlHelperTest.main(UrlHelperTest.java:32)
15:35:57,250 [main] DEBUG [UrlHelper.detectUrl(104)]  - <<<detectUrl(https://www.google.com/, null, null) - return value=false
false
15:35:57,250 [main] DEBUG [UrlHelper.detectUrl(66)]  - >>>detectUrl(http://userstream.twitter.com/, null, null)
15:38:57,187 [main] DEBUG [UrlHelper.detectUrl(91)]  - response.getStatusLine()=HTTP/1.0 504 Gateway Time-out
15:38:57,187 [main] DEBUG [UrlHelper.detectUrl(104)]  - <<<detectUrl(http://userstream.twitter.com/, null, null) - return value=false
false
15:38:57,187 [main] DEBUG [UrlHelper.detectUrl(66)]  - >>>detectUrl(https://userstream.twitter.com/, null, null)
15:38:57,203 [main] ERROR [UrlHelper.detectUrl(97)]  - 
javax.net.ssl.SSLPeerUnverifiedException: peer not authenticated
    at sun.security.ssl.SSLSessionImpl.getPeerCertificates(Unknown Source)
    at org.apache.http.conn.ssl.AbstractVerifier.verify(AbstractVerifier.java:128)
    at org.apache.http.conn.ssl.SSLSocketFactory.connectSocket(SSLSocketFactory.java:339)
    at org.apache.http.impl.conn.DefaultClientConnectionOperator.openConnection(DefaultClientConnectionOperator.java:123)
    at org.apache.http.impl.conn.AbstractPoolEntry.open(AbstractPoolEntry.java:147)
    at org.apache.http.impl.conn.AbstractPooledConnAdapter.open(AbstractPooledConnAdapter.java:101)
    at org.apache.http.impl.client.DefaultRequestDirector.execute(DefaultRequestDirector.java:381)
    at org.apache.http.impl.client.AbstractHttpClient.execute(AbstractHttpClient.java:641)
    at org.apache.http.impl.client.AbstractHttpClient.execute(AbstractHttpClient.java:576)
    at org.apache.http.impl.client.AbstractHttpClient.execute(AbstractHttpClient.java:554)
    at util.UrlHelper.detectUrl(UrlHelper.java:83)
    at util.UrlHelperTest.main(UrlHelperTest.java:35)
15:38:57,203 [main] DEBUG [UrlHelper.detectUrl(104)]  - <<<detectUrl(https://userstream.twitter.com/, null, null) - return value=false
false

*/
