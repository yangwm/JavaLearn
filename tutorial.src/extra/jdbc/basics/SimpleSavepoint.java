package extra.jdbc.basics;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;

public class SimpleSavepoint {

    public static void main(String[] args) {
        Connection conn = DriverManagerConnection.getConnection();
        Statement stmt = null ;
        ResultSet rs = null;
        Savepoint svpt1 = null;
        
        try{
            stmt = conn.createStatement();
            stmt = conn.createStatement();
            int rows = stmt.executeUpdate("INSERT INTO TAB1 (COL1) VALUES " +
                "(?FIRST?)");
            // set savepoint
            svpt1 = conn.setSavepoint("SAVEPOINT_1");
            rows = stmt.executeUpdate("INSERT INTO TAB1 (COL1) " +
                "VALUES (?SECOND?)");
            // ...
            conn.rollback(svpt1);
            // ...
            conn.commit();
        }catch(Exception e){
            e.printStackTrace();
            try {
                conn.rollback(svpt1);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }finally{
            DriverManagerConnection.close(conn, stmt, rs);
        }
    }

}
