/**
 * 
 */
package lang;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author yangwm in Jun 8, 2009 5:13:39 PM
 */
public class EscapeTest {

    /**
     * create by yangwm in Jun 8, 2009 5:13:39 PM
     * @param args
     */
    public static void main(String[] args) {
        String str = "Filesystem           1K-blocks      Used Available Use% Mounted on\n";
        str += "/dev/sda1              7850996   1511468   5940716  21% /\n";
        str += "tmpfs                   258432         0    258432   0% /lib/init/rw\n";
        str += "udev                     10240        52     10188   1% /dev\n";
        str += "tmpfs                   258432         0    258432   0% /dev/shm\n";
        
        System.out.println(str);
        
        System.out.println("StringEscapeUtils.unescapeHtml(str):");
        System.out.println(StringEscapeUtils.unescapeHtml(str));
        
        System.out.println("StringEscapeUtils.escapeHtml(str):");
        System.out.println(StringEscapeUtils.escapeHtml(str));
        
        System.out.println("StringEscapeUtils.unescapeJava(str):");
        System.out.println(StringEscapeUtils.unescapeJava(str));
        
        System.out.println("StringEscapeUtils.escapeJava(str):");
        System.out.println(StringEscapeUtils.escapeJava(str));
        
        System.out.println("StringUtils.replace(str, \"\n\", \"<br>\"):");
        System.out.println(StringUtils.replace(str, "\n", "<br>"));
        
        System.out.println("StringUtils.replace(str, \" \", \"&nbsp;\"):");
        System.out.println(StringUtils.replace(str, " ", "&nbsp;"));
    }

}
