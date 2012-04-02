
package util;

import java.util.*;

import org.apache.commons.lang.StringUtils;

/**
 * 生成 Insert SQL 语句的类.
 * 
 * @author yangwm in Jan 20, 2010 11:13:06 AM
 */
public class SQLInsert extends HashMap<String, Object> {
    /**
     * 
     */
    private static final long serialVersionUID = -2440534987118447547L;
    
    private String table;// 表格名
    private String columns;// 列名表, 以逗号隔开
    /** 是否检查列名有效性 */
    private boolean checkColumn = true;
    /** 是否允许重复设置列值, 默认允许 */
    private boolean allowDuplicate = true;
    /** 列表, 保存可能的列名 */
    private List<String> columnList = new ArrayList<String>();
    public SQLInsert() {}
    /**
     * 给定表名和列名(用来检验)的构造器.
     * @param table - 表名
     * @param columns - 列名(用来检验)
     */
    public SQLInsert(String table, String columns) {
        setTable(table);
        setColumns(columns);
    }
    public static void main(String[] args) {
        SQLInsert insert = new SQLInsert();
        insert.setTable("User");
        insert.setColumns("name, age");
        insert.put("name", "BeanSoft");
        insert.put("age", 27);
        System.out.println(insert);
    }
    public String toString() {
        if(table == null || table.length() == 0) {
            throw new Error("对不起, 请调用 setTable() 指定表名");
        }
        String sql = "insert into " + table + " ( ";
        String values = "values( ";
        if(this.size() == 0) {
            throw new Error("对不起, 没有任何列值, 无法生成 INSERT 语句");
        } else if(columnList.size()  == 0){
            // 尝试从主键列表生成列名列表
            String[] cols = this.keySet().toArray(new String[0]);
            for(String col: cols) {
                columnList.add(col);
            }
        }
//        System.out.println("columnList=" + columnList.size());
        if(columnList.size() > 0 ) {
            for (int i = 0; i < columnList.size(); i++) {
                String col = columnList.get(i);
                Object value = this.get(col);
                if(value == null && checkColumn) {
                    throw new Error("对不起, 列[" + col + "]的值为空");
                }
                sql += col;
                if(!StringUtils.isNumeric(value + "")) {
                    values += "'" + value + "'";
                } else {
                    values += value;
                }
                if (columnList.size() > 1 && (i != columnList.size() - 1)) {
                    sql += (", ");
                    values += (", ");
                }
            }
        }

        sql += (" ) ");
        values += (" )");

//        System.out.println("自动生成的 sql = " + sql + values);
        return sql + values;
    }

    /**
     * @param columns the 列名表, 以逗号隔开 to set
     */
    public void setColumns(String columns) {
        this.columns = columns;
        columnList.clear();
        if(columns != null) {
            // 替换空格等字符, 否则解析会出错
            StringBuffer buff = new StringBuffer();
            for(int i = 0; i < columns.length(); i++) {
                char ch = columns.charAt(i);
                if(Character.isSpaceChar(ch)) {
                    continue;
                }
                buff.append(ch);
            }
            String[] cols = buff.toString().split(",");
//            System.out.println("cols.length=" + cols.length);
            for(String col: cols) {
                columnList.add(col);
            }
        }
    }

    /**
     * @return the 是否允许重复设置列值, 默认允许
     */
    public boolean isAllowDuplicate() {
        return allowDuplicate;
    }

    /**
     * @param allowDuplicate the 是否允许重复设置列值, 默认允许 to set
     */
    public void setAllowDuplicate(boolean allowDuplicate) {
        this.allowDuplicate = allowDuplicate;
    }

    /**
     * @return the 是否检查列名有效性
     */
    public boolean isCheckColumn() {
        return checkColumn;
    }

    /**
     * @param checkColumn the 是否检查列名有效性 to set
     */
    public void setCheckColumn(boolean checkColumn) {
        this.checkColumn = checkColumn;
    }

    /**
     * @return the 列名表, 以逗号隔开
     */
    public String getColumns() {
        return columns;
    }

    /**
     * 表格名
     * @return
     */
    public String getTable() {
        return table;
    }

    /**
     * 表格名
     * @param table
     */
    public void setTable(String table) {
        this.table = table;
    }
}

/*
insert into User ( name, age ) values( 'BeanSoft', 27 )

*/

