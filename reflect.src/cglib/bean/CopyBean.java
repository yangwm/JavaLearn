/**
 * 
 */
package cglib.bean;

/**
 * 
 * 
 * @author yangwm Apr 16, 2010 10:07:59 AM
 */
public class CopyBean {
    
    private long id;
    private String name;
    private java.util.Date beginDate;
    private Integer interval;
    private StringBuilder describe;
    private String strDate;
    
    
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
    public Integer getInterval() {
        return interval;
    }
    public void setInterval(Integer interval) {
        this.interval = interval;
    }
    public StringBuilder getDescribe() {
        return describe;
    }
    public void setDescribe(StringBuilder describe) {
        this.describe = describe;
    }
    public String getStrDate() {
        return strDate;
    }
    public void setStrDate(String strDate) {
        this.strDate = strDate;
    }
    
    
}
