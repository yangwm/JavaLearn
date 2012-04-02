/**
 * 
 */
package cmd;


/**
 * 命令执行器测试 
 * 
 * @author yangwm in Jan 20, 2010 4:24:44 PM
 */
public class CommandRunnerTest {
    
    static class CaseScpGet{
        public static void main(String[] args) {
            CommandRunner.scpGet("100.10.0.150", "root", "amsadmin", "/scpTest.txt", "d:/");
        }
    }

    static class CaseScpPut{
        public static void main(String[] args) {
            CommandRunner.scpPut("100.10.0.150", "root", "amsadmin", "d:/scpTest.txt", "/");
        }
    }

    static class CaseRunSsh{
        public static void main(String[] args) {
            //System.out.println(CommandRunner.runSSH("100.10.0.150", "root", "amsadmin", "cat /etc/passwd"));
            System.out.println(CommandRunner.runSsh("100.10.0.150", "root", "amsadmin", "ifconfig"));
        }
    }

    static class CaseRunLocal{
        public static void main(String[] args) {
            System.out.println(CommandRunner.runLocal("ipconfig"));
        }
    }
    
    static class CaseRunTelnet{
        public static void main(String[] args) {
            System.out.println(CommandRunner.runTelnet("127.0.0.1", "yangwm", "yangwm", "ipconfig"));
        }
    }

}
