package warn;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLWarning;
import java.sql.Statement;

import util.ConnectionUtil;

public class WarningTest {
    public static class Tester1 {
        public static void main( String args[] ){
            Connection conn = null;
            Statement stmt = null ;
            ResultSet rs = null;
            try{
                conn = ConnectionUtil.getConnection();
                stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
                String sqlSelect = "select id, name from cper.pfmc_plan_item where id < 10868 " ;
                rs = stmt.executeQuery( sqlSelect );
                while ( rs.next() ){
                    Long id = rs.getLong("id");
                    String name = rs.getString("name");
                    System.out.println( id + ": " + name);
                    
                    // warnings 
                    SQLWarning warning = stmt.getWarnings();
                    if (warning != null) {
                        System.out.println("\n---Warning---\n");
                        while (warning != null) {
                            System.out.println("Message: " + warning.getMessage());
                            System.out.println("SQLState: " + warning.getSQLState());
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
                            System.out.println("Message: " + warn.getMessage());
                            System.out.println("SQLState: " + warn.getSQLState());
                            System.out.print("Vendor error code: ");
                            System.out.println(warn.getErrorCode());
                            System.out.println("");
                            warn = warn.getNextWarning();
                        }
                    }

                }
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                ConnectionUtil.close(conn, stmt, rs);
            }
        }
    }
}
