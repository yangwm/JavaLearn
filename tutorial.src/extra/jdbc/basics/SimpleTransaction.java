package extra.jdbc.basics;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SimpleTransaction {

    public static void main(String[] args) {
        Connection con = DriverManagerConnection.getConnection();
        PreparedStatement updateSales = null ;
        PreparedStatement updateTotal = null ;
        
        try{
            con.setAutoCommit(false);
            
            // first transaction with update
            updateSales = con.prepareStatement(
                "UPDATE COFFEES SET SALES = ? WHERE COF_NAME LIKE ?");
            updateSales.setInt(1, 50);
            updateSales.setString(2, "Colombian");
            updateSales.executeUpdate();
            
            // second transaction with update
            updateTotal = con.prepareStatement(
                "UPDATE COFFEES SET TOTAL = TOTAL + ? WHERE COF_NAME LIKE ?");
            updateTotal.setInt(1, 50);
            updateTotal.setString(2, "Colombian");
            updateTotal.executeUpdate();
            
            // throw a exception valid this transaction ...  
            /*if (1 == 1) {
                throw new Exception();
            }*/
            con.commit(); // commit two transaction
            con.setAutoCommit(true);
        }catch(Exception e){
            e.printStackTrace();
            try {
                con.rollback(); // rollback two transaction
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }finally{
            DriverManagerConnection.close(con, updateSales, updateTotal);
        }
    }

}

