package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
 * @author yangwm in Feb 5, 2009 9:34:53 AM
 */
public class ConnectionUtil {
    
    
    public static Connection getConnection() {
        //return getConnection("jdbc:oracle:thin:@40.1.46.62:1521:clicfj", "ams", "ams");
        //return getConnection("jdbc:oracle:thin:@40.1.46.62:1521:clicfj", "sys as sysdba", "clicfj");
        //return getConnection("jdbc:oracle:thin:@100.9.0.40:1521:ams", "ams", "ams");
        //return getConnection("jdbc:oracle:thin:@100.4.31.88:1521:ra", "system", "amsadmin");
        //return getConnection("jdbc:oracle:thin:@100.4.31.88:1521:ra", "ra", "ra");
        //return getConnection("jdbc:oracle:thin:@100.4.31.210:1521:TEST", "system", "123456");
        //return getConnection("jdbc:oracle:thin:@127.0.0.1:1521:YANGWM", "ams", "ams");
        //return getConnection("jdbc:oracle:thin:@127.0.0.1:1521:YANGWM", "sys as sysdba", "yangwm");
        //return getConnection("jdbc:oracle:thin:@192.168.0.155:1521:ams", "rcm", "rcm");
        //return getConnection("jdbc:oracle:thin:@192.168.16.239:1521:server23", "sys as sysdba", "server239");
        return getConnection("jdbc:oracle:thin:@192.168.16.236:1521:orcl", "esa", "ee");
        //return getConnection("jdbc:oracle:thin:@10.1.101.124:1521:iam", "ams", "ams");
    }

    public static Connection getConnection(String url, String username, String password) {
        return getConnection("oracle.jdbc.driver.OracleDriver", url, username, password);
    }

    public static Connection getConnectionForMysql() {
        return getConnection("com.mysql.jdbc.Driver", "jdbc:mysql://127.0.0.1:3306/test", "root", "");
        //return getConnection("com.mysql.jdbc.Driver", "jdbc:mysql://10.218.23.155:3306/test?useUnicode=true&amp;characterEncoding=utf8", "tq", "tq123456");
    }

    public static Connection getConnectionForPgsql() {
        //return getConnection("org.postgresql.Driver", "jdbc:postgresql://127.0.0.1:5432/testdb", "yangwm", "");
        //return getConnection("org.postgresql.Driver", "jdbc:postgresql://192.168.16.234:5432/ams_v13", "ams", "123");
        return getConnection("org.postgresql.Driver", "jdbc:postgresql://192.168.16.179:5432/umap", "admin", "abc123");
    }
    
    public static Connection getConnectionForSybase() {
        return getConnection("com.sybase.jdbc3.jdbc.SybDataSource", "jdbc:sybase:Tds:100.4.31.125:5000/ULTRAPOWYANGWM?charset=utf8", "sa", "");
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
    
    public static void close(Connection conn, Statement st1, Statement st2, Statement st3, Statement st4, ResultSet rs1) {
        release(conn, st1, st2, st3, st4, null, null, rs1, null, true);
    }
    
    public static void close(Connection conn, Statement st1, Statement st2, Statement st3, Statement st4, Statement st5) {
        release(conn, st1, st2, st3, st4, st5, null, null, null, true);
    }
    
    public static void close(Connection conn, Statement st1, Statement st2, ResultSet rs1) {
        release(conn, st1, st2, null, null, null, null, rs1, null, true);
    }
    
    public static void close(Connection conn, Statement st1,ResultSet rs1) {
        release(conn, st1, null, null, null, null, null, rs1, null, true);
    }
    
    public static void close(Connection conn, Statement st1, boolean needClose) {
        release(conn, st1, null, null, null, null, null, null, null, needClose);
    }
    
    public static void close(Connection conn, Statement st1) {
        release(conn, st1, null, null, null, null, null, null, null, true);
    }
    
    public static void close(Connection conn) {
        release(conn, null, null, null, null, null, null, null, null, true);
    }
    
    public static void close(Statement st1) {
        release(null, st1, null, null, null, null, null, null, null, true);
    }
    
}
