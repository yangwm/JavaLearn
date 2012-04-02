/**
 * 
 */
package jref.hello5;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @author yangwm in Dec 23, 2009 8:06:05 PM
 */
public class BeanFactory {
    
    private Map<String, Object> beans = new HashMap<String, Object>();
    
    public BeanFactory(String fileName) {
        ClassLoader loader = this.getClass().getClassLoader();
        
        InputStream is = loader.getResourceAsStream(fileName);
        SAXReader saxReader = new SAXReader();
        
        try {
            Document doc = saxReader.read(is);
            Element root = doc.getRootElement();
            List<?> beanList = root.elements("bean");
            
            for (Object o : beanList) {
                Element beanEle = (Element)o;
                String id = beanEle.attributeValue("id");
                String className = beanEle.attributeValue("class");
                
                Class<?> cls = Class.forName(className);
                
                Object obj = null;
                List<Element> consList = beanEle.elements("constructor-arg");
                if (consList.isEmpty()) {
                    obj = cls.newInstance();
                } else {
                    int size = consList.size();
                    Class<?>[] paramTypes = new Class[size];
                    Object[] params = new Object[paramTypes.length];
                    
                    for (int i = 0; i < paramTypes.length; i++) {
                        Element consEle = consList.get(i);
                        
                        paramTypes[i] = String.class;
                        params[i] = consEle.element("value").getText();
                    }
                    
                    Constructor<?> cons = cls.getConstructor(paramTypes);
                    obj = cons.newInstance(params);
                }
                beans.put(id, obj);
                
                List<Element> propList = beanEle.elements("property");
                for (int i = 0; i < propList.size(); i++) {
                    Element propEle = propList.get(i);
                    
                    String name = propEle.attributeValue("name");
                    Element valueEle = propEle.element("value");
                    Element refEle = propEle.element("ref");
                    if (valueEle != null) {
                        String value = valueEle.getText();
                        
                        MethodUtils.setFieldValue(obj, name, value, String.class);
                    } else if (refEle != null) {
                        String refName = refEle.attributeValue("local");
                        Object value = beans.get(refName);
                        
                        MethodUtils.setFieldValue(obj, name, value, value.getClass().getInterfaces()[0]);
                    }
                }
                
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        
    }
    
    public Object getBean(String beanName) {
        return beans.get(beanName);
    }
    
}
