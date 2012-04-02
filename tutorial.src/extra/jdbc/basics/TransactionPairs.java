
/*
 * Copyright (c) 1995 - 2008 Sun Microsystems, Inc.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Sun Microsystems nor the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

package extra.jdbc.basics;

import java.sql.*;
     
public class TransactionPairs {

    public static void main(String args[]) {
        //String url = "jdbc:mySubprotocol:myDataSource";
        String url = "jdbc:oracle:thin:@211.154.39.114:11101:DEV"; 
        Connection con = null;
        Statement stmt;
        try {
            //Class.forName("myDriver.ClassName");
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch(java.lang.ClassNotFoundException e) {
            System.err.print("ClassNotFoundException: "); 
            System.err.println(e.getMessage());
        }
        
        PreparedStatement updateSales;
        PreparedStatement updateTotal;
        String updateString = "update COFFEES " +
            "set SALES = ? where COF_NAME like ?";
    
        String updateStatement = "update COFFEES " +
            "set TOTAL = TOTAL + ? where COF_NAME like ?";
        String query = "select COF_NAME, SALES, TOTAL from COFFEES";
        
        try {
            //con = DriverManager.getConnection(url, "myLogin", "myPassword");
            con = DriverManager.getConnection(url, "apps", "apps");
    
            updateSales = con.prepareStatement(updateString);
            updateTotal = con.prepareStatement(updateStatement);
            // sales - 10 , before : {175, 150, 60, 155, 90}
            int [] salesForWeek = {165, 140, 50, 145, 80};
            String [] coffees = {"Colombian", "French_Roast", 
                "Espresso", "Colombian_Decaf",
                "French_Roast_Decaf"};
            int len = coffees.length;
            con.setAutoCommit(false);
            for (int i = 0; i < len; i++) {
                updateSales.setInt(1, salesForWeek[i]);
                updateSales.setString(2, coffees[i]);
                updateSales.executeUpdate();
    
                updateTotal.setInt(1, salesForWeek[i]);
                updateTotal.setString(2, coffees[i]);
                updateTotal.executeUpdate();
                
                // throw a exception valid this transaction ...  
                /*if (i == coffees.length - 1) {
                    throw new SQLException();
                }*/
                
                con.commit();
            }
            con.setAutoCommit(true);
    
            updateSales.close();
            updateTotal.close();
    
            stmt = con.createStatement();                           
            ResultSet rs = stmt.executeQuery(query);
                
            while (rs.next()) {
                String c = rs.getString("COF_NAME");
                int s = rs.getInt("SALES");
                int t = rs.getInt("TOTAL");
                System.out.println(c + "     " +  s + "    " + t);
            }
    
            stmt.close();
            con.close();
    
        } catch(SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
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
            if (con != null) {
                try {
                    System.err.print("Transaction is being ");
                    System.err.println("rolled back");
                    con.rollback();
                } catch(SQLException excep) {
                    System.err.print("SQLException: ");
                    System.err.println(excep.getMessage());
                }
            }
        }   
    }
}

