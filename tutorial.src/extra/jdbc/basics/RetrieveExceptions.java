package extra.jdbc.basics;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RetrieveExceptions {

    public static void main(String[] args) {
        Connection con = DriverManagerConnection.getConnection();
        Statement stmt = null ;
        ResultSet rs = null;
        
        try {
            // Code that could generate an exception goes here.
            // If an exception is generated, the catch block below
            // will print out information about it.
            stmt = con.createStatement();
            rs = stmt.executeQuery("select COF_NAME from COFFEES");
            
            while (rs.next()) {
                String coffeeName = rs.getString("COF_NAME");
                System.out.println("Coffees available at the Coffee Break:  ");
                System.out.println("    " + coffeeName);
            }
        } catch(SQLException ex) {
            System.out.println("\n--- SQLException caught ---\n");
            while (ex != null) {
                System.out.println("Message:   "
                                           + ex.getMessage ());
                System.out.println("SQLState:  "
                                           + ex.getSQLState ());
                System.out.println("ErrorCode: "
                                           + ex.getErrorCode ());
                ex = ex.getNextException();
                System.out.println("");
            }
        } finally{
            DriverManagerConnection.close(con, stmt, rs);
        }
    }

}
