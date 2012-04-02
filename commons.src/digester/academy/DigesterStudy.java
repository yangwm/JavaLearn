package digester.academy;

import java.io.IOException;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.DigesterLoader;
import org.xml.sax.SAXException;

/**
 * 
 * 
 * @author yangwm in Mar 15, 2009 11:19:21 PM
 */
public class DigesterStudy {

    /**
     * create by yangwm in Mar 15, 2009 12:56:18 PM
     * @param args
     */
    public static void main(String[] args) {
        //new DigesterStudy().run();
        new DigesterStudy().run2();
    }

//    /**
//     * 手工配置Digester的代码
//     * 
//     * create by yangwm in Mar 15, 2009 10:17:27 PM
//     */
//    public void run() {
//        Digester digester = new Digester();
//
//        //注意，此处并没有象上例一样使用push，是因为此处从根元素创建了一个对象实例
//        digester.addObjectCreate("academy", Academy.class);
//
//        //将<academy>的属性与对象的属性关联
//        digester.addSetProperties("academy");
//        
//        digester.addObjectCreate("academy/student", Student.class);
//        digester.addSetProperties("academy/student");
//        digester.addObjectCreate("academy/student/course", Course.class);
//        digester.addBeanPropertySetter("academy/student/course/id");
//        digester.addBeanPropertySetter("academy/student/course/name");
//        digester.addSetNext("academy/student/course", "addCourse");
//        digester.addSetNext("academy/student", "addStudent");
//        
//        digester.addObjectCreate("academy/teacher", Teacher.class);
//        digester.addSetProperties("academy/teacher");
//        //当遇到academy/teacher/certification时，调用addCertification
//        digester.addCallMethod("academy/teacher/certification", "addCertification", 1);
//        //设置addCertification的参数值，此处的0表示这个元素体的第一个值为参数值传入addCertification。
//        digester.addCallParam("academy/teacher/certification", 0);
//        digester.addSetNext("academy/teacher", "addTeacher");
//
//        try {
//            //Academy a = (Academy) digester.parse(getClass().getClassLoader().getResourceAsStream("academy.xml"));
//            Academy a = (Academy) digester.parse(DigesterStudy.class.getResourceAsStream("academy.xml"));
//
//            System.out.print(a);
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (SAXException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
    
    /**
     * 通过DigesterLoader可以从配置文件中创建Digester实例
     * 
     * create by yangwm in Mar 15, 2009 10:17:31 PM
     */
    public void run2() {
        //Digester digester = DigesterLoader.createDigester(this.getClass().getClassLoader().getResource("academyRules.xml"));
        Digester digester = DigesterLoader.createDigester(DigesterStudy.class.getResource("academy-rules.xml"));

        try {
            //Academy a = (Academy) digester.parse(getClass().getClassLoader().getResourceAsStream("academy.xml"));
            Academy a = (Academy) digester.parse(DigesterStudy.class.getResourceAsStream("academy.xml"));

            System.out.print(a);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}
