/**
 * 
 */
package xml.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.List;

import jref.hello5.MethodUtils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


/**
 * 
 * 
 * @author yangwm Apr 19, 2010 3:45:40 PM
 */
public class ConfigTest {

    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) {
        ObjectConfigBean commonObj = new ObjectConfigBean();
        readCommonObject("D:/study/tempProject/JavaLearn/src/xml/config/objectConfig.xml", commonObj);
        System.out.println(commonObj.getId() + ", " + commonObj.getName());
        writeCommonObject("D:/study/tempProject/JavaLearn/src/xml/config/objectConfig.xml.txt", commonObj);
        
        
        Class<ClassConfigBean> classObj = ClassConfigBean.class;
        readClassObject("D:/study/tempProject/JavaLearn/src/xml/config/classConfig.xml", classObj);
        System.out.println(ClassConfigBean.getIp() + ", " + ClassConfigBean.getPort());
        writeClassObject("D:/study/tempProject/JavaLearn/src/xml/config/classConfig.xml.txt", classObj);
        /**/
    }

    
    // ---------- Common Object -------------
    
    public static void readCommonObject(String fileName, Object commonObject) {
        InputStream is = null;
        
        try {
            is = new FileInputStream(fileName);
            
            SAXReader saxReader = new SAXReader();
            Document doc = saxReader.read(is);
            Element root = doc.getRootElement();

            List<Element> propList = root.elements("property");
            for (int i = 0; i < propList.size(); i++) {
                Element propEle = propList.get(i);
                
                String name = propEle.attributeValue("name");
                String value = propEle.getText();
                MethodUtils.setFieldValue(commonObject, name, value, String.class);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    
    public static void writeCommonObject(String fileName, Object commonObject) {
        FileWriter writer = null;
        try {
            Document document = DocumentHelper.createDocument();
            Element root = document.addElement("config");
            //root.addAttribute("class", commonObject.getClass().getCanonicalName());
            //root.addAttribute("type", "object");

            Field[] fields = commonObject.getClass().getDeclaredFields();
            for (Field field : fields) {
                Element element = root.addElement("property");
                
                String fieldName = field.getName();
                element.addAttribute("name", fieldName);
                element.addText((String)MethodUtils.getFieldValue(commonObject, fieldName));
            }
            
            writer = new FileWriter(fileName);
            document.write(writer);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    // ---------- Class Object -------------
    
    public static void readClassObject(String fileName, Class<?> classObject) {
        InputStream is = null;
        
        try {
            is = new FileInputStream(fileName);
            
            SAXReader saxReader = new SAXReader();
            Document doc = saxReader.read(is);
            Element root = doc.getRootElement();

            List<Element> propList = root.elements("property");
            for (int i = 0; i < propList.size(); i++) {
                Element propEle = propList.get(i);
                
                String name = propEle.attributeValue("name");
                String value = propEle.getText();
                MethodUtils.setStaticFieldValue(classObject, name, value, String.class);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    
    
    public static void writeClassObject(String fileName, Class<?> classObject) {
        FileWriter writer = null;
        try {
            Document document = DocumentHelper.createDocument();
            Element root = document.addElement("config");
            //root.addAttribute("class", classObject.getCanonicalName());
            //root.addAttribute("type", "class");

            Field[] fields = classObject.getDeclaredFields();
            for (Field field : fields) {
                Element element = root.addElement("property");
                
                String fieldName = field.getName();
                element.addAttribute("name", fieldName);
                element.addText((String)MethodUtils.getStaticFieldValue(classObject, fieldName));
            }
            
            writer = new FileWriter(fileName);
            document.write(writer);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
}

/*
T1015, yangwm
localhost, 8080

*/
