package result.meta;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import util.ConnectionUtil;

/**
 * 
 * @author yangwm in Dec 12, 2008 11:56:44 AM
 *
 */
public class ResultSetMetaDataTest {

    /**
     * create by yangwm in Dec 12, 2008 11:56:44 AM
     * @param args
     */
    public static void main(String[] args) {
        Connection conn = null;
        Statement stm = null ;
        ResultSet rs = null;
        
        try{
            conn = ConnectionUtil.getConnection();
            stm = conn.createStatement();
            //String sqlSelect = " select * from v$parameter where name like '%pool%' " ;
            String sqlSelect = " select * from v$session where username is not null order by logon_time, sid " ;
            rs = stm.executeQuery( sqlSelect );
            
            ResultSetMetaData rsmd =  rs.getMetaData();
            int numberOfColumns = rsmd.getColumnCount();
            System.out.println("numberOfColumns=" + numberOfColumns);

            while(rs.next()){
                StringBuffer resultString=new StringBuffer();
                for(int i=0;i<numberOfColumns;i++){                     
                    if(i<numberOfColumns-1){
                        resultString.append(rs.getObject(i+1)).append(";");  
                    }else{                          
                        resultString.append(rs.getObject(i));
                    }
                }
                System.out.println("resultString " + rs.getRow() + "=" + resultString);
            }
            //Thread.sleep(10*1000);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            ConnectionUtil.close(conn, stm, rs);
        }
    }

}
