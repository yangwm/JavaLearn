package safe;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import util.ConnectionUtil;

/**
 * 
 * @author yangwm   in Dec 15, 2008 5:51:10 PM
 *
 */
public class OracleSecurityBaseLineTest {
    
    //   账号 --------------------------------------------------- 

    /**
     *  select username, password, account_status from dba_users 
     * 
     * @author yangwm   in Dec 15, 2008 5:51:37 PM
     *
     */
    public static class Configuration1 {
        /**
         * 
         * create by yangwm in Dec 15, 2008 5:52:26 PM
         * @param args
         */
        public static void main( String args[] ){
            Connection conn = null;
            Statement stm = null ;
            ResultSet rs = null;
            try{
                conn = ConnectionUtil.getConnection();
                stm = conn.createStatement();
                /*
                 *  select * from dba_users 
                 *  select username, password, account_status from dba_users 
                 */
                String sql = " select username, password, account_status from dba_users " ;
                rs = stm.executeQuery( sql );
                while ( rs.next() ){
                    String username = rs.getString(1);
                    String password = rs.getString(2);
                    String accountStatus = rs.getString(3);
                    System.out.println( username + ", " + password + ", " + accountStatus);
                    
                }
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                ConnectionUtil.close(conn, stm, rs);
            }
        }
    }
    
    /**
     * 首先锁定不需要的用户在经过一段时间后，确认该用户对业务确无影响的情况下，可以删除
     * 
     * @author yangwm   in Dec 15, 2008 5:51:37 PM
     *
     */
    public static class Configuration2 {
        /**
         * 
         * create by yangwm in Dec 15, 2008 5:52:26 PM
         * @param args
         */
        public static void main( String args[] ){
            Connection conn = null;
            Statement stm = null ;
            ResultSet rs = null;
            try{
                conn = ConnectionUtil.getConnection();
                stm = conn.createStatement();
                /*
                 * select username, account_status from dba_users where username = 'XXX'
                 */
                String sql = " select username, account_status from dba_users where username = 'TEMPTEST' " ;
                rs = stm.executeQuery( sql );
                while ( rs.next() ){
                    String username = rs.getString( "username" );
                    String accountStatus = rs.getString( "account_status" );
                    System.out.println( username + ", " + accountStatus);
                }

                /**
                 * alter user username account lock 
                 */
                System.out.println( ">>>  alter user temptest account lock start " );
                sql = " alter user temptest account lock " ;
                rs = stm.executeQuery( sql );
                System.out.println( "<<<  alter user temptest account lock end " );
                
                sql = " select username, account_status from dba_users where username = 'TEMPTEST' " ;
                rs = stm.executeQuery( sql );
                while ( rs.next() ){
                    String username = rs.getString( "username" );
                    String accountStatus = rs.getString( "account_status" );
                    System.out.println( username + ", " + accountStatus);
                }
                
                sql = " alter user temptest account unlock " ;
                rs = stm.executeQuery( sql );
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                ConnectionUtil.close(conn, stm, rs);
            }
        }
    }
    
    /**
     * 在spfile中设置REMOTE_LOGIN_PASSWORDFILE=NONE来禁止SYSDBA用户从远程登陆。
     * 
     * 在sqlnet.ora中设置SQLNET.AUTHENTICATION_SERVICES=NONE来禁用 SYSDBA 角色的自动登录。
     * 
     * @author yangwm   in Dec 15, 2008 5:51:37 PM
     *
     */
    public static class Configuration3 {
        /**
         * 
         * create by yangwm in Dec 15, 2008 5:52:26 PM
         * 
         * @param args
         */
        public static void main( String args[] ){
            Connection conn = null;
            Statement stm = null ;
            ResultSet rs = null;
            try{
                conn = ConnectionUtil.getConnection();
                stm = conn.createStatement();
                /*
                 *  show parameter remote_login_passwordfile  
                 */
				//select * from v$spparameter where name like '%remote_login_passwordfile%'
                //String sql = " select name, type, value from v$parameter where name like '%remote_login_passwordfile%' " ;
                String sql = "select name, value from v$spparameter where name like '%remote_login_passwordfile%' ";
                rs = stm.executeQuery( sql );
                while ( rs.next() ){
                    String name = rs.getString( "name" );
                    String value = rs.getString( "value" );
                    System.out.println( name + ", " + value);
                }
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                ConnectionUtil.close(conn, stm, rs);
            }
        }
    }
    
    /**
     *  grant　权限　to username;
     *  
     *  revoke 权限　from username;
     * 
     * @author yangwm   in Dec 15, 2008 5:51:37 PM
     *
     */
    public static class Configuration8 {
        /**
         * 
         * create by yangwm in Dec 15, 2008 5:52:26 PM
         * 
         * @param args
         */
        public static void main( String args[] ){
            Connection conn = null;
            Statement stm = null ;
            ResultSet rs = null;
            try{
                conn = ConnectionUtil.getConnection();
                stm = conn.createStatement();
                /*
                 *  grant　权限　to username 
                 */
                String sql = " grant　权限　to temptest " ;
                rs = stm.executeQuery( sql );
                
                /*
                 *  revoke 权限　from username 
                 */
                sql = " revoke 权限　from temptest " ;
                rs = stm.executeQuery( sql );
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                ConnectionUtil.close(conn, stm, rs);
            }
        }
    }
    
    /**
     *  通过查询dba_role_privs、dba_sys_privs和dba_tab_privs等视图来检查是否使用ROLE来管理对象权限
     * 
     * @author yangwm   in Dec 15, 2008 5:51:37 PM
     *
     */
    public static class Configuration9 {
        /**
         * 
         * create by yangwm in Dec 15, 2008 5:52:26 PM
         * 
         * @param args
         */
        public static void main( String args[] ){
            Connection conn = null;
            Statement stm = null ;
            ResultSet rs = null;
            try{
                conn = ConnectionUtil.getConnection();
                stm = conn.createStatement();
                /*
                 * select * from dba_role_privs;
                 * select * from dba_sys_privs;
                 * select * from dba_tab_privs;
                 */
                String sql = " select grantee, granted_role, admin_option, default_role from dba_role_privs " ;
                rs = stm.executeQuery( sql );
                while ( rs.next() ){
                    //
                }
                
                sql = " select grantee, privilege, admin_option from dba_sys_privs " ;
                rs = stm.executeQuery( sql );
                while ( rs.next() ){
                    //
                }
                
                sql = " select grantee, owner, table_name, grantor, privilege, grantable, hierarchy from dba_tab_privs " ;
                rs = stm.executeQuery( sql );
                while ( rs.next() ){
                    //
                }
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                ConnectionUtil.close(conn, stm, rs);
            }
        }
    }
    
    /**
     *  查询视图dba_profiles和dba_users来检查profile是否创建
     * 
     * @author yangwm   in Dec 15, 2008 5:51:37 PM
     *
     */
    public static class Configuration10 {
        /**
         * 
         * create by yangwm in Dec 15, 2008 5:52:26 PM
         * 
         * @param args
         */
        public static void main( String args[] ){
            Connection conn = null;
            Statement stm = null ;
            ResultSet rs = null;
            try{
                conn = ConnectionUtil.getConnection();
                stm = conn.createStatement();
                /*
                 * select * from dba_profiles;
                 * select * from dba_users;
                 */
                String sql = " select profile, resource_name, resource_type, limit from dba_profiles where profile = 'DEFAULT' " ;
                rs = stm.executeQuery( sql );
                while ( rs.next() ){
                    String profile = rs.getString( "profile" );
                    String resourceName = rs.getString( "resource_name" );
                    String resourceType = rs.getString( "resource_type" );
                    String limit = rs.getString( "limit" );
                    System.out.println( profile + ", " + resourceName + ", " + resourceType + ", " + limit);
                }
                
                sql = " select count(*) from dba_users where username='temptest' and profile='DEFAULT' " ;
                rs = stm.executeQuery( sql );
                while ( rs.next() ){
                    long num = rs.getLong(1);
                    System.out.println(num);
                }
                
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                ConnectionUtil.close(conn, stm, rs);
            }
        }
    }
    
   /**
    * 以普通dba用户登陆到数据库，不能查看X$开头的表，比如：select * from sys. x$ksppi;
    * 
    * 使用show parameter命令来检查参数O7_DICTIONARY_ACCESSIBILITY是否设置为FALSE。
    * 
    * @author yangwm   in Dec 15, 2008 5:51:37 PM
    *
    */
   public static class Configuration13 {
       /**
        * 
        * create by yangwm in Dec 15, 2008 5:52:26 PM
        * 
        * @param args
        */
       public static void main( String args[] ){
           Connection conn = null;
           Statement stm = null ;
           ResultSet rs = null;
           try{
               conn = ConnectionUtil.getConnection();
               stm = conn.createStatement();

               /*
                * show parameter O7_DICTIONARY_ACCESSIBILITY 
                */
               String sql = " select name, type, value from v$parameter where name like '%O7_DICTIONARY_ACCESSIBILITY%' " ;
               rs = stm.executeQuery( sql );
               while ( rs.next() ){
                   String name = rs.getString( "name" );
                   String type = rs.getString( "type" );
                   String value = rs.getString( "value" );
                   System.out.println( name + ", " + type + ", " + value);
               }
               
               /*
                *  select * from sys.x$ksppi 抛出 java.sql.SQLException: ORA-00942: table or view does not exist ；
                *  表示不能访问RDBMS(X$)表。 
                */
               sql = " select * from sys.x$ksppi " ;
               String msg = "";
               try {
                   rs = stm.executeQuery( sql );
               }catch(SQLException e){
                   msg = e.getMessage();
               }
               System.out.print(msg);
               if ( msg.indexOf("table or view does not exist") > 0 ) {
                   System.out.print("不能访问RDBMS(X$)表");
               } else {
                   System.out.print("能访问RDBMS(X$)表");
               }
               
           }catch(Exception e){
               e.printStackTrace();
           }finally{
               ConnectionUtil.close(conn, stm, rs);
           }
       }
   }
   
   
   // 口令 --------------------------------------------------- 
   
   /**
    *  alter user abcd1 identified by abcd1;将失败 
    * 
    * @author yangwm   in Dec 15, 2008 5:51:37 PM
    *
    */
   public static class Configuration4 {
       /**
        * 
        * create by yangwm in Dec 15, 2008 5:52:26 PM
        * 
        * @param args
        */
       public static void main( String args[] ){
           Connection conn = null;
           Statement stm = null ;
           ResultSet rs = null;
           try{
               conn = ConnectionUtil.getConnection();
               /*
                * select * from dba_profiles;
                * select * from dba_users;
                */
               stm = conn.createStatement();
               String sql = "  " ;
               rs = stm.executeQuery( sql );
               while ( rs.next() ){
                   // 
               }
           }catch(Exception e){
               e.printStackTrace();
           }finally{
               ConnectionUtil.close(conn, stm, rs);
           }
       }
   }
   
   /**
    *  以DBA用户检查数据库默认账户是否使用了用户名作为密码或默认密码  
    * 
    * @author yangwm   in Dec 15, 2008 5:51:37 PM
    *
    */
   public static class Configuration11 {
       /**
        * 
        * create by yangwm in Dec 15, 2008 5:52:26 PM
        * 
        * @param args
        */
       public static void main( String args[] ){
           Connection conn = null;
           Statement stm = null ;
           ResultSet rs = null;
           try{
               conn = ConnectionUtil.getConnection();
               stm = conn.createStatement();
               String sql = "  " ;
               rs = stm.executeQuery( sql );
               while ( rs.next() ){
                   // 
               }
           }catch(Exception e){
               e.printStackTrace();
           }finally{
               ConnectionUtil.close(conn, stm, rs);
           }
       }
   }
   

   // 其他----------------------------------------------------------
   
   
   /**
    *  查询视图v$vpd_policy 和dba_policies。
    * 
    * @author yangwm   in Dec 15, 2008 5:51:37 PM
    *
    */
   public static class Configuration14 {
       /**
        * 
        * create by yangwm in Dec 15, 2008 5:52:26 PM
        * 
        * @param args
        */
       public static void main( String args[] ){
           Connection conn = null;
           Statement stm = null ;
           ResultSet rs = null;
           try{
               conn = ConnectionUtil.getConnection();
               stm = conn.createStatement();
               String sql = " select * from v$vpd_policy " ;
               rs = stm.executeQuery( sql );
               while ( rs.next() ){
                   String username = rs.getString(1);
                   String password = rs.getString(2);
                   String accountStatus = rs.getString(3);
                   System.out.println( username + ", " + password + ", " + accountStatus);
                   
               }
           }catch(Exception e){
               e.printStackTrace();
           }finally{
               ConnectionUtil.close(conn, stm, rs);
           }
       }
   }
   
   /**
    *  select count(*) from dba_users where username='DVSYS';
    *  select count(*) from dba_objects where object_name like '%DBMS_MACADM%'
    * 
    * @author yangwm   in Dec 15, 2008 5:51:37 PM
    *
    */
   public static class Configuration15 {
       /**
        * 
        * create by yangwm in Dec 15, 2008 5:52:26 PM
        * 
        * @param args
        */
       public static void main( String args[] ){
           Connection conn = null;
           Statement stm = null ;
           ResultSet rs = null;
           try{
               conn = ConnectionUtil.getConnection();
               stm = conn.createStatement();
               String sql = " select count(*) from dba_users where username='DVSYS' " ;
               rs = stm.executeQuery( sql );
               if ( rs.next() ){
                   long num = rs.getLong(1);
                   System.out.println(num);
               }
               
               sql = " select count(*) from dba_objects where object_name like '%DBMS_MACADM%' " ;
               rs = stm.executeQuery( sql );
               if ( rs.next() ){
                   long num = rs.getLong(1);
                   System.out.println(num);
               }
           }catch(Exception e){
               e.printStackTrace();
           }finally{
               ConnectionUtil.close(conn, stm, rs);
           }
       }
   }
   
}
