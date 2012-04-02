package extra.jdbc.basics;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DataSourceConnection {

    /**
     * @param args
     * @throws NamingException 
     * @throws SQLException 
     */
    public static void main(String[] args) throws NamingException, SQLException {
        InitialContext ic = new InitialContext();
        DataSource ds = (DataSource) ic.lookup("java:comp/env/jdbc/myDB");
        Connection con = ds.getConnection();
        System.out.println("con=" + con);
        
        /*DataSource ds = (DataSource) org.apache.derby.jdbc.ClientDataSource();
        ds.setPort(1527);
        ds.setHost("localhost");
        ds.setUser("APP")
        ds.setPassword("APP");
        Connection con = ds.getConnection();*/
        
        /*VendorDataSource vds = new VendorDataSource();
        vds.setServerName("my_database_server");
        String name = vds.getServerName();*/

    }

}
