
/**
 * Copyright 2007 ultrapower All right reserved. 
 * 
 */

package safe;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import util.ConnectionUtil;

/**
 * Oracle安全基线检查  
 * @author yangwm  in Dec 15, 2008  11:29:14 AM
 *
 */
public class OracleSecurityBaseLineCheck {
    /**
     * 环境中是否使用了spfile 
     */
    public final static boolean useSpfile;
    
    /**
     * 初始化系统运行依赖的参数 
     */
    static {
        useSpfile = decideUseSpfile();
    }
    
    /**
     * 查看环境spfile的值是否为空 
     * create by yangwm in Dec 16, 2008 11:06:55 AM 
     * @return 不为空返回true, 否则返回false 
     */
    public static boolean decideUseSpfile() {
        System.out.println(">>>OracleSecurityBaseLineCheck decideUseSpfile()");
        boolean spfileNotNull = false;
        Connection conn = null;
        Statement stm = null ;
        ResultSet rs = null;
        try{
            conn = ConnectionUtil.getConnection();
            stm = conn.createStatement();
            /*
             *  show parameter spfile  
             */
            String sql = " select value from v$parameter where name like '%spfile%' ";
            rs = stm.executeQuery( sql );
            if ( rs.next() ){
                String value = rs.getString( "value" );
                //System.out.println( value );
                if (null != value && !"".equals(value.trim())) {
                    spfileNotNull = true;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            ConnectionUtil.close(conn, stm, rs);
        }
        System.out.println("<<<OracleSecurityBaseLineCheck decideUseSpfile spfileNotNull=" + spfileNotNull);
        return spfileNotNull;
    }
    
    /**
     * 判断spparameter在v$spparameter是否生效 
     * create by yangwm in Dec 16, 2008 11:18:05 AM
     * @param parameter 检查参数 
     * @return 不为空返回true , 否则返回false
     */
    public static boolean decideSpparameterEffect(String spparameter) {
        System.out.println(">>>OracleSecurityBaseLineCheck decideSpparameterEffect(" + spparameter + ")");
        boolean parameterEffect = false;
        Connection conn = null;
        Statement stm = null ;
        ResultSet rs = null;
        try{
            conn = ConnectionUtil.getConnection();
            stm = conn.createStatement();
            /*
             * 先判断该项是否在spfile中有设置 
             */
            String sql = " select count(*) from v$spparameter where value is not NULL and isspecified='TRUE' and name like '%remote_login_passwordfile%' ";
            rs = stm.executeQuery( sql );
            if ( rs.next() ){
                long num = rs.getLong( 1);
                //System.out.println( num );
                if (num > 0) {
                    parameterEffect = true;
                } else {
                    parameterEffect = false;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            ConnectionUtil.close(conn, stm, rs);
        }
        System.out.println("<<<OracleSecurityBaseLineCheck decideSpparameterEffect parameterEffect=" + parameterEffect);
        return parameterEffect;
    }
    
    /**
     * 检查参数REMOTE_LOGIN_PASSWORDFILE是否设置为NONE 
     * 
     * create by yangwm in Dec 15, 2008 11:28:37 AM
     */
    public static void Configuration3Check() {
        Connection conn = null;
        Statement stm = null ;
        ResultSet rs = null;
        try{
            conn = ConnectionUtil.getConnection();
            stm = conn.createStatement();
            
            /*
             *  环境中是否使用了spfile且"remote_login_passwordfile"在v$spparameter是否生效
             *  如果生效查询v$spparameter， 否则查询v$parameter ( show parameter remote_login_passwordfile )。
             */
            if (useSpfile && decideSpparameterEffect("remote_login_passwordfile") ) {
                String sql = " select name, value from v$spparameter where name like '%remote_login_passwordfile%' ";
                rs = stm.executeQuery( sql );
                while ( rs.next() ){
                    String name = rs.getString( "name" );
                    String value = rs.getString( "value" );
                    System.out.println( name + ", " + value);
                }
            } else {
                String sql = " select name, value from v$parameter where name like '%remote_login_passwordfile%' " ;
                rs = stm.executeQuery( sql );
                while ( rs.next() ){
                    String name = rs.getString( "name" );
                    String value = rs.getString( "value" );
                    System.out.println( name + ", " + value);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            ConnectionUtil.close(conn, stm, rs);
        }
    }
    

    /**
     * create by yangwm in Dec 15, 2008 11:28:06 AM
     * @param args
     */
    public static void main(String[] args) {
        Configuration3Check();
    }

}
