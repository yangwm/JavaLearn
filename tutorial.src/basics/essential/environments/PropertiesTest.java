package basics.essential.environments;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesTest {

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        // set up new properties object
        // from file "myProperties.txt"
        FileInputStream propFile = new FileInputStream(
                                           "myProperties.txt");
        Properties p = new Properties(System.getProperties());
        p.load(propFile);

        // set the system properties
        System.setProperties(p);
        // display new properties
        System.getProperties().list(System.out);
    }

}
/*
-- listing properties --
my.love=undefine
java.runtime.name=Java(TM) SE Runtime Environment
sun.boot.library.path=D:\Program Files\Java\jre1.6.0_01\bin
java.vm.version=1.6.0_01-b06
java.vm.vendor=Sun Microsystems Inc.
java.vendor.url=http://java.sun.com/
path.separator=;
java.vm.name=Java HotSpot(TM) Client VM
file.encoding.pkg=sun.io
user.country=CN
sun.java.launcher=SUN_STANDARD
sun.os.patch.level=Service Pack 2
java.vm.specification.name=Java Virtual Machine Specification
user.dir=D:\yworkspace\JavaLearn
java.runtime.version=1.6.0_01-b06
java.awt.graphicsenv=sun.awt.Win32GraphicsEnvironment
java.endorsed.dirs=D:\Program Files\Java\jre1.6.0_01\lib...
os.arch=x86
java.io.tmpdir=C:\DOCUME~1\yangwm\LOCALS~1\Temp\
line.separator=

java.vm.specification.vendor=Sun Microsystems Inc.
user.variant=
os.name=Windows XP
sun.jnu.encoding=GB18030
subliminal.message=Buy StayPuft Marshmallows!
java.library.path=D:\Program Files\Java\jre1.6.0_01\bin...
java.specification.name=Java Platform API Specification
java.class.version=50.0
sun.management.compiler=HotSpot Client Compiler
os.version=5.1
user.home=C:\Documents and Settings\yangwm
user.timezone=
java.awt.printerjob=sun.awt.windows.WPrinterJob
file.encoding=GB18030
java.specification.version=1.6
user.name=yangwm
java.class.path=D:\yworkspace\JavaLearn\classes;D:\yw...
java.vm.specification.version=1.0
sun.arch.data.model=32
java.home=D:\Program Files\Java\jre1.6.0_01
java.specification.vendor=Sun Microsystems Inc.
user.language=zh
awt.toolkit=sun.awt.windows.WToolkit
java.vm.info=mixed mode, sharing
java.version=1.6.0_01
java.ext.dirs=D:\Program Files\Java\jre1.6.0_01\lib...
sun.boot.class.path=D:\Program Files\Java\jre1.6.0_01\lib...
java.vendor=Sun Microsystems Inc.
file.separator=\
java.vendor.url.bug=http://java.sun.com/cgi-bin/bugreport...
sun.cpu.endian=little
sun.io.unicode.encoding=UnicodeLittle
sun.desktop=windows
sun.cpu.isalist=pentium_pro+mmx pentium_pro pentium+m...
*///~
