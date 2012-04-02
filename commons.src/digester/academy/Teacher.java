package digester.academy;

import java.util.ArrayList;
import java.util.List;

public class Teacher {
    private String name;
    private List<String> certifications = new ArrayList<String>();
    
    public void addCertification(String certification) {
        certifications.add(certification);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass());
        sb.append(":{");
        
        sb.append("name:");
        sb.append(name);

        sb.append(", certifications:[");
        for (String s : certifications) {
            sb.append(s);
            sb.append(",");
        }
        sb.append("]");
        
        sb.append("}");
        return sb.toString();
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<String> getCertifications() {
        return certifications;
    }
    public void setCertifications(List<String> certifications) {
        this.certifications = certifications;
    }
    
}
