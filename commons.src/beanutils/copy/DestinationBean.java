/**
 * 
 */
package beanutils.copy;


/**
 * ֤��������ݴ���form
 * 
 * @author yangwm in May 31, 2009 5:23:46 PM
 */
public class DestinationBean {
    
    private long id;
    private String name;
    private java.util.Date beginDate;
    private String interval;
    private long endDate;
    private StringBuilder describe;
    private String createDate;
    private java.util.Date strDate;
    private java.sql.Date sqlDate;
    private java.sql.Timestamp sqlTimestamp;
    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public java.util.Date getBeginDate() {
        return beginDate;
    }
    public void setBeginDate(java.util.Date beginDate) {
        this.beginDate = beginDate;
    }
    public String getInterval() {
        return interval;
    }
    public void setInterval(String interval) {
        this.interval = interval;
    }
    public long getEndDate() {
        return endDate;
    }
    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }
    public StringBuilder getDescribe() {
        return describe;
    }
    public void setDescribe(StringBuilder describe) {
        this.describe = describe;
    }
    public String getCreateDate() {
        return createDate;
    }
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
    public java.util.Date getStrDate() {
        return strDate;
    }
//    public void setStrDate(java.util.Date strDate) {
//        this.strDate = strDate;
//    }
    public java.sql.Date getSqlDate() {
        return sqlDate;
    }
    public void setSqlDate(java.sql.Date sqlDate) {
        this.sqlDate = sqlDate;
    }
    public java.sql.Timestamp getSqlTimestamp() {
        return sqlTimestamp;
    }
    public void setSqlTimestamp(java.sql.Timestamp sqlTimestamp) {
        this.sqlTimestamp = sqlTimestamp;
    }
    
}
