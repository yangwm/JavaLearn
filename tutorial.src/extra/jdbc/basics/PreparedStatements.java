package extra.jdbc.basics;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class PreparedStatements {
    
    /**
     * executeUpdate Method 
     */
    public static class Tester1 {
        public static void main(String[] args) {
            Connection con = DriverManagerConnection.getConnection();
            PreparedStatement updateSales = null ;
            
            try{
                updateSales = con.prepareStatement(
                    "UPDATE COFFEES SET SALES = ? WHERE COF_NAME LIKE ?");
                updateSales.setInt(1, 77);
                updateSales.setString(2, "Colombian");
                updateSales.executeUpdate();
                
                // changes SALES column of French Roast row to 100
                updateSales.setInt(1, 101); 
                updateSales.setString(2, "French_Roast"); 
                updateSales.executeUpdate(); 
                
                // changes SALES column of Espresso row to 100 (the first 
                // parameter stayed 100, and the second parameter was reset
                // to "Espresso")            
                updateSales.setString(2, "Espresso");
                updateSales.executeUpdate(); 

                // java.sql.SQLException: 索引中丢失  IN 或 OUT 参数:: 1
                updateSales.clearParameters();
                updateSales.setString(2, "Espresso");
                updateSales.executeUpdate(); 

            }catch(Exception e){
                e.printStackTrace();
            }finally{
                DriverManagerConnection.close(con, updateSales);
            }
        }
    }

    /**
     * Using a Loop to executeUpdate Method
     */
    public static class Tester2 {
        public static void main(String[] args) {
            Connection con = DriverManagerConnection.getConnection();
            PreparedStatement updateSales = null ;
            
            try{
                String updateString = "update COFFEES " +
                    "set SALES = ? where COF_NAME like ?";
                updateSales = con.prepareStatement(updateString);
                int [] salesForWeek = {175, 150, 60, 155, 90};
                String [] coffees = {"Colombian", "French_Roast", "Espresso",
                    "Colombian_Decaf", "French_Roast_Decaf"};
                int len = coffees.length;
                for(int i = 0; i < len; i++) {
                    updateSales.setInt(1, salesForWeek[i]);
                    updateSales.setString(2, coffees[i]);
                    updateSales.executeUpdate();
                }
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                DriverManagerConnection.close(con, updateSales);
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
    public static class Tester3 {
        public static void main(String[] args) {
            Connection con = DriverManagerConnection.getConnection();
            PreparedStatement updateSales = null ;
            
            try{
                updateSales = con.prepareStatement(
                    "UPDATE COFFEES SET SALES = ? WHERE COF_NAME LIKE ?");
                updateSales.setInt(1, 50); 
                updateSales.setString(2, "Espresso"); 
                int n = updateSales.executeUpdate(); // n = 1 because one row had a change in it
                System.out.println("n=" + n);
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                DriverManagerConnection.close(con, updateSales);
            }
        }
    }
    
}
