/**
 * 
 */
package net;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 生成测试用的syslog。
 * 
 * @author yangwm in Mar 17, 2010 4:08:40 PM
 */
public class SyslogSender implements Runnable {

    private String encoding = "GBK";
    private int speed = -1;
    private int time = 30;
    private String dest;
    private int port = 514;
    private String file;

    private long sendCount;
    private long sendBytesCount;
    private DatagramSocket socket;
    private byte[] buf;
    private DatagramPacket p;
    private boolean needStop = false;
    private BufferedReader reader;
    private ScheduledExecutorService ses = Executors
            .newSingleThreadScheduledExecutor();

    public static void main(String[] args) throws Exception {
        SyslogSender s = new SyslogSender();
        if (args == null || args.length == 0) {
            printUsage();
            System.exit(1);
        }
        try {
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("-d")) {
                    s.dest = args[++i];
                }
                if (args[i].equals("-s")) {
                    s.speed = Integer.parseInt(args[++i]);
                }
                if (args[i].equals("-t")) {
                    s.time = Integer.parseInt(args[++i]);
                }
                if (args[i].equals("-p")) {
                    s.port = Integer.parseInt(args[++i]);
                }
                if (args[i].equals("-f")) {
                    s.file = args[++i];
                }
                if (args[i].equals("-e")) {
                    s.encoding = args[++i];
                }
            }
            if (s.dest == null || s.speed == -1) {
                printUsage();
                System.exit(1);
            }
        } catch (RuntimeException e) {
            printUsage();
            System.exit(1);
        }
        s.go();
    }

    private static void printUsage() {
        System.out.println("Usage:");
        System.out.println("java " + SyslogSender.class.getName()
                + " -d destination -s speed [options]");
        System.out.println();
        System.out.println("Options:");
        System.out.println("-d (dest) dest syslog server ip or hostname");
        System.out.println("-s (speed) items per seconds.");
        System.out.println("-t (time) time in seconds. default 30");
        System.out.println("-p (port) dest syslog server port. default 514");
        System.out.println("-f (file) msg src file");
        System.out.println("-e encoding. default GBK");
    }

    private void createReader() throws IOException {
        reader = new BufferedReader(new InputStreamReader(new FileInputStream(
                file), encoding), 1024 * 1024);
    }

    private void go() throws Exception {

        socket = new DatagramSocket();
        socket.setSendBufferSize(64 * 1024);
        buf = new byte[1024];
        buf[0] = '<';
        buf[1] = '1';
        buf[2] = '4';
        buf[3] = '>';
        if (file != null) {
            createReader();
        }
        p = new DatagramPacket(buf, 1024, InetAddress.getByName(dest), port);

        long startTime = System.currentTimeMillis();
        ses.scheduleAtFixedRate(this, 0, 1, TimeUnit.MILLISECONDS);
        Thread.sleep(time * 1000);
        needStop = true;
        ses.shutdown();
        ses.awaitTermination(100, TimeUnit.SECONDS);
        long endTime = System.currentTimeMillis();

        DecimalFormat f = new DecimalFormat("###,###");
        DecimalFormat f2 = new DecimalFormat("#,###.##");
        long occurTime = endTime - startTime;
        System.out.println("dest = " + dest);
        System.out.println("occur time = " + f2.format(occurTime / 1000.0)
                + " seconds");
        System.out.println("total send " + f.format(sendCount) + " msgs, avg "
                + f.format(sendCount * 1000 / occurTime) + "/seconds");
        System.out.println("total send " + f.format(sendBytesCount)
                + " bytes, avg " + f.format(sendBytesCount * 1000 / occurTime)
                + "/seconds");
        if (reader != null) {
            reader.close();
        }
    }

    private long _count = 0;// 同一秒内的计数
    private Date _sendtime = null;
    private String _sendtimeStr = null;
    private static final SimpleDateFormat _sdf = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss,SSS");

    private void sendRandomBuf() throws IOException {
        if (_sendtime == null) {
            _sendtime = new Date(System.currentTimeMillis());
            _sendtimeStr = _sdf.format(_sendtime);
        } else {
            long t = System.currentTimeMillis();
            if (t != _sendtime.getTime()) {
                if (t / 1000 != _sendtime.getTime() / 1000) {
                    // 秒数不同，重置
                    _count = 0;
                }
                _sendtime = new Date(t);
                _sendtimeStr = _sdf.format(_sendtime);
            }
        }

        StringBuilder sb = new StringBuilder(128).append("Now time is ")
                .append(_sendtimeStr).append(", count ").append(_count++)
                .append(" in this second. 中文测试");
        byte[] bs = sb.toString().getBytes(encoding);
        int len = bs.length > 1020 ? 1020 : bs.length;
        System.arraycopy(bs, 0, buf, 4, len);
        p.setLength(len + 4);

        sendPack();
    }

    private int _runCount = 0;

    public void run() {
        _runCount++;
        double batchCount = speed / 1000.0;
        double needSend = batchCount * _runCount;
        try {
            if (reader == null) {
                if (speed == 0) {
                    while (!needStop) {
                        sendRandomBuf();
                    }
                } else {
                    while (sendCount <= needSend - 1) {
                        sendRandomBuf();
                    }
                }
            } else {
                if (speed == 0) {
                    while (!needStop) {
                        readLineAndSend();
                    }
                } else {
                    while (sendCount <= needSend - 1) {
                        readLineAndSend();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readLineAndSend() throws IOException {
        String line = reader.readLine();
        if (line == null) {
            createReader();
            line = reader.readLine();
        }
        byte bs[] = line.getBytes(encoding);
        int len = bs.length > 1020 ? 1020 : bs.length;
        System.arraycopy(bs, 0, buf, 4, len);
        p.setLength(len + 4);
        sendPack();
    }

    private void sendPack() throws IOException {
        socket.send(p);
        // System.out.println(new String(p.getData(),
        // 0, p.getLength(), encoding));
        sendCount++;
        sendBytesCount += p.getLength();
    }

}
