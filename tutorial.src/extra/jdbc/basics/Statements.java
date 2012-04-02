package extra.jdbc.basics;

import java.sql.Connection;
import java.sql.Statement;

public class Statements {

    /**
     * executeUpdate Method 
     */
    public static class Tester1 {
        public static void main(String[] args) {
            Connection con = DriverManagerConnection.getConnection();
            Statement stmt = null ;
            
            try{
                stmt = con.createStatement();
                String updateString = "UPDATE COFFEES SET SALES = 75 " + 
                    "WHERE COF_NAME LIKE 'Colombian'";
                stmt.executeUpdate(updateString);
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                DriverManagerConnection.close(con, stmt);
            }
            
        }
    }

    /**
     * Return Values for the executeUpdate Method 
     * 
     * Note that when the return value for executeUpdate is 0, it can mean one of two things:
     * the statement executed was an update statement that affected zero rows
     * the statement executed was a DDL statement.
     */
    public static class Tester2 {
        public static void main(String[] args) {
            Connection con = DriverManagerConnection.getConnection();
            Statement stmt = null ;
            
            try{
                stmt = con.createStatement();
                String createTableCoffees = 
                    "create table coffees ("
                    + "cof_name    varchar2(32),"
                    + "sup_id      number, "
                    + "price       number, "
                    + "sales       number, "
                    + "total       number"
                    + ")";
                int n = stmt.executeUpdate(createTableCoffees); // n = 0
                System.out.println("n=" + n);
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                DriverManagerConnection.close(con, stmt);
            }
            
        }
    }
}
