package net;
/**
 * 
 */


import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author yangwm in Dec 12, 2009 3:24:34 PM
 */
public class ServerStateDetector {
    /**
     * Logger for this class
     */
    private static final Log log = LogFactory.getLog(ServerStateDetector.class);
    
    protected ObjectInputStream ois = null;

    public boolean isServerAlive(ServerConfig config) {
        boolean flag = false;
        Socket socket = null;
        String returnValue = null;
        for (int i = 0; i < 3; i++) {
            try {
                socket = new Socket();
                // 设置读数据的超时时间，缺省的设置是0，即超时永远不会发生。超时的判断是累计式的，一次设置后，
                // 每次调用引起的阻塞时间都从该值中扣除，直至另一次超时设置或有超时异常抛出。比如，某种服务需要三次调用read()，
                // 超时设置为1分钟，那么如果某次服务三次read()调用的总时间超过1分钟就会有异常抛出，
                // 如果要在同一个Socket上反复进行这种服务，就要在每次服务之前设置一次超时。
                if(log.isDebugEnabled()){
                    log.debug("建立socket连接");
                }
                socket.connect(new InetSocketAddress(config.getIp(), config.getProbePort()), config.getTimeOut() + i * 300);
                
//              log.info("设置超时时间");
                socket.setSoTimeout(1000);
                
//              log.info("读取流");
                ois = new ObjectInputStream(socket.getInputStream());
                
                returnValue = (String) ois.readObject();
                if(log.isDebugEnabled()){
                    log.debug("探测返回值:" + returnValue);
                }
                
//              log.info("关闭流");
                ois.close();
            } catch (ClassNotFoundException e) {
                log.error("第" + i + "次连接" + config.getIp() + "服务器失败 ClassNotFoundException" + e.getMessage() + ",超时时间"
                                + (config.getTimeOut() + i * 300));
                flag = true;
            } catch (NumberFormatException e) {
                log.error("第" + i + "次连接" + config.getIp() + "服务器失败 NumberFormatException" + e.getMessage() + ",超时时间"
                                + (config.getTimeOut() + i * 300));
                flag = true;
            } catch (IOException e) {
                log.error("第" + i + "次连接" + config.getIp() + "服务器失败 IOException" + e.getMessage() + ",超时时间"
                                + (config.getTimeOut() + i * 300));
                flag = true;
            }finally{
                try {
                    if(log.isDebugEnabled()){
                        log.debug("关闭socket");
                    }
                    socket.close();
                    socket = null;
                    if(flag){
                        sleep();
                        if(log.isDebugEnabled()){
                            log.debug("等待300毫秒");
                        }
                        flag = false;
                    }
                } catch (IOException e) {
                    log.error("IOException " , e);
                }
            }
            if (returnValue != null) {
                if (returnValue.equals(config.getRetValue())) {
                    if(log.isDebugEnabled()){
                        log.debug("第" + i + "次连接" + config.getIp()
                                    + "服务器成功,超时时间" + (config.getTimeOut() + i
                                        * 300));
                    }
                    return true;
                }
            }
        }
        if(log.isInfoEnabled()){
            log.info("服务器" + config.getIp() + "不可用");
        }
        return false;
    }
    
    private void sleep(){
        try {
            Thread.sleep(300);
        } catch (InterruptedException e1) {
            log.error("sleep 异常", e1);
        }
    }
    
    
    static class ServerConfig {
        private String ip;
        private int probePort;
        private int timeOut;
        private String retValue;
        
        public ServerConfig() {
        }
        public ServerConfig(String ip, int probePort, int timeOut, String retValue) {
            this.ip = ip;
            this.probePort = probePort;
            this.timeOut = timeOut;
            this.retValue = retValue;
        }
        
        
        public String getIp() {
            return ip;
        }
        public void setIp(String ip) {
            this.ip = ip;
        }
        public int getProbePort() {
            return probePort;
        }
        public void setProbePort(int probePort) {
            this.probePort = probePort;
        }
        public int getTimeOut() {
            return timeOut;
        }
        public void setTimeOut(int timeOut) {
            this.timeOut = timeOut;
        }
        public String getRetValue() {
            return retValue;
        }
        public void setRetValue(String retValue) {
            this.retValue = retValue;
        }
        
    }
    
    
    /**
     * create by yangwm in Dec 12, 2009 3:24:34 PM
     * @param args
     */
    public static void main(String[] args) {
        ServerConfig serverConfig = new ServerConfig("100.4.31.125", 8081, 300, null);
        new ServerStateDetector().isServerAlive(serverConfig);
    }

}
