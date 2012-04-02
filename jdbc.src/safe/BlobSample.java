package safe;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;

import util.ConnectionUtil;

public class BlobSample{
    public void insertBlob(String fileName){
        Connection conn = null;
        Statement stm =null;
        ResultSet rs = null;
        try{
            conn = ConnectionUtil.getConnection();
            conn.setAutoCommit(false);
            stm = conn.createStatement();
            stm.executeUpdate("insert into zip_files(id,filename,zip)"             
                              +"values(users_seq.nextval,'myfile',empty_blob())");
            System.out.println("========insert empty blob ok===========");

            rs = stm.executeQuery("select zip from zip_files where filename='myfile'");
            rs.next();
            Blob ob = rs.getBlob(1); 
            oracle.sql.BLOB oob = (oracle.sql.BLOB)ob;
            OutputStream os = oob.getBinaryOutputStream();

            InputStream is = new FileInputStream(fileName);
            byte buf[] = new byte[4096];
            int len = 0;
            
            int count=0;
            while(true){
                len = is.read(buf);
                if(len<=0) break;
                os.write(buf,0,len);
                if(count%10==0){
                     System.out.println(40960+"bytes ok!");
                }
                count++;
            }  
            is.close();
            os.close();
            conn.commit();
            System.out.println("===========insert ok=================");
        }catch(Exception e){
            if(conn!=null) try{conn.rollback();}catch(Exception e1){}
            e.printStackTrace();
        }
    }
    public void queryBlob(String name){
        Connection conn = null;
        PreparedStatement pstm=null;
        ResultSet rs = null;
        try{
            conn = ConnectionUtil.getConnection();
            conn.setAutoCommit(false);

            pstm = conn.prepareStatement("select zip from zip_files"
                                     +"where filename=? for update");
            pstm.setString(1, name);
            System.out.println(pstm.toString());
            rs = pstm.executeQuery();
  
            int count = 0;
            if(rs.next()){
                Blob b = rs.getBlob(1);
                InputStream is = b.getBinaryStream();
                OutputStream os = new FileOutputStream("e:/copy.zip");
                byte buf[] = new byte[4096];
                int len = 0;
                while(true){
                    len = is.read(buf);
                    if(len<=0) break;
                    os.write(buf,0,len);
                    if(count %10 == 0){
                         System.out.println("40960 bytes read");
                    } 
                    is.close();
                    os.close();
                    conn.commit();
                }
           }
       }catch(Exception e){
            if(conn != null)try{conn.rollback();}catch(Exception e1){}
            e.printStackTrace();
       } finally {
           ConnectionUtil.close(conn, pstm, rs);
       }
    }
    
    public static void main(String args[]){
        //new BlobSample().insertBlob("d:/jdk-6-doc.zip");
        new BlobSample().queryBlob("d:/jdk-6-doc.zip");
    }
     
}

