/**
 * 
 */
package lang;

import org.apache.commons.lang.SystemUtils;

/**
 * @author yangwm in Sep 23, 2009 9:41:26 AM
 */
public class SystemUtilsUsage {

    /**
     * create by yangwm in Sep 23, 2009 9:41:26 AM
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(SystemUtils.FILE_ENCODING);
        System.out.println(SystemUtils.FILE_SEPARATOR);
        System.out.println(SystemUtils.JAVA_CLASS_PATH);
        System.out.println(SystemUtils.JAVA_CLASS_VERSION);
        System.out.println("-----------------------------");
        
        System.out.println(SystemUtils.JAVA_ENDORSED_DIRS);
        System.out.println(SystemUtils.JAVA_EXT_DIRS);
        System.out.println(SystemUtils.JAVA_HOME);
        System.out.println(SystemUtils.JAVA_IO_TMPDIR);
        System.out.println(SystemUtils.JAVA_LIBRARY_PATH);
        System.out.println("-----------------------------");
        
        System.out.println(SystemUtils.JAVA_RUNTIME_NAME);
        System.out.println(SystemUtils.JAVA_RUNTIME_VERSION);
        System.out.println(SystemUtils.JAVA_VENDOR);
        System.out.println(SystemUtils.JAVA_VERSION);
        System.out.println(SystemUtils.LINE_SEPARATOR);
        System.out.println("-----------------------------");
        
        System.out.println(SystemUtils.OS_ARCH);
        System.out.println(SystemUtils.OS_NAME);
        System.out.println(SystemUtils.OS_VERSION);
        System.out.println(SystemUtils.PATH_SEPARATOR);
        System.out.println(SystemUtils.USER_HOME);
        System.out.println(SystemUtils.USER_NAME);
        System.out.println("-----------------------------");
        
        System.out.println(System.getProperty("sun.os.patch.level"));
    }

}

/*
GBK
\
D:\study\tempProject\JavaLearn\classes;D:\study\tempProject\JavaLearn\lib\ldapbp.jar;D:\study\tempProject\JavaLearn\lib\log4j-1.2.15.jar;D:\study\tempProject\JavaLearn\lib\commons-lang-2.4.jar;D:\study\tempProject\JavaLearn\lib\commons-beanutils-1.8.0.jar;D:\study\tempProject\JavaLearn\lib\J7Zip.jar;D:\study\tempProject\JavaLearn\lib\commons-dbutils-1.1.jar;D:\study\tempProject\JavaLearn\lib\commons-digester-2.0.jar;D:\study\tempProject\JavaLearn\lib\quartz-all-1.6.5.jar;D:\study\tempProject\JavaLearn\lib\commons-collections-3.2.1.jar;D:\study\tempProject\JavaLearn\lib\commons-betwixt-0.8.jar;D:\study\tempProject\JavaLearn\lib\dom4j-1.6.1.jar;D:\study\tempProject\JavaLearn\lib\jaxen-1.1.1.jar;D:\study\tempProject\JavaLearn\lib\ojdbc5_g.jar;D:\study\tempProject\JavaLearn\lib\commons-logging-1.1.1.jar;D:\study\tempProject\JavaLearn\lib\xml-apis-2.0.2.jar;D:\study\tempProject\JavaLearn\lib\Log4j_ultrapower_1.0_bate.jar
50.0
-----------------------------
D:\Program Files\Java\jdk1.6.0_13\jre\lib\endorsed
D:\Program Files\Java\jdk1.6.0_13\jre\lib\ext;C:\WINDOWS\Sun\Java\lib\ext
D:\Program Files\Java\jdk1.6.0_13\jre
C:\DOCUME~1\yangwm\LOCALS~1\Temp\
D:\Program Files\Java\jdk1.6.0_13\bin;.;C:\WINDOWS\Sun\Java\bin;C:\WINDOWS\system32;C:\WINDOWS;.;D:\Program Files\JavaFX\javafx-sdk1.1\bin;D:\Program Files\JavaFX\javafx-sdk1.1\emulator\bin;C:\Program Files\PC Connectivity Solution\;C:\Program Files\Common Files\NetSarang;.;C:\WINDOWS\system32;C:\WINDOWS;C:\WINDOWS\System32\Wbem;D:\Program Files\Java\jdk1.5.0_16\bin;D:\working\apache-tomcat-5.5.20\bin;D:\oracle\product\10.2.0\db_1\bin;D:\Program Files\Rational\common;d:\Program Files\WinSCP\;D:\Program Files\Symantec\pcAnywhere\;D:\Program Files\TortoiseSVN\bin;D:\Program Files\MySQL\mysql-5.4.1-beta-win32\bin;D:\working\Dev-Cpp\bin;C:\Program Files\Microsoft SQL Server\80\Tools\Binn\;C:\Program Files\Microsoft SQL Server\90\Tools\binn\;C:\Program Files\Microsoft SQL Server\90\DTS\Binn\;C:\Program Files\Microsoft SQL Server\90\Tools\Binn\VSShell\Common7\IDE\;D:\Program Files\Subversion\bin;D:\Program Files\apache-ant-1.7.1\bin;D:\Program Files\apache-maven-2.1.0\bin;D:\Program Files\Microsoft Visual Studio\Common\Tools\WinNT;D:\Program Files\Microsoft Visual Studio\Common\MSDev98\Bin;D:\Program Files\Microsoft Visual Studio\Common\Tools;D:\Program Files\Microsoft Visual Studio\VC98\bin;d:\Program Files\StormII\Codec;d:\Program Files\StormII
-----------------------------
Java(TM) SE Runtime Environment
1.6.0_13-b03
Sun Microsystems Inc.
1.6.0_13


-----------------------------
x86
Windows XP
5.1
;
C:\Documents and Settings\yangwm
yangwm
-----------------------------
Service Pack 3
*/
