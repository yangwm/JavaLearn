
public class TestMany2Many{
}
class Student{
    //Course[] cs;
    StudentCourse[] scs;
}
class Course{
   // Student[] ss;
   StudentCourse[] scs;
}

class StudentCourse{
    Student s;
    Course c;
}