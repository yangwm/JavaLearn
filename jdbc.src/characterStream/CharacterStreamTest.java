package characterStream;


import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import util.ConnectionUtil;

public class CharacterStreamTest {
	 public static void main( String args[] ){
	     Connection conn = null;
	     PreparedStatement pstm = null;
	     Statement stm = null ;
	     ResultSet rs = null;
	     
	     try{
	         long ie = Long.parseLong("3i");
	         //throw new NumberFormatException();
	         StringBuilder message = new StringBuilder("12345");
//	         for (int i = 0; i < 110; i++) {
//	             message.append("abcdefghijklmnopqrstuvwxyz");
//	         }
	         message.append("数据库 Oracle Database 10g Express Edition 入门");
	         byte[] messageBytes = message.toString().getBytes();
	         int messageBytesLength = messageBytes.length;
	         System.out.println("messageBytesLength=" + messageBytesLength);
	         System.out.println("messageLength=" + message.toString().length());
	         InputStreamReader isr = new InputStreamReader(new java.io.ByteArrayInputStream(messageBytes));
	         String sqlUpdate = "update cper.pfmc_plan_item " +
				" set name = ?, method = ? " +
				" where id = ? ";
	         
	         // 执行 SQL ------> PreparedStatement 
	         conn = ConnectionUtil.getConnection();
	         pstm = conn.prepareStatement(sqlUpdate);
	         pstm.setString(1, "工作目标1gai3Two");
	         
//             使用该语句 pstm.setString(2, message.toString()); 所插入的字符串不能大于2000(用的数据库环境为Oracle 9i,如果用的数据库环境为Oracle 10g不能大于4000).
//             如果大于2000,可能会抛出java.sql.SQLException: Data size bigger than max size for this type: 2001
//             如果使用该语句 pstm.setCharacterStream(2, isr, messageLength);
	         pstm.setCharacterStream(2, isr, messageBytesLength);
	         pstm.setLong(3, 1382);
	         
//	         pstm.addBatch();
//	         pstm.executeBatch();

	         int r = pstm.executeUpdate();
	         System.out.println( r + " rows effected" );
	         
	         // 执行 SQL ------> Statement
	         stm = conn.createStatement();
	         String sqlSelect = "select method m from cper.pfmc_plan_item where id=1382" ;
	         rs = stm.executeQuery( sqlSelect );
	         if ( rs.next() ){
	             String mess = rs.getString( "m" );
	             System.out.println( "=======================" );
	             System.out.println( "message: " + mess );
	             System.out.println( "=======================" );
	             System.out.println( "message.length() " + mess.length() );
	         }
	     }catch(SQLException e){
	         e.printStackTrace();
	     }finally{
	         //5, 释放数据资源.
	         ConnectionUtil.close(conn, stm, pstm, rs);
	     }
	     
	 }
}

/*
create table yusers ( 
 id            number primary key ,
 username    varchar2(50),
 password    varchar2(20), 
 phone        varchar2(20),
 email        varchar2(50),
 message        varchar2(4000)
);

create sequence yusers_seq;
insert into yusers(id,username,password,phone,email,message) values(yusers_seq.nextval,'yangwm','12345','12345678','jxfzywm@163.com','Oracle Database 10g Express Edition 入门'); 
*/
