/**
 * 
 */
package dbutilsi;

import java.io.Serializable;
import java.util.Date;


/**
 * 基本业务对象
 * 
 * @author yangwm
 */
public class BaseBean implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -9007239337140120689L;
    
    private String description;
    private String createBy;
    private Date createDate;
    private String updateBy;
    private Date updateDate;
    
    public BaseBean() {
     
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("BaseBean{");
        sb.append(description);
        sb.append(",");
        sb.append(createBy);
        sb.append(",");
        sb.append(createDate);
        sb.append(",");
        sb.append(updateBy);
        sb.append(",");
        sb.append(updateDate);
        sb.append("}");
        return sb.toString();
    }

    // getter setter 
    

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

}
