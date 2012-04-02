/**
 * 
 */

package cmd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;


/**
 * @author yangwm in Jun 8, 2009 5:22:35 PM
 */
public class ReadEchoUtil {
    

    // ----------------- process ------------------------------
    
    /**
     * 
     * @param process
     * @return
     */
    public static String readEcho(Process process) throws IOException {
        String str = null;
        
        InputStream is = null;
        try {
            is = process.getInputStream();
            str = readEchoScanner(is);
        } finally {
            // nothing 
        }

        return str;
    }
    
    // ----------------- socket ------------------------------
    
    /**
     * 
     * @param process
     * @return
     */
    public static String readEcho(Socket socket) throws IOException {
        String str = null;
        
        InputStream is = null;
        OutputStream os = null;
        try {
            is = socket.getInputStream();
            os = socket.getOutputStream();
            str = readEchoTelnet(is, os);
        } finally {
            // nothing 
        }

        return str;
    }
    
    // ----------------- session ------------------------------
    
    /**
     * 
     * @param process
     * @return
     */
    public static String readEcho(Session session) throws IOException {
        String str = null;
        
        InputStream is = null;
        try {
            is = new StreamGobbler(session.getStdout());
            str = readEchoScanner(is);
        } finally {
            // nothing 
        }

        return str;
    }
    
    // ----------------- basic ------------------------------
    
    public static final String DEFAULT_ENCODE = "gbk";

    /**
     * 
     * create by yangwm in Jan 21, 2010 12:44:03 PM
     * @param socket
     * @return
     * @throws IOException
     */
    public static String readEchoTelnet(InputStream is, OutputStream os) 
            throws IOException {
        byte[] buffer = new byte[1024 * 100];
        int len = is.read(buffer);
        if (len <= 0){
            return null;
        }
        
        List<Byte> bsList = new ArrayList<Byte>();
        TelnetNegotiationCmdList<TelnetNegotiationCmd> cmdList = 
            new TelnetNegotiationCmdList<TelnetNegotiationCmd>();
        VirtualTerminal virtualTerminal = new VirtualTerminal();
        for (int i = 0; i < len; i++) {
            char c = toChar(buffer[i]);
            if (c == TelnetNegotiationCmd.IAC) {
                TelnetNegotiationCmd command = new TelnetNegotiationCmd();
                if (toChar(buffer[i + 2]) == 1
                        || toChar(buffer[i + 2]) == 3) {
                    int verb = toChar(buffer[i + 1]);
                    if (verb == TelnetNegotiationCmd.SB) {

                    } else if (verb == TelnetNegotiationCmd.WILL) {
                        command.option = (byte) TelnetNegotiationCmd.DO;
                    } else if (verb == TelnetNegotiationCmd.WONT) {
                        command.option = (byte) TelnetNegotiationCmd.WILL;
                    } else if (verb == TelnetNegotiationCmd.DO) {
                        command.option = (byte) TelnetNegotiationCmd.WILL;
                    } else if (verb == TelnetNegotiationCmd.DONT) {
                        command.option = (byte) TelnetNegotiationCmd.WONT;
                    }
                } else if (toChar(buffer[i + 1]) == TelnetNegotiationCmd.WILL
                        || toChar(buffer[i + 1]) == TelnetNegotiationCmd.WONT) {
                    command.option = (byte) TelnetNegotiationCmd.DONT;
                } else if (toChar(buffer[i + 1]) == TelnetNegotiationCmd.DO
                        || toChar(buffer[i + 1]) == TelnetNegotiationCmd.DONT) {
                    command.option = (byte) TelnetNegotiationCmd.WONT;
                }
                command.value = buffer[i + 2];
                cmdList.add(command);
                i += 2;
            } else {
                Byte b = virtualTerminal.process(c);
                if (b != null) {
                    bsList.add(b);
                }
            }

        }
        os.write(cmdList.getBytes());
        
        int size = bsList.size();
        byte[] bs = new byte[size];
        for (int i = 0; i < size; i++) {
            bs[i] = ((Byte) bsList.get(i)).byteValue();
        }
        String rv = new String(bs, DEFAULT_ENCODE);

        return rv;
    }

    /**
     * recieveEcho with InputStream and VirtualTerminal
     *
     * @return String
     * @throws IOException 
     * @throws UnsupportedEncodingException 
     */
    public static String readEchoInputStream(InputStream is) throws IOException {
        byte[] buffer = new byte[1024 * 100];
        int len = is.read(buffer);
        if (len <= 0){
            return null;
        }
        
        ArrayList<Byte> bsList = new ArrayList<Byte>();
        VirtualTerminal virtualTerminal = new VirtualTerminal();
        for (int i = 0; i < len; i++) {
            char c = toChar(buffer[i]);
            Byte b = virtualTerminal.process(c);
            if (b != null) {
                bsList.add(b);
            }
            continue;
        }
        
        int size = bsList.size();
        byte[] bs = new byte[size];
        for (int i = 0; i < size; i++) {
            bs[i] = ((Byte) bsList.get(i)).byteValue();
        }
        
        String str = new String(bs, DEFAULT_ENCODE);

        return str;
    }

    /**
     *
     * @param b byte
     * @return char
     */
    private static char toChar(byte b) {
        return (char) (b & 0xff);
    }

    
    /**
     * recieveEcho with Scanner 
     *
     * @return String
     * @throws UnsupportedEncodingException 
     */
    public static String readEchoScanner(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        
        Scanner in = new Scanner(is);
        while (in.hasNextLine()) {
            String line = in.nextLine();
            
            sb.append("\n");
            sb.append(line);
        }

        return sb.toString();
    }
    
    /**
     * recieveEcho with BufferedReader
     *
     * @return String
     * @throws IOException 
     * @throws UnsupportedEncodingException 
     */
    public static String readEchoBufferedReader(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        String line = null;
        while ((line = in.readLine()) != null) {
            sb.append("\n");
            sb.append(line);
        }
        
        return sb.toString();
    }
    
    
}
