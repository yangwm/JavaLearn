package extra.jdbc.basics;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class SimpleJoins {
    
    public static void main(String[] args) {
        Connection con = DriverManagerConnection.getConnection();
        Statement stmt = null ;
        ResultSet rs = null;
        
        try{
            stmt = con.createStatement();
            String query = "SELECT COFFEES.COF_NAME " +
                "FROM COFFEES, SUPPLIERS " +
                "WHERE SUPPLIERS.SUP_NAME LIKE 'Acme, Inc.' " +
                "and SUPPLIERS.SUP_ID = COFFEES.SUP_ID" ;

            rs = stmt.executeQuery(query);
            System.out.println("Coffees bought from Acme, Inc.: ");
            while (rs.next()) {
                String coffeeName = rs.getString("COF_NAME");
                System.out.println("     " + coffeeName);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            DriverManagerConnection.close(con, stmt, rs);
        }
        
    }
    
}
