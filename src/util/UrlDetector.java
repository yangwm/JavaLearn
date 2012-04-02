/**
 * 
 */
package util;

import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * url detector 
 * 
 * @author yangwm in Dec 11, 2009 3:17:33 PM
 */
public class UrlDetector {
    /**
     * Logger for this class
     */
    private static final Log logger = LogFactory.getLog(UrlDetector.class);
    
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
        
        /*
         * https and http both use detectUrlPlain
         */
        result = detectUrlPlain(url);
        
        /*
         * https use detectUrlSSL and http use detectUrlPlain
        
        if (url.trim().startsWith("https")) {
            result = detectUrlSSL(url);
        } else {
            result = detectUrlPlain(url);
        }
         */
        
        logger.debug("<<<detectUrl(" + url + ") - return value=" + result);
        return result;
    }
    
    /**
     * detect http url
     * 
     * @param url
     * @return
     */
    public static boolean detectUrlPlain(String url) {
        boolean result = false;
        HttpURLConnection conn = null;
        try {
            URL urlObj = new URL(url);
            conn = (HttpURLConnection) urlObj.openConnection();
            conn.setConnectTimeout(3 * 1000);
            conn.connect();
            
            logger.debug("conn.getResponseCode()=" + conn.getResponseCode());
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                result = true;
            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        
        return result;
    }
    
    /**
     * detect https url
     * 
     * @param url
     * @return
     */
    public static boolean detectUrlSSL(String url) {
        boolean result = false;
        
        HttpsURLConnection conn = null;
        try {
            URL urlObj = new URL(url);
            
            TrustManager trustAnyTrustManager = new X509TrustManager() {
                public void checkClientTrusted(
                        X509Certificate[] chain,
                        String authType) throws CertificateException {
                    // Oh, I am easy!
                }
                public void checkServerTrusted(
                        X509Certificate[] chain,
                        String authType) throws CertificateException {
                    // Oh, I am easy!
                }
                public X509Certificate[] getAcceptedIssuers() {
                    X509Certificate cerCert = null;
                    /*
                    InputStream is = null;
                    try {
                        is = new FileInputStream("d:/working/RaSuperAdmin.cer");
                        cerCert = (X509Certificate) CertificateFactory.
                            getInstance("x509").generateCertificate(is);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (is != null) {
                            try {
                                is.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    */
                    
                    return new X509Certificate[] {cerCert};
                }
            };
            HostnameVerifier trustAnyHostnameVerifier = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };

            
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[] { trustAnyTrustManager }, null);

            conn = (HttpsURLConnection) urlObj.openConnection();
            conn.setConnectTimeout(3 * 1000);
            conn.setSSLSocketFactory(sc.getSocketFactory());
            conn.setHostnameVerifier(trustAnyHostnameVerifier);
            conn.connect();
            
            logger.debug("conn.getResponseCode()=" + conn.getResponseCode());
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                result = true;
            }
        } catch (SSLHandshakeException e) {
            logger.error(e);
        } catch (Exception e) {
            logger.error(e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        
        return result;
    }

}

