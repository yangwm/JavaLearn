package util;

import org.apache.commons.lang.StringUtils;

/**
 * 
 */

/**
 * sql语句帮助类 
 * 
 * @author yangwm in Nov 23, 2009 4:48:29 PM
 */
public class PageUtils {

    /** Constant identifying the Oracle jdbc driver */
    public static String ORACLE_JDBC_DRIVER = "Oracle JDBC driver";
    /** Constant identifying the MySQL jdbc driver */
    public static String MYSQL_JDBC_DRIVER = "MySQL-AB JDBC Driver";
    /** Constant identifying the PostgreSQL jdbc driver */
    public static String PGSQL_JDBC_DRIVER = "PostgreSQL Native Driver";
    /** Constant identifying the Sybase jdbc driver */
    public static String SYBASE_JDBC_DRIVER = "jConnect (TM) for JDBC (TM)";
    
    public static void query(String sql, String driverName) {
        String countSql = generateCountSql(sql, ORACLE_JDBC_DRIVER);
        System.out.println(countSql);
        
        //System.out.println(isSpecificDriver(driverName));
        
        String pageSql = generatePageSql(sql, ORACLE_JDBC_DRIVER);
        System.out.println(pageSql);
    }
    
    /**
     * Generate page sql by origin sql for oracle   
     * 
     * @param driverName driver name.
     * @return  if the driver Oracle, MySQL, PostgreSQL, Sybase is true.
     * @throws SQLException
     */
    public static boolean isSpecificDriver(String driverName) {
         boolean result = ORACLE_JDBC_DRIVER.equals(driverName) 
             || MYSQL_JDBC_DRIVER.equals(driverName)
             || PGSQL_JDBC_DRIVER.equals(driverName)
             || SYBASE_JDBC_DRIVER.equals(driverName)
             ;
         return result;
    }

    public static String generateCountSql(String sql, String driverName) {
        //System.out.println(StringUtils.substringBetween("yabczy123z", "y", "z"));
        String tempSql = sql.toLowerCase();
        int tempIndex = 0;
        int beginIndex = 0;
        tempIndex = tempSql.indexOf("select");
        if (tempIndex != -1) {
            beginIndex = tempIndex + 6;
        }
        int endIndex = 0;
        tempIndex = tempSql.indexOf("from");
        if (tempIndex != -1) {
            endIndex = tempIndex;
        }
        int lengthIndex = sql.length();
        if (PGSQL_JDBC_DRIVER.equals(driverName)) {
            tempIndex = tempSql.indexOf("order");
            if (tempIndex != -1) {
                lengthIndex = tempIndex;
            }
        }
        //System.out.println(beginIndex + ", " + endIndex + ", " + lengthIndex);
        
        StringBuilder result = new StringBuilder();
        result.append(sql.substring(0, beginIndex))
        .append(" count(*) ")
        .append(sql.substring(endIndex, lengthIndex));
        
        return result.toString();
    }
    
    public static String generatePageSql(String sql, String driverName) {
        //System.out.println(">>>generatePageSql sql=" + sql + ", pageArg=" + pageArg + ", driverName=" + driverName);
        String pageSql = sql;
        if ( ORACLE_JDBC_DRIVER.equals(driverName) ) {
            pageSql = generatePageSqlForOracle(sql);
        } else if ( MYSQL_JDBC_DRIVER.equals(driverName) ) {
            pageSql = generatePageSqlForMysql(sql);
        } else if ( PGSQL_JDBC_DRIVER.equals(driverName) ) {
            pageSql = generatePageSqlForPgsql(sql);
        } else if ( SYBASE_JDBC_DRIVER.equals(driverName) ) {
            pageSql = generatePageSqlForSybase(sql);
        } 

        //System.out.println("<<<generatePageSql pageSql=" + pageSql);
        return pageSql;
    }
    
    public static String generatePageSqlForOracle(String sql) {
        StringBuilder result = new StringBuilder();
        result.append("select ").append("*")
        .append("\nfrom (select ").append("temp.*").append(", rownum row_num ")
        .append("\n      from (").append(sql).append(") temp \nwhere rownum <= ").append(15)
        .append("\n) where row_num >= ").append(1);

        return result.toString();
    }
    
    public static String generatePageSqlForMysql(String sql) {
        StringBuilder result = new StringBuilder();
        result.append(sql).append(" limit ").append(0).append(", ").append(15);

        return result.toString();
    }

    public static String generatePageSqlForPgsql(String sql) {
        StringBuilder result = new StringBuilder();
        result.append(sql).append(" limit ").append(15).append(" offset ").append(0);

        return result.toString();
    }

    public static String generatePageSqlForSybase(String sql) {
        StringBuilder result = new StringBuilder();
        result.append("set rowcount ").append(15)
		.append("\n").append(sql)
		.append("\nset rowcount ").append(0);

        return result.toString();
    }
    
    
    
    // -----------------------  other   -------------------------------
    
    public static String generatePageSql2(String sql) {
        StringBuilder result = new StringBuilder();
        result.append("select ").append(getFiledSql(sql, ""))
        .append(" \nfrom (select ").append(getFiledSql(sql, "")).append(", rownum row_num ")
        .append(" \n      from ( ").append(sql).append(" ) temp ")
        .append("\n) where row_num between 11 and 20");
        
        return result.toString();
    }
    
    public static String getFiledSql(String sql, String str) {
        String temp = StringUtils.substringBetween(sql, "select", "from");
        //System.out.println("temp=" + temp);

        StringBuilder result = new StringBuilder();
        String[] strs = StringUtils.split(temp, ",");
        int i = 0;
        for (String s : strs) {
            while (StringUtils.indexOf(s, " ") != -1) {
                s = StringUtils.substringAfter(s, " ");
            }
            while (StringUtils.indexOf(s, ".") != -1) {
                s = StringUtils.substringAfter(s, ".");
            }
            result.append(str).append(s);
            if ( (i++) != (strs.length - 1) ) {
                result.append(",");
            }
        }

        //System.out.println("result.toString()=" + result.toString());
        return result.toString();
    }
    
    // --------------- other -----------------
    /**
     * 将单个的 ' 换成 ''; SQL 规则:如果单引号中的字符串包含一个嵌入的引号,可以使用两个单引号表示嵌入的单引号.
     */

    public static String replaceSql(String input) {
        return StringUtils.replace(input, "'", "''");
    }


    /**
     * create by yangwm in Nov 23, 2009 4:48:29 PM
     * @param args
     */
    public static void main(String[] args) {
        String sql = "Select b.id b_id, b.title b_title, b.authors \tb_authors, d.dummy d_dummy From book b, dual d where 1=1 Order by b.id desc";
        PageUtils.query(sql, ORACLE_JDBC_DRIVER);
        PageUtils.query(sql, MYSQL_JDBC_DRIVER);
        PageUtils.query(sql, PGSQL_JDBC_DRIVER);
        PageUtils.query(sql, SYBASE_JDBC_DRIVER);
        //SqlHelper.getFiledSql(sql, " rs.");
    }

}

/*// select b.id, b.title, b.authors from book b order by b.id desc
generatePageSql：
select count(*) from book b order by b.id desc
select *
from (select temp.*, rownum row_num 
      from ( select b.id, b.title, b.authors from book b order by b.id desc ) temp 
) where row_num between 11 and 20

generatePageSql2：
select count(*) from book b order by b.id desc
select id,title,authors 
from (select temp.id,temp.title,temp.authors, rownum row_num  
      from ( select b.id, b.title, b.authors from book b order by b.id desc ) temp 
) where row_num between 11 and 20

*/


/*//select b.id b_id, b.title b_title, b.authors b_authors, d.dummy d_dummy from book b, dual d order by b.id desc
generatePageSql：
select count(*) from book b, dual d order by b.id desc
select *
from (select temp.*, rownum row_num 
      from ( select b.id b_id, b.title b_title, b.authors b_authors, d.dummy d_dummy from book b, dual d order by b.id desc ) temp 
) where row_num between 11 and 20

generatePageSql2：
select count(*) from book b, dual d order by b.id desc
select b_id,b_title,    b_authors, 
from (select b_id,b_title,  b_authors,, rownum row_num  
      from ( select b.id b_id, b.title b_title, b.authors   b_authors, d.dummy d_dummy from book b, dual d order by b.id desc ) temp 
) where row_num between 11 and 20

*/

/*
select count(*) from book b, dual d order by b.id desc
select *
from (select temp.*, rownum row_num 
      from (select b.id b_id, b.title b_title, b.authors    b_authors, d.dummy d_dummy from book b, dual d order by b.id desc) temp 
where rownum <= 15
) where row_num >= 1
select b.id b_id, b.title b_title, b.authors    b_authors, d.dummy d_dummy from book b, dual d order by b.id desc limit 0, 15
select b.id b_id, b.title b_title, b.authors    b_authors, d.dummy d_dummy from book b, dual d order by b.id desc limit 15 offset 0
set rowcount 15
select b.id b_id, b.title b_title, b.authors    b_authors, d.dummy d_dummy from book b, dual d order by b.id desc
set rowcount 0

*/
