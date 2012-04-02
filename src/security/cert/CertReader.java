/**
 * 
 */
package security.cert;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

/**
 * @author yangwm in Dec 11, 2009 5:56:44 PM
 */
public class CertReader {

    /**
     * create by yangwm in Dec 11, 2009 5:56:44 PM
     * @param args
     */
    public static void main(String[] args) throws Exception {
        // Open an input stream on the keystore file 
        
        // public key 
        String cerFileName = "d:/working/RaSuperAdmin.cer";//"d:/certA.cer";
        InputStream is = new FileInputStream(cerFileName);
        CertificateFactory cf = CertificateFactory.getInstance("x509");
        Certificate cerCert = cf.generateCertificate(is);
        System.out.println("public key:\n" + cerCert);
        
        /**/
        String p12FileName = "d:/working/RaSuperAdmin.pfx";//"d:/certA.p12";
        String pfxPassword = "123456";
        InputStream fis = new FileInputStream(p12FileName);
   
        // Create a keystore object 
        //KeyStore keyStore = KeyStore.getInstance("PKCS12", "BC");
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        // Load the file into the keystore 
        keyStore.load(fis, pfxPassword.toCharArray());
   
        String aliaesName = "abcd";
        PrivateKey priKey = (PrivateKey) (keyStore.getKey(aliaesName, null));
        System.out.println("private key:\n" + priKey);
    }

}
