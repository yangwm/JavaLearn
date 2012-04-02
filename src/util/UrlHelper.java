/**
 * 
 */
package util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.KeyStore;

import javax.net.ssl.SSLPeerUnverifiedException;

import org.apache.commons.lang.StringUtils;
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
 * UrlHelper
 * 
 * @author yangwm in Aug 16, 2009 4:58:22 PM
 */
public class UrlHelper {
    /**
     * Logger for this class
     */
    private static final Log logger = LogFactory.getLog(UrlHelper.class);
    
    private static final String defaultEncode = "UTF-8";

    public static String encode(String str) {
        try {
            return URLEncoder.encode(str, defaultEncode);
        } catch (UnsupportedEncodingException e) {
            logger.warn("Could not encode URL parameter '" + str + "', returning value un-encoded");
            return str;
        }
    }
    

    public static String decode(String str) {
        try {
            return URLDecoder.decode(str, defaultEncode);
        } catch (UnsupportedEncodingException e) {
            logger.warn("Could not encode URL parameter '" + str + "', returning value un-encoded");
            return str;
        }
    }
    
    
    /**
     * detector url
     * 
     * @param url
     * @param jksPath
     * @return
     */
    public static boolean detectUrl(String url, String jksPath, String jksPassword) {
        logger.debug(">>>detectUrl(" + url + ", " + jksPath + ", " + jksPassword + ")");

        boolean result = false;

        HttpClient httpclient = new DefaultHttpClient();
        try {
            if (StringUtils.isNotEmpty(jksPath) && jksPassword != null) {
                KeyStore trustStore = ConfigUtil.getConfigKeyStore(jksPath, jksPassword);
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
            //result = true;
            logger.error(null, e);
        } catch (Exception e) {
            logger.error(null, e);
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
        
        logger.debug("<<<detectUrl(" + url + ", " + jksPath + ", " + jksPassword + ") - return value=" + result);
        return result;
    }

}

