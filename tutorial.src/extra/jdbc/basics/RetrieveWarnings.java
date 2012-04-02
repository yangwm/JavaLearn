package extra.jdbc.basics;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;

public class RetrieveWarnings {

    public static void main(String[] args) {
        Connection con = DriverManagerConnection.getConnection();
        Statement stmt = null ;
        ResultSet rs = null;
        
        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery("select COF_NAME from COFFEES");
            
            while (rs.next()) {
                String coffeeName = rs.getString("COF_NAME");
                System.out.println("Coffees available at the Coffee Break:  ");
                System.out.println("    " + coffeeName);
                SQLWarning warning = stmt.getWarnings();
                if (warning != null) {
                    System.out.println("\n---Warning---\n");
                    while (warning != null) {
                        System.out.println("Message: "
                                                       + warning.getMessage());
                        System.out.println("SQLState: "
                                                       + warning.getSQLState());
                        System.out.print("Vendor error code: ");
                        System.out.println(warning.getErrorCode());
                        System.out.println("");
                        warning = warning.getNextWarning();
                    }
                }
                SQLWarning warn = rs.getWarnings();
                if (warn != null) {
                    System.out.println("\n---Warning---\n");
                    while (warn != null) {
                        System.out.println("Message: "
                                                       + warn.getMessage());
                        System.out.println("SQLState: "
                                                       + warn.getSQLState());
                        System.out.print("Vendor error code: ");
                        System.out.println(warn.getErrorCode());
                        System.out.println("");
                        warn = warn.getNextWarning();
                    }
                }
            }
        }  catch(SQLException ex) {
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
        } catch(Exception e){
            e.printStackTrace();
        } finally{
            DriverManagerConnection.close(con, stmt, rs);
        }
    }

}
