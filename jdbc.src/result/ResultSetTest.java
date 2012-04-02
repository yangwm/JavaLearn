package result;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import util.ConnectionUtil;

public class ResultSetTest {

    public static class SysTester {
        public static void main( String args[] ){
            //for (int i = 0; i < 50; i++) {
                Connection conn = null;
                Statement stm = null ;
                ResultSet rs = null;
                try{
                    conn = ConnectionUtil.getConnection();
                    stm = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                    String sqlSelect = " select account_status from dba_users " ;   // view sys.dba_users --> table sys.user$
                    rs = stm.executeQuery( sqlSelect );
                    rs.last();
                    System.out.println(rs.getRow());
                    
                    rs.beforeFirst();   
                    System.out.println(rs.getRow());
                    while ( rs.next() ){
                        String accountStatus = rs.getString("account_status");
                        System.out.println(accountStatus);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }finally{
                    ConnectionUtil.close(conn, stm, rs);
                }
            //}
            
        }
    }
    
    public static class Tester1 {
        public static void main( String args[] ){
            Connection conn = null;
            Statement stm = null ;
            ResultSet rs = null;
            try{
                conn = ConnectionUtil.getConnection();
                stm = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
                String sqlSelect = "select id, name from cper.pfmc_plan_item where id < 10868 " ;
                rs = stm.executeQuery( sqlSelect );
                while ( rs.next() ){
                    Long id = rs.getLong("id");
                    String name = rs.getString("name");
                    System.out.println( id + ": " + name);
                }
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                ConnectionUtil.close(conn, stm, rs);
            }
        }
    }
    
    public static class Tester2 {
        public static void main( String args[] ){
            Connection conn = null;
            Statement stm = null ;
            ResultSet rs = null;
            try{
                conn = ConnectionUtil.getConnection();
                stm = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                String sqlSelect = "select id, name from cper.pfmc_plan_item where id < 10868 " ;
                rs = stm.executeQuery( sqlSelect );
                System.out.println("rs.getRow(): " + rs.getRow());
//                rs.afterLast();
//                System.out.println("rs.getRow(): " + rs.getRow());
//                rs.last();
//                System.out.println("rs.getRow(): " + rs.getRow());
                rs.absolute(4);
                System.out.println("rs.getRow(): " + rs.getRow());
                while (rs.previous()){
                    Long id = rs.getLong("id");
                    String name = rs.getString("name");
                    System.out.println( id + ": " + name);
                }
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                ConnectionUtil.close(conn, stm, rs);
            }
        }
    }
    
    public static class Tester3 {
        public static void main( String args[] ){
            Connection conn = null;
            Statement stm = null ;
            ResultSet rs = null;
            try{
                conn = ConnectionUtil.getConnection();
                stm = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                String sqlSelect = "select id, name from cper.pfmc_plan_item where id < 10868 " ;
                rs = stm.executeQuery( sqlSelect );
                System.out.println("rs.getRow(): " + rs.getRow());
                rs.absolute(-1);
                System.out.println("rs.getRow(): " + rs.getRow());
                while (rs.previous()) {
                    Long id = rs.getLong("id");
                    String name = rs.getString(2);
                    System.out.println( id + ": " + name);
                }
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                ConnectionUtil.close(conn, stm, rs);
            }
        }
    }
    
    public static class Tester4 {
        public static void main( String args[] ){
            Connection conn = null;
            Statement stm = null ;
            ResultSet rs = null;
            try{
                conn = ConnectionUtil.getConnection();
                stm = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                String sqlSelect = "select ppi.id, ppi.name from cper.pfmc_plan_item ppi where ppi.id > 10800 and ppi.id <= 10868 " ;
                rs = stm.executeQuery( sqlSelect );
                
                System.out.println("-------before update-------");
                while (rs.next()){
                    Long id = rs.getLong("id");
                    String name = rs.getString(2);
                    System.out.println( id + ": " + name);
                }
                
                rs.beforeFirst();
                rs.last();
                rs.updateString("name", "ll1");
                rs.updateRow();
                rs.previous();
                rs.updateString("name", "ll2");
                rs.previous();
                rs.updateString("name", "ll3");
                rs.cancelRowUpdates();
                
                System.out.println("-------after update-------");
                rs.beforeFirst();
                while (rs.next()){
                    Long id = rs.getLong("id");
                    String name = rs.getString(2);
                    System.out.println( id + ": " + name);
                }
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                ConnectionUtil.close(conn, stm, rs);
            }
        }
    }
}
