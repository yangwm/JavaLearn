package cmd;

/**
 * 
 */


/**
 * @author yangwm in Jun 6, 2009 5:17:21 PM
 */
public class RunEcho {
    
    static class CpuUsage {
        public static void main(String[] args) {
            System.out.println("getCpuUsage()=" + getCpuUsage());
        }
    }
    
    static class MemUsage {
        public static void main(String[] args) {
            System.out.println("getMemUsage()=" + getMemUsage());
        }
    }

    
    static class Ipconfig {
        public static void main(String[] args) {
            System.out.println("getIpconfig()=" + getIpconfig());
        }
    }
    
    /**
     * 
     * @return
     */
    public static int getCpuUsage() {
        int result = 0;
        try {
            String command = "vmstat";
            Process p = Runtime.getRuntime().exec(command);
            if (p != null) {
                String str = ReadEchoUtil.readEcho(p);
                String[] strs = str.split("\\s+");
                result = Integer.parseInt(strs[33]);
              
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * 
     * @return
     */
    public static int getMemUsage() {
        int result = 0;
        try {
            String command = "free";
            Process p = Runtime.getRuntime().exec(command);
            if (p != null) {
                String str = ReadEchoUtil.readEcho(p);
                String[] strs = str.split("\\s+");
                float temValue = Float.parseFloat(strs[9])/Float.parseFloat(strs[8]);
                result = (int)(temValue*100);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return result;
        
    }

    /**
     * 
     * @return
     */
    public static String getIpconfig() {
        String result = null;
        /*
         * top
         * df -k
         * ipconfig
         */
        try {
            String command = "ipconfig";
            Process p = Runtime.getRuntime().exec(command);
            if (p != null) {
                String str = ReadEchoUtil.readEcho(p);
                result = str;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return result;
    }
}
