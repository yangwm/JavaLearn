/**
 * 
 */
package extra.i18n.network;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Internationalized Resource Identifier
 * 
 * @author yangwm Jun 2, 2010 6:06:04 PM
 */
public class IriTest {

    /**
     * @param args
     * @throws URISyntaxException 
     * @throws IOException 
     * @throws MalformedURLException 
     */
    public static void main(String[] args) throws URISyntaxException, MalformedURLException, IOException {
        URI uri = new URI("http://清华大学.cn/");
        HttpURLConnection conn = (HttpURLConnection) uri.toURL().openConnection();
        int responseCode = conn.getResponseCode();
        System.out.println("http://清华大学.cn/ 's responseCode is " + responseCode);
    }

}

/*
http://清华大学.cn/ 's responseCode is 200

*/
