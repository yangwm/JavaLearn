package digester.academy;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private String name;
    private String division;
    private List<Course> courses = new ArrayList<Course>();
    
    public void addCourse(Course course) {
        courses.add(course);
    }
    
//    // not ok 
//    public void addCourse(String id, String name) {
//        Course course = new Course();
//        course.setId(id);
//        course.setName(name);
//        courses.add(course);
//    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass());
        sb.append(":{");
        
        sb.append("name:");
        sb.append(name);
        
        sb.append(", division:");
        sb.append(division);
        
        sb.append(", courses:[");
        for (Course c : courses) {
            sb.append(c.toString());
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
    public String getDivision() {
        return division;
    }
    public void setDivision(String division) {
        this.division = division;
    }
    public List<Course> getCourses() {
        return courses;
    }
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

}
