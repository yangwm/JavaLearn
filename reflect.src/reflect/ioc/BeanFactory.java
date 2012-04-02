package reflect.ioc;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class BeanFactory {
	private final Map beanElements=new HashMap();
	
	public BeanFactory(){
		try {
			DocumentBuilderFactory f=DocumentBuilderFactory.newInstance();
			DocumentBuilder b=f.newDocumentBuilder();
			Document doc=b.parse(BeanFactory.class.getResource("").getPath() + "beans.xml");
			NodeList nl=doc.getElementsByTagName("bean");
			for(int i=0;i<nl.getLength();i++){
				Element e=(Element)nl.item(i);
				String id=e.getAttribute("id");
				this.beanElements.put(id,e);
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Object getBean(String id) throws Exception{
		Element e=(Element)this.beanElements.get(id);
		String className=e.getAttribute("class");
		Class<?> c=Class.forName(className);
		Object o=c.newInstance();
		
		NodeList nl=e.getElementsByTagName("property");
		for(int i=0;i<nl.getLength();i++){
			Element propertyElement=(Element)nl.item(i);
			String propertyName=propertyElement.getAttribute("name");
			String propertyValue=propertyElement.getAttribute("value");
			String propertyRef=propertyElement.getAttribute("ref"); // if not have attribute name with ref, be return ""
			
			Field field=c.getDeclaredField(propertyName);
			String fieldTypeSimpleName=field.getType().getSimpleName();
			Object parameter=null;
			if (propertyRef.equals("")){
				if ("String".equals(fieldTypeSimpleName)) {
					parameter=propertyValue;
				} else if ("int".equalsIgnoreCase(fieldTypeSimpleName)) {
					parameter=Integer.valueOf(propertyValue);
				} else if ("double".equalsIgnoreCase(fieldTypeSimpleName)) {
					parameter=Double.valueOf(propertyValue);
				} else if ("long".equalsIgnoreCase(fieldTypeSimpleName)) {
					parameter=Long.valueOf(propertyValue);
				} else if ("float".equalsIgnoreCase(fieldTypeSimpleName)) {
					parameter=Float.valueOf(propertyValue);
				} else if ("short".equalsIgnoreCase(fieldTypeSimpleName)) {
					parameter=Short.valueOf(propertyValue);
				} else if ("bype".equalsIgnoreCase(fieldTypeSimpleName)) {
					parameter=Byte.valueOf(propertyValue);
				} else if ("boolean".equalsIgnoreCase(fieldTypeSimpleName)) {
					parameter=Boolean.valueOf(propertyValue);
				}
 			} else{
				parameter=getBean(propertyRef);
			}
			System.out.println("field.type=" + field.getType().getCanonicalName() + ", propertyName=" + propertyName + ", propertyValue=" + propertyValue + ", propertyRef=" + propertyRef );
			
			char[] cs=propertyName.toCharArray();
			cs[0]-=32;	// lowerCase to upperCase
			String methodName="set"+new String(cs);
			Method setMethod=c.getDeclaredMethod(methodName,field.getType());
			setMethod.invoke(o, parameter); // setXxx
		}
		return o;
	}
}
