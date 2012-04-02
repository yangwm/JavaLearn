/**
 * 
 */
package util;

/**
 * @author yangwm in Dec 11, 2009 3:18:41 PM
 */
public class UrlDetectorTest {

    /**
     * create by yangwm in Dec 11, 2009 3:18:41 PM
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(UrlDetector.detectUrl("http://mail.qq.com/"));
        System.out.println(UrlDetector.detectUrl("https://mail.qq.com/"));
        
        System.out.println(UrlDetector.detectUrl("http://login.taobao.com/member/login.jhtml"));
        System.out.println(UrlDetector.detectUrl("https://login.taobao.com/member/login.jhtml"));
        
        System.out.println(UrlDetector.detectUrl("http://www.google.com/"));
        System.out.println(UrlDetector.detectUrl("https://www.google.com/"));
        
        System.out.println(UrlDetector.detectUrl("http://twitter.com/"));
        System.out.println(UrlDetector.detectUrl("https://twitter.com/"));
        
        System.out.println(UrlDetector.detectUrl("http://userstream.twitter.com/"));
        System.out.println(UrlDetector.detectUrl("https://userstream.twitter.com/"));
        System.out.println(UrlDetector.detectUrl("https://userstream.twitter.com/2/user.json"));
            
        /*
        System.out.println(UrlDetector.detectUrl("http://100.4.31.125:8081/amsproxy/login.action"));
        System.out.println(UrlDetector.detectUrl("https://100.4.31.125:8444/amsproxy/login.action"));
        System.out.println(UrlDetector.detectUrl("https://100.4.31.124:8444/amsproxy2/login.action"));
        System.out.println(UrlDetector.detectUrl("https://ratestserver:8444/amsproxy/login.action"));
        */
    }

}

/*
https and http both use detectUrlPlain:
15:40:16,156 [main] DEBUG [UrlDetector.detectUrl(40)]  - >>>detectUrl(http://mail.qq.com/)
15:40:16,296 [main] DEBUG [UrlDetector.detectUrlPlain(76)]  - conn.getResponseCode()=200
15:40:16,296 [main] DEBUG [UrlDetector.detectUrl(57)]  - <<<detectUrl(http://mail.qq.com/) - return value=true
true
15:40:16,296 [main] DEBUG [UrlDetector.detectUrl(40)]  - >>>detectUrl(https://mail.qq.com/)
15:40:17,031 [main] DEBUG [UrlDetector.detectUrlPlain(76)]  - conn.getResponseCode()=200
15:40:17,031 [main] DEBUG [UrlDetector.detectUrl(57)]  - <<<detectUrl(https://mail.qq.com/) - return value=true
true
15:40:17,031 [main] DEBUG [UrlDetector.detectUrl(40)]  - >>>detectUrl(http://login.taobao.com/member/login.jhtml)
15:40:17,156 [main] DEBUG [UrlDetector.detectUrlPlain(76)]  - conn.getResponseCode()=200
15:40:17,156 [main] DEBUG [UrlDetector.detectUrl(57)]  - <<<detectUrl(http://login.taobao.com/member/login.jhtml) - return value=true
true
15:40:17,156 [main] DEBUG [UrlDetector.detectUrl(40)]  - >>>detectUrl(https://login.taobao.com/member/login.jhtml)
15:40:17,343 [main] DEBUG [UrlDetector.detectUrlPlain(76)]  - conn.getResponseCode()=200
15:40:17,343 [main] DEBUG [UrlDetector.detectUrl(57)]  - <<<detectUrl(https://login.taobao.com/member/login.jhtml) - return value=true
true
15:40:17,343 [main] DEBUG [UrlDetector.detectUrl(40)]  - >>>detectUrl(http://www.google.com/)
15:40:18,062 [main] DEBUG [UrlDetector.detectUrlPlain(76)]  - conn.getResponseCode()=200
15:40:18,062 [main] DEBUG [UrlDetector.detectUrl(57)]  - <<<detectUrl(http://www.google.com/) - return value=true
true
15:40:18,078 [main] DEBUG [UrlDetector.detectUrl(40)]  - >>>detectUrl(https://www.google.com/)
15:40:18,093 [main] ERROR [UrlDetector.detectUrlPlain(81)]  - javax.net.ssl.SSLHandshakeException: sun.security.validator.ValidatorException: PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target
15:40:18,093 [main] DEBUG [UrlDetector.detectUrl(57)]  - <<<detectUrl(https://www.google.com/) - return value=false
false
15:40:18,093 [main] DEBUG [UrlDetector.detectUrl(40)]  - >>>detectUrl(http://userstream.twitter.com/)
15:43:18,140 [main] DEBUG [UrlDetector.detectUrlPlain(76)]  - conn.getResponseCode()=504
15:43:18,140 [main] DEBUG [UrlDetector.detectUrl(57)]  - <<<detectUrl(http://userstream.twitter.com/) - return value=false
false
15:43:18,140 [main] DEBUG [UrlDetector.detectUrl(40)]  - >>>detectUrl(https://userstream.twitter.com/)
15:43:18,156 [main] ERROR [UrlDetector.detectUrlPlain(81)]  - javax.net.ssl.SSLHandshakeException: sun.security.validator.ValidatorException: PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target
15:43:18,156 [main] DEBUG [UrlDetector.detectUrl(57)]  - <<<detectUrl(https://userstream.twitter.com/) - return value=false
false
15:43:18,156 [main] DEBUG [UrlDetector.detectUrl(40)]  - >>>detectUrl(https://userstream.twitter.com/2/user.json)
15:43:18,171 [main] ERROR [UrlDetector.detectUrlPlain(81)]  - javax.net.ssl.SSLHandshakeException: sun.security.validator.ValidatorException: PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target
15:43:18,171 [main] DEBUG [UrlDetector.detectUrl(57)]  - <<<detectUrl(https://userstream.twitter.com/2/user.json) - return value=false
false



https use detectUrlSSL and http use detectUrlPlain:
15:33:06,312 [main] DEBUG [UrlDetector.detectUrl(40)]  - >>>detectUrl(http://mail.qq.com/)
15:33:06,406 [main] DEBUG [UrlDetector.detectUrlPlain(73)]  - conn.getResponseCode()=200
15:33:06,406 [main] DEBUG [UrlDetector.detectUrl(54)]  - <<<detectUrl(http://mail.qq.com/) - return value=true
true
15:33:06,406 [main] DEBUG [UrlDetector.detectUrl(40)]  - >>>detectUrl(https://mail.qq.com/)
15:33:07,187 [main] DEBUG [UrlDetector.detectUrlSSL(152)]  - conn.getResponseCode()=200
15:33:07,187 [main] DEBUG [UrlDetector.detectUrl(54)]  - <<<detectUrl(https://mail.qq.com/) - return value=true
true
15:33:07,187 [main] DEBUG [UrlDetector.detectUrl(40)]  - >>>detectUrl(http://login.taobao.com/member/login.jhtml)
15:33:07,281 [main] DEBUG [UrlDetector.detectUrlPlain(73)]  - conn.getResponseCode()=200
15:33:07,296 [main] DEBUG [UrlDetector.detectUrl(54)]  - <<<detectUrl(http://login.taobao.com/member/login.jhtml) - return value=true
true
15:33:07,296 [main] DEBUG [UrlDetector.detectUrl(40)]  - >>>detectUrl(https://login.taobao.com/member/login.jhtml)
15:33:07,453 [main] DEBUG [UrlDetector.detectUrlSSL(152)]  - conn.getResponseCode()=200
15:33:07,453 [main] DEBUG [UrlDetector.detectUrl(54)]  - <<<detectUrl(https://login.taobao.com/member/login.jhtml) - return value=true
true
15:33:07,453 [main] DEBUG [UrlDetector.detectUrl(40)]  - >>>detectUrl(http://www.google.com/)
15:33:57,421 [main] DEBUG [UrlDetector.detectUrlPlain(73)]  - conn.getResponseCode()=200
15:33:57,421 [main] DEBUG [UrlDetector.detectUrl(54)]  - <<<detectUrl(http://www.google.com/) - return value=true
true
15:33:57,421 [main] DEBUG [UrlDetector.detectUrl(40)]  - >>>detectUrl(https://www.google.com/)
15:33:57,671 [main] DEBUG [UrlDetector.detectUrlSSL(152)]  - conn.getResponseCode()=302
15:33:57,671 [main] DEBUG [UrlDetector.detectUrl(54)]  - <<<detectUrl(https://www.google.com/) - return value=false
false
15:33:57,671 [main] DEBUG [UrlDetector.detectUrl(40)]  - >>>detectUrl(http://userstream.twitter.com/)
15:36:58,218 [main] DEBUG [UrlDetector.detectUrlPlain(73)]  - conn.getResponseCode()=504
15:36:58,218 [main] DEBUG [UrlDetector.detectUrl(54)]  - <<<detectUrl(http://userstream.twitter.com/) - return value=false
false
15:36:58,218 [main] DEBUG [UrlDetector.detectUrl(40)]  - >>>detectUrl(https://userstream.twitter.com/)
15:37:11,953 [main] DEBUG [UrlDetector.detectUrlSSL(152)]  - conn.getResponseCode()=401
15:37:11,953 [main] DEBUG [UrlDetector.detectUrl(54)]  - <<<detectUrl(https://userstream.twitter.com/) - return value=false
false
15:37:11,953 [main] DEBUG [UrlDetector.detectUrl(40)]  - >>>detectUrl(https://userstream.twitter.com/2/user.json)
15:37:12,171 [main] DEBUG [UrlDetector.detectUrlSSL(152)]  - conn.getResponseCode()=401
15:37:12,171 [main] DEBUG [UrlDetector.detectUrl(54)]  - <<<detectUrl(https://userstream.twitter.com/2/user.json) - return value=false
false

*/


