/**
 * 
 */
package extra.i18n.network;

import java.net.IDN;

/**
 * Internationalized Domain Name
 * 
 * @author yangwm Jun 2, 2010 6:02:21 PM
 */
public class IdnTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        String ace_name = IDN.toASCII("http://www.tsinghua.edu.cn/");
        System.out.println(ace_name);
        
        ace_name = IDN.toASCII("http://清华大学.cn/");
        System.out.println(ace_name);
        
        ace_name = IDN.toASCII("http://网易.com/");
        System.out.println(ace_name);
        
        String ace_name2 = IDN.toUnicode("http://清华大学.cn/");
        System.out.println(ace_name2);
    }

}

/*
http://www.tsinghua.edu.cn/
xn--http://-qp2lt84bkofex3d.cn/
xn--http://-023on13p.com/
http://清华大学.cn/

*/
