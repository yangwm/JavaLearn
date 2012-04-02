/**
 * 
 */
package pb.model;

import java.io.Serializable;

/**
 * 
 * 
 * @author yangwm Oct 30, 2011 12:44:13 AM
 */
public class PhoneNumber implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private java.lang.String number;
    private pb.model.PhoneType type;
    
    public java.lang.String getNumber() {
        return number;
    }
    public void setNumber(java.lang.String number) {
        this.number = number;
    }
    public pb.model.PhoneType getType() {
        return type;
    }
    public void setType(pb.model.PhoneType type) {
        this.type = type;
    }
    
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{number=");
        sb.append(number);
        sb.append(",type=");
        sb.append(type);
        sb.append("}");
        return sb.toString();
    }
}
