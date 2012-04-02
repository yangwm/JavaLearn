package digester.academy;

public class Course {
    private String id;
    private String name;
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass());
        sb.append(":{");
        
        sb.append("id:");
        sb.append(id);
        
        sb.append(", name:");
        sb.append(name);
        
        sb.append("}");
        return sb.toString();
    }
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
}
