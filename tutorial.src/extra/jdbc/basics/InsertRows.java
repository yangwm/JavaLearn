package extra.jdbc.basics;

import java.sql.*;

public class InsertRows {

    public static void main(String args[]) {
    
       //String url = "jdbc:mySubprotocol:myDataSource";
       String url = "jdbc:oracle:thin:@211.154.39.114:11101:DEV"; 
       Connection con;
       Statement stmt;
       try {
           //Class.forName("myDriver.ClassName");
           Class.forName("oracle.jdbc.driver.OracleDriver");
       } catch(java.lang.ClassNotFoundException e) {
           System.err.print("ClassNotFoundException: "); 
           System.err.println(e.getMessage());
       }
    
       try {
         //con = DriverManager.getConnection(url, "myLogin", "myPassword");
         con = DriverManager.getConnection(url, "apps", "apps");
    
         stmt = con.createStatement(
                  ResultSet.TYPE_SCROLL_SENSITIVE,
                  ResultSet.CONCUR_UPDATABLE);              
    
         //ResultSet uprs = stmt.executeQuery("SELECT * FROM COFFEES"); // error
         ResultSet uprs = stmt.executeQuery(
             "SELECT COF_NAME, SUP_ID, PRICE, SALES, TOTAL FROM COFFEES");
         System.out.println("uprs.getConcurrency()=" + uprs.getConcurrency());
         
         System.out.println("----------Table COFFEES before insertion:");
           while (uprs.next()) {
             String name = uprs.getString("COF_NAME");
             int id = uprs.getInt("SUP_ID");
             float price = uprs.getFloat("PRICE");
             int sales = uprs.getInt("SALES");
             int total = uprs.getInt("TOTAL");
             System.out.println(
               name + "   " + id + "   " + price + "   " 
               + sales + "   " + total);
           }
    
         uprs.moveToInsertRow();
    
         uprs.updateString("COF_NAME", "Kona");
         uprs.updateInt("SUP_ID", 150);
         uprs.updateDouble("PRICE", 10.99f);
         uprs.updateInt("SALES", 0);
         uprs.updateInt("TOTAL", 0);
    
         uprs.insertRow();
    
         uprs.updateString("COF_NAME", "Kona_Decaf");
         uprs.updateInt("SUP_ID", 150);
         uprs.updateDouble("PRICE", 11.99f);
         uprs.updateInt("SALES", 0);
         uprs.updateInt("TOTAL", 0);
    
         uprs.insertRow();
    
         uprs.beforeFirst();
    
         System.out.println("---------Table COFFEES after insertion:");
         while (uprs.next()) {
           String name = uprs.getString("COF_NAME");
           int id = uprs.getInt("SUP_ID");
           float price = uprs.getFloat("PRICE");
           int sales = uprs.getInt("SALES");
           int total = uprs.getInt("TOTAL");
           System.out.println(
             name + "   " + id + "   " + price + "   " 
             + sales + "   " + total);
         }
    
         uprs.close();
         stmt.close();
         con.close();
    
       } catch(SQLException ex) {
           ex.printStackTrace();
         System.err.println(
           "SQLException: " + ex.getMessage());
       }
    }
}

