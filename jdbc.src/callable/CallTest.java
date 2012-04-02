package callable;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.ConnectionUtil;

public class CallTest {
	 public static void main( String args[] ){
	     Connection conn = null;
	     CallableStatement cs = null;

	     try{
	        conn = ConnectionUtil.getConnection();
	        conn.setAutoCommit(false);
	    	String s3 = "{ call cmcc_ehr_pfmc.calc_evaluate(?,?,?,?)}";
	        cs = conn.prepareCall(s3);
	        cs.setLong(1, 684);
	        cs.registerOutParameter(2, 2);
            cs.registerOutParameter(3, 12);
            cs.registerOutParameter(4, -10);
            cs.execute();
            int flag = -1;
            flag = cs.getInt(2);
            System.out.println("flag=" + flag);
            String msg = cs.getString(3);
            System.out.println("msg=" + msg);
            
            ResultSet rs = (ResultSet)cs.getObject(4);
            System.out.println("rs=" + rs);
            if (null != rs) {
                for(int i = 0; rs.next(); i++) {
                    System.out.println("rs[" + i + "]=" + rs.getString(1));
                }
            }
	        cs.execute();
	        conn.commit();
	     }catch(Exception e){
	         try {
                conn.rollback();
	         } catch (SQLException e1) {
                e1.printStackTrace();
	         }
	         e.printStackTrace();
	     }finally{
	         ConnectionUtil.close(conn, cs);
	     }
	 }
}
