package basics.essential.environments;

import java.util.Map;

public class EnvMap {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Map<String, String> env = System.getenv();
        for (String envName : env.keySet()) {
            System.out.format("%s=%s%n", envName, env.get(envName));
        }
    }

}
/*
USERPROFILE=C:\Documents and Settings\yangwm
PATHEXT=.COM;.EXE;.BAT;.CMD;.VBS;.VBE;.JS;.JSE;.WSF;.WSH
JAVA_HOME=D:\Program Files\Java\jdk1.6.0_01
TEMP=C:\DOCUME~1\yangwm\LOCALS~1\Temp
SystemDrive=C:
ProgramFiles=C:\Program Files
Path=D:\work\orant\bin;.;D:\Program Files\Java\jdk1.6.0_01\bin;D:\Program Files\Tool_Ant\bin;D:\work\JRun4\bin;C:\WINDOWS\system32;C:\WINDOWS;C:\WINDOWS\System32\Wbem;D:\Program Files\Borland\Delphi7\Bin;D:\Program Files\Borland\Delphi7\Projects\Bpl\
HOMEDRIVE=C:
PROCESSOR_REVISION=0f02
Rav=C:\Documents and Settings\All Users\Application Data\Rising\Rav
CLIENTNAME=Console
USERDOMAIN=PCCW-581628B3A4
ALLUSERSPROFILE=C:\Documents and Settings\All Users
PROCESSOR_IDENTIFIER=x86 Family 6 Model 15 Stepping 2, GenuineIntel
SESSIONNAME=Console
TMP=C:\DOCUME~1\yangwm\LOCALS~1\Temp
LOGONSERVER=\\PCCW-581628B3A4
CommonProgramFiles=C:\Program Files\Common Files
PROCESSOR_ARCHITECTURE=x86
FP_NO_HOST_CHECK=NO
OS=Windows_NT
HOMEPATH=\Documents and Settings\yangwm
PROCESSOR_LEVEL=6
classpath=.;D:\Program Files\Java\jdk1.6.0_01\lib
COMPUTERNAME=PCCW-581628B3A4
windir=C:\WINDOWS
SystemRoot=C:\WINDOWS
NUMBER_OF_PROCESSORS=2
USERNAME=yangwm
ComSpec=C:\WINDOWS\system32\cmd.exe
APPDATA=C:\Documents and Settings\yangwm\Application Data
*///~
