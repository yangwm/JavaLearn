/**
 * 
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

/**
 * 
 * 
 * @author yangwm Jul 28, 2011 2:25:04 PM
 */
public class SampleTest {
    /**
     * Logger for this class
     */
    private static final Logger log = Logger.getLogger(SampleTest.class);
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("SampleTest begin...");
//        byte b = (byte) 0xF0;
//        byte b1 = (byte) 0xA4;
//        byte b2 = (byte) 0xA9;
//        byte b3 = (byte) 0xB9;
//        String s = new String(new byte[]{b, b1, b2, b3});
//        System.out.println(s);
        
        testInsert();
    }
    
    public static void testInsert() {
        // delete from mblog5.status where id=4; 
        Connection conn = null;
        Statement stm = null ;
        ResultSet rs = null;
        try{
            conn = getConnectionForMysql();
            
            stm = conn.createStatement();
            String sqlInsert = " insert into mblog5.status(id, content) values (1, \"wm1会不会有𤩹 \\xF0\\xA4\\xA9\\xB9\") "; 
            log.debug(sqlInsert);
            stm.execute(sqlInsert);
            
            String sqlInsert2 = " insert into mblog5.status(id, content) values (?, ?) "; 
            PreparedStatement ps = conn.prepareStatement(sqlInsert2);
            ps.setLong(1, 2L);
            ps.setString(2, "wm2会不会有𤩹 \\xF0\\xA4\\xA9\\xB9");
            log.debug(sqlInsert2);
            ps.execute();
            
            String sqlInsert3 = " insert into mblog5.status(id, content) values (?, ?) "; 
            PreparedStatement psB = conn.prepareStatement(sqlInsert3);
            psB.setLong(1, 3L);
            psB.setBytes(2, "wm3会不会有𤩹 \\xF0\\xA4\\xA9\\xB9".getBytes());
            log.debug(sqlInsert3);
            psB.execute();
            
            String sqlSelect = " select * from mblog5.status where id < 5 limit 5 "; 
            log.debug(sqlSelect);
            rs = stm.executeQuery( sqlSelect );
            while ( rs.next() ){
                String content = new String(rs.getBytes("content"));
                log.debug(content);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            close(conn, stm, rs);
        }
    }
    
    public static void testQuery() {
        Connection conn = null;
        Statement stm = null ;
        ResultSet rs = null;
        try{
            conn = getConnectionForMysql();
            stm = conn.createStatement();
            for (int i = 5; i < 10; i++) {
                String sqlSelect = " select * from mblog" + i + ".status limit 2 " ; 
                log.debug(sqlSelect);
                rs = stm.executeQuery( sqlSelect );
                while ( rs.next() ){
                    String content = new String(rs.getBytes("content"));
                    log.debug(content);
                }  
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            close(conn, stm, rs);
        }
    }

    public static Connection getConnectionForMysql() {
        //String url = "jdbc:mysql://m4521i.apollo.grid.sina.com.cn:4521/?useUnicode=true&amp;characterEncoding=UTF-8&amp;autoReconnect=true";
        String url = "jdbc:mysql://10.75.48.21:4521/?useUnicode=true&amp;characterEncoding=UTF-8&amp;autoReconnect=true";
        System.out.println("url:" + url);
        return getConnection("com.mysql.jdbc.Driver", url, "openapi", "im1400uc");
    }
    
    public static Connection getConnection(String driver, String url, String username, String password) {
        Connection con = null;
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, username, password);
        } catch (SQLException e)  {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return con;
    }
    
    public static void close(Connection conn, Statement st1,ResultSet rs1) {
        release(conn, st1, null, null, null, null, null, rs1, null, true);
    }
    
    public static void release(Connection conn, Statement st1, Statement st2, Statement st3, Statement st4, Statement st5, Statement st6, ResultSet rs1, ResultSet rs2, boolean needClose) {
        if(rs1 != null ) try {rs1.close();} catch(SQLException e) {e.printStackTrace();}
        if(rs2 != null ) try {rs2.close();} catch(SQLException e) {e.printStackTrace();}
        if(st1 != null ) try {st1.close();} catch(SQLException e) {e.printStackTrace();}
        if(st2 != null ) try {st2.close();} catch(SQLException e) {e.printStackTrace();}
        if(st3 != null ) try {st3.close();} catch(SQLException e) {e.printStackTrace();}
        if(st4 != null ) try {st4.close();} catch(SQLException e) {e.printStackTrace();}
        if(st5 != null ) try {st5.close();} catch(SQLException e) {e.printStackTrace();}
        if(st6 != null ) try {st6.close();} catch(SQLException e) {e.printStackTrace();}
        if(conn != null && needClose == true) try {conn.close();} catch(SQLException e) {e.printStackTrace();}
    }

}

/*
javac -classpath commons-lang-2.4.jar:commons-logging-1.1.1.jar:log4j-1.2.15.jar:mysql-connector-java-5.1.10-bin.jar SampleTest.java

java -classpath commons-lang-2.4.jar:commons-logging-1.1.1.jar:log4j-1.2.15.jar:mysql-connector-java-5.1.10-bin.jar:. SampleTest 
*/

