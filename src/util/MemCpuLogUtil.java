package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.DecimalFormat;


public class MemCpuLogUtil {

    public static String getMemCpuInfo() {
        return memoryReport() + " ,CPUTotal used percent " + LinuxCpuCounter.cpuCount();
    }
    
    public static String memoryReport() {
        Runtime runtime = Runtime.getRuntime();

        double freeMemory = (double) runtime.freeMemory() / (1024 * 1024);
        double maxMemory = (double) runtime.maxMemory() / (1024 * 1024);
        double totalMemory = (double) runtime.totalMemory() / (1024 * 1024);
        double usedMemory = totalMemory - freeMemory;
        double percentFree = ((maxMemory - usedMemory) / maxMemory) * 100.0;
        double percentUsed = 100 - percentFree;
        // int percent = 100 - (int) Math.round(percentFree);

        DecimalFormat mbFormat = new DecimalFormat("#0.00");
        DecimalFormat percentFormat = new DecimalFormat("#0.0");
        
        StringBuilder sb = new StringBuilder(" ");
        sb.append(mbFormat.format(usedMemory)).append("MB of ").append(mbFormat.format(maxMemory))
        .append(" MB (").append(percentFormat.format(percentUsed)).append("%) used");
        return sb.toString();
    }

    static class LinuxCpuCounter {
        long user;
        long nice;
        long system;
        long idle;
        long iowait;
        long irq;
        long softirq;
        double tcpu;
        double scpu;

        public static double cpuCount() {
            LinuxCpuCounter count1 = getCpuInfo();
            try {
                Thread.sleep(2000L);
            } catch (Exception e) {
            }
            LinuxCpuCounter count2 = getCpuInfo();
            if (count1 == null || count2 == null || count2.scpu - count1.scpu == 0.0D) {
                return 0.0D;
            } else {
                double cpuper = ((count2.tcpu - count1.tcpu) / (count2.scpu - count1.scpu)) * 100D;
                DecimalFormat mbFormat = new DecimalFormat("#0.00");
                cpuper = Double.parseDouble(mbFormat.format(cpuper));
                return cpuper;
            }
        }

        public static LinuxCpuCounter getCpuInfo() {
            BufferedReader in = null;
            LinuxCpuCounter linuxcpucounter;
            try {
                in = new BufferedReader(new FileReader("/proc/stat"));

                String str[];
                do {
                    String s1 = (in.readLine());
                    do {
                        if (s1 == null) {
                            break;
                        }
                    } while (!s1.startsWith("cpu"));
                    str = s1.split(" ");
                } while (str.length <= 8);
                LinuxCpuCounter c = new LinuxCpuCounter();
                c.user = Long.parseLong(str[2].trim());
                c.nice = Long.parseLong(str[3].trim());
                c.system = Long.parseLong(str[4].trim());
                c.idle = Long.parseLong(str[5].trim());
                c.iowait = Long.parseLong(str[6].trim());
                c.irq = Long.parseLong(str[7].trim());
                c.softirq = Long.parseLong(str[8].trim());
                c.scpu = (double) c.user + (double) c.nice + (double) c.system + (double) c.idle + (double) c.iowait
                        + (double) c.irq + (double) c.softirq;
                c.tcpu = (double) c.user + (double) c.nice + (double) c.system;
                linuxcpucounter = c;
                in.close();
                return linuxcpucounter;
            } catch (Exception e) {
            } finally {
                try {
                    in.close();
                } catch (Exception e) { // Misplaced declaration of an exception
                                        // variable
                }

                try {
                    in.close();
                } catch (Exception e) { // Misplaced declaration of an exception
                                        // variable
                }

                try {
                    in.close();
                } catch (Exception e) {
                }
            }
            return null;
        }
    }
    
    public static void main(String[] args) {
        System.out.println(MemCpuLogUtil.getMemCpuInfo());
    }

}