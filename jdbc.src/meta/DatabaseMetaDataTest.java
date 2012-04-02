/**
 * 
 */
package meta;

import java.sql.Connection;
import java.sql.DatabaseMetaData;

import util.ConnectionUtil;

/**
 * @author yangwm in Nov 27, 2009 5:58:56 PM
 */
public class DatabaseMetaDataTest {

    /**
     * create by yangwm in Nov 27, 2009 5:58:56 PM
     * @param args
     */
    public static void main(String[] args) {
        Connection conn = null;
        try{
            conn = ConnectionUtil.getConnection();
            DatabaseMetaData databaseMetaData = conn.getMetaData();
            System.out.println(databaseMetaData.getDriverName());
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            ConnectionUtil.close(conn);
        }
    }

}
