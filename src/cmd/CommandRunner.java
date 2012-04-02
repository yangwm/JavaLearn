package cmd;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.Session;

/**
 * 命令执行器 
 * 
 * local : Provides static methods for running local 
 * telnet : Provides static methods for running telnet 
 * scp and ssh : Provides static methods for running ssh, scp as well as local commands.(http://www.ganymed.ethz.ch/ssh2/)
 * 参见： http://blog.csdn.net/fishyqd/archive/2006/08/17/1087305.aspx
 */
public class CommandRunner {
  /**
   * Logger for this class
   */
  private static final Log logger = LogFactory.getLog(CommandRunner.class);

  private CommandRunner() {
  }
  
  //------------------------- scp and SSH -----------------------
  
  /**
   * Get remote file through scp
   * @param host
   * @param username
   * @param password
   * @param remoteFile
   * @param localDir
   * @throws IOException
   */
  public static void scpGet(String host, String username, String password,
      String remoteFile, String localDir) {
    if (logger.isDebugEnabled()) {
      logger.debug("spc [" + remoteFile + "] from " + host + " to " + localDir);
    }
    
    Connection conn = null;
    try {
        conn = getOpenedConnection(host, username, password);
        SCPClient client = new SCPClient(conn);
        client.get(remoteFile, localDir);
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        if (conn != null) {
            conn.close();
        }
    }
  }
  
  /**
   * Put local file to remote machine.
   * @param host
   * @param username
   * @param password
   * @param localFile
   * @param remoteDir
   * @throws IOException
   */
  public static void scpPut(String host, String username, String password,
      String localFile, String remoteDir) {
    if (logger.isDebugEnabled()) {
      logger.debug("spc [" + localFile + "] to " + host + remoteDir);
    }
    Connection conn = null;
    try {
        conn = getOpenedConnection(host, username, password);
        SCPClient client = new SCPClient(conn);
        client.put(localFile, remoteDir);
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        if (conn != null) {
            conn.close();
        }
    }
  }
  
  /**
   * Run SSH command.
   * @param host
   * @param username
   * @param password
   * @param cmd
   * @return exit status
   * @throws IOException
   */
  public static String runSsh(String host, String username, String password,
      String cmd) {
    if (logger.isDebugEnabled()) {
      logger.debug("running runSsh cmd [" + cmd + "]");
    }
    
    String result = null;
    Connection conn = null;
    Session session = null;
    try {
        conn = getOpenedConnection(host, username, password);
        session = conn.openSession();
        session.execCommand(cmd);

        ReadEchoUtil.readEcho(session);
        
        //sess.getExitStatus().intValue();
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        if (session != null) {
            session.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

    return result;
  }
  
  /**
   * return a opened Connection
   * @param host
   * @param username
   * @param password
   * @return
   * @throws IOException
   */
  private static Connection getOpenedConnection(String host, String username,
      String password) throws IOException {
    if (logger.isDebugEnabled()) {
      logger.debug("connecting to " + host + " with user " + username
          + " and pwd " + password);
    }

    Connection conn = new Connection(host);
    conn.connect(); // make sure the connection is opened
    boolean isAuthenticated = conn.authenticateWithPassword(username, password);
    if (isAuthenticated == false) {
      throw new IOException("Authentication failed.");
    }
    return conn;
  }
  
  
  // ------------------------- local -----------------------
  
  /**
   * Run local command
   * @param cmd
   * @return exit status
   * @throws IOException
   */
  public static String runLocal(String cmd) {
    if (logger.isDebugEnabled()) {
      logger.debug("running local cmd [" + cmd + "]");
    }

    String result = null;
    Process process = null;
    try {
        process = Runtime.getRuntime().exec(cmd);
        result = ReadEchoUtil.readEcho(process);
        
        //p.exitValue();
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        if (process != null) {
            process.destroy();
        }
    }
    return result;
  }
  
  //------------------------- telnet -----------------------
  
  public static final byte[] COMMAND_END = "\r\n".getBytes();
  
  /**
   * Run telnet command.
   * http://www.ietf.org/rfc/rfc854.txt
   * 
   * @param host
   * @param username
   * @param password
   * @param cmd
   * @return exit status
   * @throws IOException
   */
  public static String runTelnet(String host, String username, String password,
      String cmd) {
    if (logger.isDebugEnabled()) {
      logger.debug("running telnet cmd [" + cmd + "]");
    }
    
    StringBuilder result = new StringBuilder();
    Socket socket = null;
    try {
        SocketAddress socketAddress = new InetSocketAddress(host, 23);
        socket = new Socket();
        socket.connect(socketAddress, 3 * 1000);
        socket.setSoTimeout(1000);
        
        boolean authed = auth(socket, username, password, ">");
        if (authed == true) {
            logger.debug("Authentication Success****************");
            
            socket.getOutputStream().write(cmd.getBytes());
            socket.getOutputStream().write(COMMAND_END);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            String rs = ReadEchoUtil.readEcho(socket);
            logger.debug("\n" + rs);
        } else {
            logger.debug("Authentication Failure****************");
        }
    } catch (UnknownHostException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    return result.toString();
  }

  /**
   * telnet auth 
   * 
   * @param username
   * @param password
   * @param prompt
   * @throws IOException
   */
  public static boolean auth(Socket socket, String username, String password, String prompt)
      throws IOException {
      
      // send useusernamerid
      String rs = readEchoUtilColon(socket);
      logger.debug("\n" + rs);
      socket.getOutputStream().write(username.getBytes());
      socket.getOutputStream().write(COMMAND_END);
      
      // send password
      rs = readEchoUtilColon(socket);
      logger.debug("\n" + rs);
      socket.getOutputStream().write(password.getBytes());
      socket.getOutputStream().write(COMMAND_END);
      try {
          Thread.sleep(500);
      } catch (InterruptedException e1) {
          e1.printStackTrace();
      }
      
      // send ok 
      socket.getOutputStream().write("".getBytes());
      socket.getOutputStream().write(COMMAND_END);
      try {
          Thread.sleep(500);
      } catch (InterruptedException e1) {
          e1.printStackTrace();
      }
      
      // send cmd
      StringBuffer inputStr = new StringBuffer();
      while (true) {
          inputStr.append(ReadEchoUtil.readEcho(socket));
          if (Colon.endsWith(inputStr.toString())) {
              return false;
          }
          if (inputStr.toString().endsWith(prompt)) {
              return true;
          }
      }
      
  }
  
  /**
   * 
   * create by yangwm in Jan 21, 2010 2:06:30 PM
   * @param socket
   * @return
   * @throws IOException
   */
  public static String readEchoUtilColon(Socket socket) throws IOException {
      try {
          Thread.sleep(500);
      } catch (InterruptedException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
      }
      
      StringBuffer str = new StringBuffer();
      do {
          str.append(ReadEchoUtil.readEcho(socket));
          //logger.debug("str=" + str);
          if (str.toString().toLowerCase().indexOf("failure") > 0) {
              throw new IOException("readEchoUtilColon error");
          }
      } while (!Colon.endsWith(str.toString()));
      return str.toString();
  }
  
}
