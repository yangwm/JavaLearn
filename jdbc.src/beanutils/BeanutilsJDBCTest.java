package beanutils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.ResultSetDynaClass;
import org.apache.commons.beanutils.RowSetDynaClass;

import util.ConnectionUtil;

public class BeanutilsJDBCTest {

    /**
     * 其实在利用ResultSetDynaClass时，必须在ResultSet等数据库资源关闭之前，处理好那些数据，
     * 你不能在资源关闭之后使用DynaBean，否则就会抛出异常
     * 
     * @author yangwm in Mar 1, 2009 9:19:07 PM
     */
    public static class ResultSetDynaClassTester {
        public static void main(String[] args) {
            Connection conn = null;
            Statement st = null;
            ResultSet rs = null;
            try {
                conn = ConnectionUtil.getConnection();
                st = conn.createStatement();
                rs = st.executeQuery("select * from book");

                ResultSetDynaClass rsdc = new ResultSetDynaClass(rs);
                Iterator<DynaBean> itr = rsdc.iterator();
                System.out.println("title-------------authors");
                while (itr.hasNext()) {
                    DynaBean dBean = itr.next();
                    System.out.println(PropertyUtils.getSimpleProperty(dBean, "title")
                                    + "-------------"
                                    + PropertyUtils.getSimpleProperty(dBean, "authors"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                ConnectionUtil.close(conn, st, rs);
            }
        }
    }

    /**
     * 利用RowSetDynaClass可以很好的解决上述ResultSetDynaClass遇到的问题
     * 
     * @author yangwm in Mar 1, 2009 9:25:58 PM
     */
    public static class RowSetDynaClassTester {
        public static void main(String[] args) {
            try {
                Iterator<DynaBean> itr = getDynaBeans().iterator();
                System.out.println("title-------------authors");
                while (itr.hasNext()) {
                    DynaBean dBean = itr.next();
                    System.out.println(PropertyUtils.getSimpleProperty(dBean, "title")
                                    + "-------------"
                                    + PropertyUtils.getSimpleProperty(dBean, "authors"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        private static List<DynaBean> getDynaBeans() {
            Connection conn = null;
            Statement st = null;
            ResultSet rs = null;
            try {
                conn = ConnectionUtil.getConnection();
                st = conn.createStatement();
                rs = st.executeQuery("select * from book");

                RowSetDynaClass rsdc = new RowSetDynaClass(rs);
                
                return rsdc.getRows();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                ConnectionUtil.close(conn, st, rs);
            }
            return null;
        }
    }
    
}

/*
 * create table book( id number(11) NOT NULL, title varchar2(50) NOT NULL,
 * authors varchar2(50), primary key (id) ) tablespace ams;
 * 
 * create sequence seq_book;
 * 
 * create trigger tri_book before insert on book for each row begin select
 * seq_book.nextval into :new.id from dual; end;
 * 
 * insert into book(title, authors) values('c++ primer plus', 'duck') ; insert
 * into book(title, authors) values('thinking in java', 'bruce eckel') ;
 * 
 */
