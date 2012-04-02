package safe;

import java.io.IOException;
import java.io.Writer;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.SQLException;

import oracle.jdbc.OracleTypes;
import oracle.sql.BLOB;
import oracle.sql.CLOB;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import util.ConnectionUtil;

/**
 * 生成大字段对象的工具类 
 * 参考：http://www.rakeshv.org/docs/oracle/jdbc3.0/oracle/sql/CLOB.html 
 * 
 * @author yangwm in Feb 5, 2009 9:33:47 AM
 */
public class LobUtil {
    private static Log logger = LogFactory.getLog(LobUtil.class);
   
    /**
     * 生成空Clob对象
     * 
     * create by yangwm in Feb 5, 2009 9:33:47 AM
     * @param conn
     * @return 返回Clob对象
     * @throws SQLException
     */
    public static Clob createTemporaryClob(Connection conn)throws SQLException {
        CallableStatement cstm = null;
        try {
            cstm = conn.prepareCall("{call dbms_lob.createTemporary(?, false, dbms_lob.SESSION)}");
            cstm.registerOutParameter(1, OracleTypes.CLOB);
            cstm.execute();
            return (Clob)cstm.getClob(1);
        } finally {
            ConnectionUtil.close(cstm);
        }
    }

    /**
     * 生成空Blob对象
     * 
     * create by yangwm in Feb 5, 2009 9:33:47 AM
     * @param conn
     * @return 返回Blob对象
     * @throws SQLException
     */
    public static Blob createTemporaryBlob(Connection conn) throws SQLException {
        CallableStatement cstm = null;
        try {
            cstm = conn.prepareCall("{call dbms_lob.createTemporary(?, false, dbms_lob.SESSION)}");
            cstm.registerOutParameter(1, OracleTypes.BLOB);
            cstm.execute();
            return (Blob)cstm.getBlob(1);
        } finally {
            ConnectionUtil.close(cstm);
        }
    }
    
    /**
     * 生成以blobData为数据的Blob对象
     * 
     * create by yangwm in Feb 5, 2009 9:33:47 AM
     * @param conn
     * @param blobData
     * @return 返回Blob对象
     * @throws Exception
     */
    public static Blob createTemporaryBlob(Connection conn, byte[] blobData) throws SQLException {
        logger.debug(">>>createTemporaryBlob(byte[] blobData)");
        BLOB tempLob = null;
        try{
            tempLob = BLOB.createTemporary(conn, true, BLOB.DURATION_SESSION);
            tempLob.setBytes(1, blobData);
        } finally {
            // TODO 
        }
        return tempLob;
    }

    /**
     * 生成以clobData为数据的Blob对象
     * 
     * create by yangwm in Feb 5, 2009 9:33:47 AM
     * @param conn
     * @param clobData
     * @return 返回Clob对象
     * @throws Exception
     */
    public static Clob createTemporaryClob(Connection conn, String clobData) throws SQLException {
        logger.debug(">>>createTemporaryClob(String clobData)");
        CLOB tempLob = null;
        try{
            tempLob = CLOB.createTemporary(conn, true, CLOB.DURATION_SESSION);
            tempLob.setString(1, clobData);
            
            /*Writer tempClobWriter = tempLob.setCharacterStream(1);
            tempClobWriter.write(clobData);
            tempClobWriter.close();
        } catch (IOException e) {
            tempLob.freeTemporary();
            e.printStackTrace();*/
            /*
            tempLob.open(CLOB.MODE_READWRITE);
            Writer tempClobWriter = tempLob.getCharacterOutputStream();
            tempClobWriter.write(clobData);
            tempClobWriter.close();
        } catch (IOException e) {
            tempLob.freeTemporary();
            e.printStackTrace();*/
        } finally {
            
        }
        return tempLob;
    }

}
