/**
 * 
 */
package pb.model;

import java.io.Serializable;

/**
 * 
 * 
 * @author yangwm Oct 30, 2011 12:23:28 AM
 */
public class Person implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private long id;
    private java.lang.String name;
    private java.lang.String email;
    private java.util.List<pb.model.PhoneNumber> phone;
    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public java.lang.String getName() {
        return name;
    }
    public void setName(java.lang.String name) {
        this.name = name;
    }
    public java.lang.String getEmail() {
        return email;
    }
    public void setEmail(java.lang.String email) {
        this.email = email;
    }
    public java.util.List<pb.model.PhoneNumber> getPhone() {
        return phone;
    }
    public void setPhone(java.util.List<pb.model.PhoneNumber> phone) {
        this.phone = phone;
    }
}
