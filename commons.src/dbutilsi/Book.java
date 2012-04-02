/**
 * 
 */
package dbutilsi;

/**
 * 书业务对象 
 * 
 * @author yangwm in Mar 25, 2010 2:48:13 PM
 */
public class Book extends BaseBean {
    /**
     * 
     */
    private static final long serialVersionUID = -8181458937439302524L;
        
    public long id;
    public String title;
    public String authors ;
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Book{")
        .append(id)
        .append(",")
        .append(title)
        .append(",")
        .append(authors)
        .append(super.toString())
        .append("}");
        return sb.toString();
    }

    // getter setter 
    
    public String getAuthors() {
        return authors;
    }
    public void setAuthors(String authors) {
        this.authors = authors;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
}
