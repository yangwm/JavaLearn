/**
 * 
 */
package dbutilsi;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.PageArgument;
import org.apache.commons.dbutils.ResultSetHandler;

/**
 * 结果集到书业务对象封装类 
 * 
 * @author yangwm in May 26, 2009 1:39:41 PM
 */
public class BookHandler implements ResultSetHandler {

    /* (non-Javadoc)
     * @see org.apache.commons.dbutils.ResultSetHandler#handle(java.sql.ResultSet)
     */
    public Object handle(ResultSet rs) throws SQLException {
        return handle(rs, null);
    }

    public Object handle(ResultSet rs, PageArgument pageArg)
            throws SQLException {
        Book bean = null;
        if (rs.next()) {
            bean = new Book();
            
            bean.setId(rs.getLong("id"));
            bean.setTitle(rs.getString("title"));
            bean.setAuthors(rs.getString("authors"));
        }
        return bean;
    }

}
