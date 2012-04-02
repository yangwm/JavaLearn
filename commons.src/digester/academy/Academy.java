package digester.academy;

import java.util.ArrayList;
import java.util.List;

public class Academy {
    private String name;
    private List<Student> students = new ArrayList<Student>();
    private List<Teacher> teachers = new ArrayList<Teacher>();
    
    public void addStudent(Student student) {
        students.add(student);
    }
    
    public void addTeacher(Teacher teacher) {
        teachers.add(teacher);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass());
        sb.append(":{");
        
        sb.append("name:");
        sb.append(name);
        
        sb.append(", students:[");
        for (Student s : students) {
            sb.append(s.toString());
            sb.append(",");
        }
        sb.append("]");
        
        sb.append(", teachers:[");
        for (Teacher t : teachers) {
            sb.append(t.toString());
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
    public List<Student> getStudents() {
        return students;
    }
    public void setStudents(List<Student> students) {
        this.students = students;
    }
    public List<Teacher> getTeachers() {
        return teachers;
    }
    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }
    
}
