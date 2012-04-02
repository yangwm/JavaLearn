package extra.jdbc.basics;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DriverManagerConnection {
    public static Connection getConnection() {
        return getConnection("jdbc:oracle:thin:@211.154.39.114:11101:DEV", "apps", "apps");
    }
    public static Connection getConnection(String url, String username, String password) {
        Connection con = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
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
    
    public static void close(Connection conn, Statement st1, Statement st2) {
        release(conn, st1, st2, null, null, null, null, null, null, true);
    }
    
    public static void close(Connection conn, Statement st1, ResultSet rs1) {
        release(conn, st1, null, null, null, null, null, rs1, null, true);
    }
    
    public static void close(Connection conn, Statement st1, boolean needClose) {
        release(conn, st1, null, null, null, null, null, null, null, needClose);
    }
    
    public static void close(Connection conn, Statement st1) {
        release(conn, st1, null, null, null, null, null, null, null, true);
    }
}
