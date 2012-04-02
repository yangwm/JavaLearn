package extra.jdbc.basics;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ResultSetMethods {

    /**
     * ResultSet Type : TYPE_SCROLL_SENSITIVE and CONCUR_READ_ONLY
     */
    public static class Tester1 {
        public static void main( String args[] ){
            Connection con = DriverManagerConnection.getConnection();
            Statement stmt = null ;
            ResultSet srs = null;
            
            try{
                stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                srs = stmt.executeQuery("SELECT COF_NAME, PRICE FROM COFFEES");
                
                // 1005 to indicate ResultSet.TYPE_SCROLL_SENSITIVE
                int type = srs.getType();
                System.out.println("srs.getType()=" + type);
                
                while (srs.next()) {
                    String name = srs.getString("COF_NAME");
                    float price = srs.getFloat("PRICE");
                    System.out.println(name + "     " + price);
                }
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                DriverManagerConnection.close(con, stmt, srs);
            }
        }
    }
    /*
    1003 to indicate ResultSet.TYPE_FORWARD_ONLY
    1004 to indicate ResultSet.TYPE_SCROLL_INSENSITIVE
    1005 to indicate ResultSet.TYPE_SCROLL_SENSITIVE 
    *///~

    /**
     * ResultSet Type : TYPE_SCROLL_INSENSITIVE and CONCUR_READ_ONLY
     */
    public static class Tester2 {
        public static void main( String args[] ){
            Connection con = DriverManagerConnection.getConnection();
            Statement stmt = null ;
            ResultSet srs = null;
            
            try{
                stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                srs = stmt.executeQuery("SELECT COF_NAME, PRICE FROM COFFEES");
                srs.afterLast();
                while (srs.previous()) {
                    String name = srs.getString("COF_NAME");
                    float price = srs.getFloat("PRICE");
                    System.out.println(name + "     " + price);
                }
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                DriverManagerConnection.close(con, stmt, srs);
            }
        }
    }

    /**
     * ResultSet Type : TYPE_SCROLL_SENSITIVE and CONCUR_READ_ONLY
     * absolute  Method and relative  Method
     */
    public static class Tester3 {
        public static void main( String args[] ){
            Connection con = DriverManagerConnection.getConnection();
            Statement stmt = null ;
            ResultSet srs = null;
            
            try{
                stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                srs = stmt.executeQuery("SELECT COF_NAME, PRICE FROM COFFEES");
                srs.absolute(4); 
                int rowNum = srs.getRow(); // rowNum should be 4
                System.out.println("rowNum=" + rowNum);
                srs.relative(-3); 
                rowNum = srs.getRow(); // rowNum should be 1
                System.out.println("rowNum=" + rowNum);
                srs.relative(2); 
                rowNum = srs.getRow(); // rowNum should be 3
                System.out.println("rowNum=" + rowNum);
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                DriverManagerConnection.close(con, stmt, srs);
            }
        }
    }

    /**
     * ResultSet Type : TYPE_SCROLL_SENSITIVE and CONCUR_READ_ONLY
     * isAfterLast Method
     */
    public static class Tester4 {
        public static void main( String args[] ){
            Connection con = DriverManagerConnection.getConnection();
            Statement stmt = null ;
            ResultSet srs = null;
            
            try{
                stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                srs = stmt.executeQuery("SELECT COF_NAME, PRICE FROM COFFEES");
                if (srs.isAfterLast() == false) {
                    srs.afterLast();        
                }
                while (srs.previous()) {
                    String name = srs.getString("COF_NAME");
                    float price = srs.getFloat("PRICE");
                    System.out.println(name + "     " + price);
                }
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                DriverManagerConnection.close(con, stmt, srs);
            }
        }
    }
    
    /**
     * ResultSet Type : TYPE_FORWARD_ONLY and CONCUR_UPDATABLE
     * updateRow  Method and cancelRowUpdates Method
     */
    public static class Tester5 {
        public static void main( String args[] ){
            Connection conn = DriverManagerConnection.getConnection();
            Statement stmt = null ;
            ResultSet uprs = null;
            
            try{
                stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE);
                uprs = stmt.executeQuery("select COF_Name, PRICE from COFFEES " +
                    "where sup_id=101");
                    //"where price = 7.99");
                
                // 1008 to indicate ResultSet.CONCUR_UPDATABLE 
                int concurrency = uprs.getConcurrency();
                System.out.println("uprs.getConcurrency()=" + concurrency);
                
                uprs.next();
                uprs.updateString("COF_NAME", "Foldgers");
                uprs.updateRow();
                
                //uprs.last();
                uprs.updateDouble("PRICE", 10.79);
                uprs.updateRow();
                uprs.updateDouble("PRICE", 10.99);
                uprs.cancelRowUpdates();
                
                // throw java.lang.NoClassDefFoundError: oracle/gss/util/NLSLocale 
                /*uprs.updateFloat("PRICE", 10.79f);
                uprs.updateRow();
                uprs.updateFloat("PRICE", 10.99f);
                uprs.cancelRowUpdates();*/
                
                /*uprs.previous();
                uprs.updateFloat("PRICE", 9.79f);
                uprs.updateRow();*/
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                DriverManagerConnection.close(conn, stmt, uprs);
            }
        }
    }
    /*
    1007 to indicate ResultSet.CONCUR_READ_ONLY 
    1008 to indicate ResultSet.CONCUR_UPDATABLE 
    *///~
    
    /**
     * ResultSet Type : TYPE_SCROLL_SENSITIVE and CONCUR_UPDATABLE
     * moveToInsertRow Method
     * column name or the column number 
     */
    public static class Tester6 {
        public static void main( String args[] ){
            Connection conn = DriverManagerConnection.getConnection();
            Statement stmt = null ;
            ResultSet uprs = null;
            
            try{
                stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE, 
                    ResultSet.CONCUR_UPDATABLE);
                //ResultSet uprs = stmt.executeQuery("SELECT * FROM COFFEES"); // error
                uprs = stmt.executeQuery(
                    "SELECT COF_NAME, SUP_ID, PRICE, SALES, TOTAL FROM COFFEES");
                System.out.println("before-----------------------");
                while (uprs.next()) {
                    String name = uprs.getString("COF_NAME");
                    int supId = uprs.getInt("SUP_ID");
                    float price = uprs.getFloat("PRICE");
                    int sales = uprs.getInt("SALES");
                    int total = uprs.getInt("TOTAL");
                    System.out.println(name + "     " + supId + "     " + price + "     " + sales + "     " + total);
                }

                uprs.moveToInsertRow();

                // column name
                /*uprs.updateString("COF_NAME", "Kona");
                uprs.updateInt("SUP_ID", 150);
                uprs.updateDouble("PRICE", 10.99f);
                uprs.updateInt("SALES", 0);
                uprs.updateInt("TOTAL", 0);*/
                // or the column number 
                uprs.updateString(1, "Kona");
                uprs.updateInt(2, 150);
                uprs.updateDouble(3, 10.99f);
                uprs.updateInt(4, 0);
                uprs.updateInt(5, 0);
                
                uprs.insertRow();
                
                System.out.println("after-----------------------");
                uprs = stmt.executeQuery(
                "SELECT COF_NAME, SUP_ID, PRICE, SALES, TOTAL FROM COFFEES");
                while (uprs.next()) {
                    String name = uprs.getString("COF_NAME");
                    int supId = uprs.getInt("SUP_ID");
                    float price = uprs.getFloat("PRICE");
                    int sales = uprs.getInt("SALES");
                    int total = uprs.getInt("TOTAL");
                    System.out.println(name + "     " + supId + "     " + price + "     " + sales + "     " + total);
                }/**/
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                DriverManagerConnection.close(conn, stmt, uprs);
            }
        }
    }
    
    /**
     * ResultSet Type : TYPE_SCROLL_SENSITIVE and CONCUR_UPDATABLE
     * deleteRow Method
     */
    public static class Tester7 {
        public static void main( String args[] ){
            Connection conn = DriverManagerConnection.getConnection();
            Statement stmt = null ;
            ResultSet uprs = null;
            
            try{
                stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE, 
                    ResultSet.CONCUR_UPDATABLE);
                //ResultSet uprs = stmt.executeQuery("SELECT * FROM COFFEES"); // error
                uprs = stmt.executeQuery(
                    "SELECT COF_NAME, SUP_ID, PRICE, SALES, TOTAL FROM COFFEES");
                System.out.println("before-----------------------");
                while (uprs.next()) {
                    String name = uprs.getString("COF_NAME");
                    int supId = uprs.getInt("SUP_ID");
                    float price = uprs.getFloat("PRICE");
                    int sales = uprs.getInt("SALES");
                    int total = uprs.getInt("TOTAL");
                    System.out.println(name + "     " + supId + "     " + price + "     " + sales + "     " + total);
                }

                uprs.absolute(4);
                uprs.deleteRow();
                
                System.out.println("after-----------------------");
                uprs = stmt.executeQuery(
                "SELECT COF_NAME, SUP_ID, PRICE, SALES, TOTAL FROM COFFEES");
                while (uprs.next()) {
                    String name = uprs.getString("COF_NAME");
                    int supId = uprs.getInt("SUP_ID");
                    float price = uprs.getFloat("PRICE");
                    int sales = uprs.getInt("SALES");
                    int total = uprs.getInt("TOTAL");
                    System.out.println(name + "     " + supId + "     " + price + "     " + sales + "     " + total);
                }/**/
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                DriverManagerConnection.close(conn, stmt, uprs);
            }
        }
    }

}
