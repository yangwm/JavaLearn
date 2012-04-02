/**
 * 
 */
package security.jsse;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManagerFactory;

/**
 * @author yangwm in Dec 13, 2009 11:41:22 PM
 */
public class SSLEngineSample {

    /**
     * create by yangwm in Dec 13, 2009 11:41:22 PM
     * @param args
     * @throws KeyStoreException 
     * @throws IOException 
     * @throws FileNotFoundException 
     * @throws CertificateException 
     * @throws NoSuchAlgorithmException 
     * @throws UnrecoverableKeyException 
     * @throws KeyManagementException 
     */
    public static void main(String[] args) 
            throws KeyStoreException, NoSuchAlgorithmException, 
            CertificateException, FileNotFoundException, IOException, 
            UnrecoverableKeyException, KeyManagementException {
        // Create/initialize the SSLContext with key material

        char[] passphrase = "passphrase".toCharArray();

        // First initialize the key and trust material.
        KeyStore ksKeys = KeyStore.getInstance("JKS");
        ksKeys.load(new FileInputStream("testKeys"), passphrase);
        KeyStore ksTrust = KeyStore.getInstance("JKS");
        ksTrust.load(new FileInputStream("testTrust"), passphrase);

        // KeyManager's decide which key material to use.
        KeyManagerFactory kmf =
            KeyManagerFactory.getInstance("SunX509");
        kmf.init(ksKeys, passphrase);

        // TrustManager's decide whether to allow connections.
        TrustManagerFactory tmf =
            TrustManagerFactory.getInstance("SunX509");
        tmf.init(ksTrust);

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(
            kmf.getKeyManagers(), tmf.getTrustManagers(), null);

        String hostname = "";
        int port = 0;
        // We're ready for the engine.
        SSLEngine engine = sslContext.createSSLEngine(hostname, port);

        // Use as client
        engine.setUseClientMode(true);

    }

}
