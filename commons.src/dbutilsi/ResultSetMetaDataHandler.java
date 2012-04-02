/**
 * 
 */
package dbutilsi;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.apache.commons.dbutils.PageArgument;
import org.apache.commons.dbutils.ResultSetHandler;

/**
 * 
 * 
 * @author yangwm Jun 22, 2010 5:34:55 PM
 */
public class ResultSetMetaDataHandler implements ResultSetHandler {

    /* (non-Javadoc)
     * @see org.apache.commons.dbutils.ResultSetHandler#handle(java.sql.ResultSet)
     */
    public Object handle(ResultSet rs) throws SQLException {
        return handle(rs, null);
    }

    /* (non-Javadoc)
     * @see org.apache.commons.dbutils.ResultSetHandler#handle(java.sql.ResultSet, org.apache.commons.dbutils.PageArgument)
     */
    public Object handle(ResultSet rs, PageArgument pageArg)
            throws SQLException {
        
        if (rs.next()) {
            StringBuilder rsmdStr = new StringBuilder();
            ResultSetMetaData rsmd = rs.getMetaData();
            int cols = rsmd.getColumnCount();
            for (int colIndex = 1; colIndex <= cols; colIndex++) {
                rsmdStr.append(colIndex);
                rsmdStr.append(":");
                rsmdStr.append(rsmd.getColumnName(colIndex));
                rsmdStr.append(":");
                rsmdStr.append(rsmd.getColumnLabel(colIndex));
                rsmdStr.append(":");
                rsmdStr.append(rs.getObject(colIndex));
                rsmdStr.append("-----");
            }
            System.out.println(rsmdStr.toString());
        }
        
        return null;
    }

}
