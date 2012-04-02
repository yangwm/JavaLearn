/**
 * 
 */
package dbutilsi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.PageArgument;
import org.apache.commons.dbutils.ResultSetHandler;

/**
 * 结果集到书业务对象列表封装类 
 * 
 * @author yangwm in May 25, 2009 9:38:21 PM
 */
public class BookListHandler implements ResultSetHandler {

    /* (non-Javadoc)
     * @see org.apache.commons.dbutils.ResultSetHandler#handle(java.sql.ResultSet)
     */
    public Object handle(ResultSet rs) throws SQLException {
        return handle(rs, new PageArgument());
    }

    /* (non-Javadoc)
     * @see com.ultrapower.ra.common.db.MyResultSetHandler#handle(java.sql.ResultSet, com.ultrapower.ra.common.page.PageArgument)
     */
    public Object handle(ResultSet rs, PageArgument pageArg)
            throws SQLException {
        List<Book> beanList = new ArrayList<Book>();
        int i = 0;
        while (rs.next() && (i++) < pageArg.getPageSize()) {    
            Book bean = new Book();
            bean.setId(rs.getLong("id"));
            bean.setTitle(rs.getString("title"));
            bean.setAuthors(rs.getString("authors"));
            beanList.add(bean);
        }
        return beanList;
    }

}
