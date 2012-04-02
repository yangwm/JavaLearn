
package util;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * MySQL/HQL 查询条件构建器(只支持and查询), 支持 like 通配符查询和忽略 NULL 参数.
 * 智能拼参数: 当参数为空时, 会忽略此 and 条件. 调用方式见 main 方法的DEMO.
 * 
 * @author yangwm in Jan 20, 2010 11:13:33 AM
 */
public class SQLCondition {
    public final static String LIKE = "like";
//    public final static String GT = ">";
//    public final static String LT = "<";
//    public final static String EQ = "=";
//    public final static String EQIGCASE = "EC";
//    public final static String GTE = ">=";
//    public final static String LTE = "<=";

    public final static String ParamType_Number = "NUM";//数字类型
    public final static String ParamType_String = "STR";//字符串类型
    public final static String ParamType_Datetime = "DATE";//日期类型
    private String condition = "";//查询条件
    private String table;// 表名
    // 此方法演示了如何调用
    public static void main(String[] args) {
        SQLCondition cond = new SQLCondition();
        cond.setTable("表1 bill");// 可选设置, 用于生成 FROM 和 COUNT 语句
        cond.add("bill.providername", "like", "value");
        cond.add("bill.2", "=", "value2");
        cond.add("bill.3", "=", null);// 被忽略
        cond.add("bill.4", "=", "");// 被忽略
        cond.addNum("数字列", "=", "3");
        cond.addNum("数字列", ">", "4");
        cond.addDate("日期列", "<=", "2009", "2");
        System.out.println(cond.getCondition());// 条件语句
        System.out.println(cond.getFromSQL());//生成的 FROM 语句
        System.out.println(cond.getCountSQL());// 生成的 COUNT 语句

        cond.clear();// 清空现有参数列表, 然后可重用此对象

    }
    /**
     * 添加比较条件, 指定所有参数.
     * @param column 列名
     * @param operator 操作符
     * @param value 参数值
     * @param type 类型
     * @param allowEmpty 是否允许为空
     */
    public void add(String column, String operator, Object value, String type, boolean allowEmpty) {
        if(!allowEmpty && ( value == null || "".equals(value))) {
            return;
        }
        if(type.equals(ParamType_String) || type.equals(ParamType_Datetime)) {
            if(value instanceof String) {
                value = PageUtils.replaceSql((String)value);
            }
            if(LIKE.equalsIgnoreCase(operator)) {
                if(type.equals(ParamType_Datetime)) {
                    condition += column  + " LIKE '"+ value +"%' AND ";// 缩小查询范围
                } else {
                    condition += column  + " LIKE '%"+ value +"%' AND ";
                }
            } else {
                // TODO 更多查询操作符支持
                condition += column  + " " + operator + " '"+ value +"' AND ";
            }
        }
        // 数字类型
        else if(type.equals(ParamType_Number)) {
            if(LIKE.equalsIgnoreCase(operator)) {
                condition += column  + " = "+ " " + value +" AND ";
            } else {
                condition += column  + " " + operator + " " + value +" AND ";
            }
        }
    }
    /**
     * 添加字符串比较条件
     * @param column 列名
     * @param operator 操作符
     * @param value 取值
     */
    public void add(String column, String operator, String value) {
        add(column, operator, value, ParamType_String, false);
    }
    /**
     * 添加数字比较条件
     * @param column 列名
     * @param operator 操作符
     * @param value 取值
     */
    public void addNum(String column, String operator, String value) {
        if(!StringUtils.isNumeric(value)) {
            return;
        }
        add(column, operator, value, ParamType_Number, false);
    }
    /**
     * 将给定的日期字符串作为日期参与比较.
     * @param column 列名
     * @param operator 操作符
     * @param value 日期字符串
     */
    public void addDate(String column, String operator, String value) {
        add(column, operator, value, ParamType_Datetime, false);
    }
    /**
     * 将年月格式的日期格式化后作为比较字符串.
     * @param column 列名
     * @param operator 操作符
     * @param year 年
     * @param month 月
     */
    public void addDate(String column, String operator, String year, String month) {
        if(StringUtils.isEmpty(year) || StringUtils.isEmpty(month)) {
            return;
        }
        // 日期匹配, 月份统一显示为2位
        if(month.length() == 1) {
            month = "0" + month;
        }
        add(column, operator, year + "-" + month, ParamType_Datetime, false);
    }
    /**
     * 将日期固定格式化后作为比较字符串.
     * @param column 列名
     * @param operator 操作符
     * @param date
     * @param datePattern 日期格式化字符串
     */
    public void addDate(String column, String operator, Date date, String datePattern) {
        if(date == null) {
            return;
        }
        String value = DateUtil.formatDate(date, datePattern);
        addDate(column, operator, value);
    }
    /**
     * 将日期作为 YYYY-MM-dd 的格式加入比较字符串.
     * @param column 列名
     * @param operator 操作符
     * @param date
     */
    public void addDate(String column, String operator, Date date) {
        if(date == null) {
            return;
        }
        addDate(column, operator, date, "YYYY-MM-dd");
    }
    /**
     * 得到 From 语句和条件.
     * @return FROM ${table} where ....
     */
    public String getFromSQL() {
        return "FROM " + getTable() + getCondition();
    }
    /**
     * 得到计算 COUNT 语句和条件.
     * @return SELECT COUNT(*) FROM ${table} where ....
     */
    public String getCountSQL() {
        return "SELECT COUNT(*) FROM " + getTable() + getCondition();
    }
    /**
     * 获得查询条件.
     * @return 包含 Where 条件的语句
     */
    public String getCondition() {
        // 防止多次调用出现多个where
        if(condition.indexOf("WHERE") != -1) {
            return condition;
        }
        // 去掉末尾的"and "并加入where语句
        if(!StringUtils.isEmpty(condition)) {
            if(condition.substring(condition.length()-4, condition.length()).equals("AND ") ){
                condition = condition.substring(0, condition.length()-4);
            }
            condition = " WHERE " + condition;
        }

        return condition;
    }
    /** 清空查询条件 */
    public void clear() {
        condition = "";
    }
    /** 返回查询条件作为对象描述 */
    public String toString() {
        return getCondition();
    }

    /**
     * 得到表名
     * @return
     */
    public String getTable() {
        return table;
    }

    /**
     * 设置表名/实体名
     * @param table
     */
    public void setTable(String table) {
        this.table = table;
    }

}

/*
 WHERE bill.providername LIKE '%value%' AND bill.2 = 'value2' AND 数字列 = 3 AND 数字列 > 4 AND 日期列 <= '2009-02' 
FROM 表1 bill WHERE bill.providername LIKE '%value%' AND bill.2 = 'value2' AND 数字列 = 3 AND 数字列 > 4 AND 日期列 <= '2009-02' 
SELECT COUNT(*) FROM 表1 bill WHERE bill.providername LIKE '%value%' AND bill.2 = 'value2' AND 数字列 = 3 AND 数字列 > 4 AND 日期列 <= '2009-02' 

*/
