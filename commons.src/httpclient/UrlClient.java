/**
 * 
 */
package httpclient;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;

import javax.net.ssl.SSLPeerUnverifiedException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;

/**
 * url detector 
 * 
 * @author yangwm in Dec 11, 2009 11:42:10 AM
 */
public class UrlClient {
    /**
     * Logger for this class
     */
    private static final Log logger = LogFactory.getLog(UrlClient.class);

    /**
     * detector url
     * 
     * @param url
     * @return
     */
    public static boolean detectUrl(String url) {
        logger.debug(">>>detectUrl(" + url + ")");

        boolean result = false;
        
        if (url == null || "".equals(url)) {
            return result;
        }
        
        if (url.trim().startsWith("https")) {
            result = detectUrl(url, "d:/working/serverNew.jks", "12345678");
        } else {
            result = detectUrl(url, null, null);
        }
        
        logger.debug("<<<detectUrl(" + url + ") - return value=" + result);
        return result;
    }
    
    /**
     * detector url
     * 
     * @param url
     * @param jksPath
     * @return
     */
    public static boolean detectUrl(String url, String jksPath, String jksPassword) {
        //logger.debug(">>>detectUrl(" + url + ", " + jksPath + ", " + jksPassword + ")");

        boolean result = false;

        HttpClient httpclient = new DefaultHttpClient();
        try {
            if (null != jksPath && !"".equals(jksPath) && null != jksPassword) {
                KeyStore trustStore  = KeyStore.getInstance(KeyStore.getDefaultType());
                FileInputStream instream = null;
                try {
                    instream = new FileInputStream(new File(jksPath));
                    trustStore.load(instream, jksPassword.toCharArray());
                } finally {
                    if (instream != null) {
                        instream.close();
                    }
                }
                
                SSLSocketFactory socketFactory = new SSLSocketFactory(trustStore);
                socketFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER); // otherwise url's hostname must be 'ratestserver' 
                Scheme sch = new Scheme("https", socketFactory, 443);
                
                httpclient.getConnectionManager().getSchemeRegistry().register(sch);
            }

            httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3 * 1000);
            HttpGet httpget = new HttpGet(url);
            HttpResponse response = httpclient.execute(httpget);
            
            /*
            // make sure to use a proxy that supports CONNECT
            HttpHost proxy = new HttpHost("127.0.0.1", 8080, "http");
            httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
            */
            
            logger.debug("response.getStatusLine()=" + response.getStatusLine());
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = true;
            }
        } catch(SSLPeerUnverifiedException e) {
            result = true;
            logger.error(null, e);
        }  catch (Exception e) {
            logger.error(null, e);
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
        
        //logger.debug("<<<detectUrl(" + url + ", " + jksPath + ", " + jksPassword + ") - return value=" + result);
        return result;
    }

    /**
     * create by yangwm in Dec 11, 2009 11:42:10 AM
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        /*
        System.out.println(detectUrl("http://mail.qq.com/"));
        System.out.println(detectUrl("https://mail.qq.com/"));
        System.out.println(detectUrl("http://100.4.31.125:8081/amsproxy/login.action"));
        System.out.println(detectUrl("https://100.4.31.125:8444/amsproxy/login.action"));
        System.out.println(detectUrl("https://100.4.31.124:8444/amsproxy2/login.action"));
        System.out.println(detectUrl("https://ratestserver:8444/amsproxy/login.action"));
        */
        
        
        System.out.println(detectUrl("http://100.4.31.125:8081/amsproxy/login.action"));
        System.out.println(detectUrl("http://100.10.0.150:7001/ams_bj2/"));
        System.out.println(detectUrl("http://100.9.0.40:7004/ams/"));
        
        System.out.println(detectUrl("https://100.4.31.125:8444/amsproxy/login.action"));
        System.out.println(detectUrl("https://100.10.0.150:7002/ams_bj/"));
        System.out.println(detectUrl("https://100.10.0.150:7002/ams_bj2/"));
        System.out.println(detectUrl("https://100.10.0.150:7003/ams_bj/"));
        System.out.println(detectUrl("https://100.9.0.40:7002/ams/"));
        System.out.println(detectUrl("https://100.9.0.40:7002/ams_yanwm/"));
        System.out.println(detectUrl("https://100.9.0.40:7003/ams/"));
        /**/
    }

}
