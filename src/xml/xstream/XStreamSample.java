/**
 * 
 */
package xml.xstream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * xstream sample 
 * 
 * @author yangwm in Dec 17, 2009 5:53:43 PM
 */
public class XStreamSample {
    
    static class SampleOne {
        /**
         * create by yangwm in Dec 17, 2009 5:53:43 PM
         * @param args
         */
        public static void main(String[] args) {
            PersonBean bean = new PersonBean("XStream1", 1, 
                    Arrays.asList(new String[] {"English", "中文"}), 
                    new PhoneNumber("0794", "8333846"));
            
            XStream sm = new XStream(new DomDriver());
            System.out.println(sm.toXML(bean));
        }
    }

    static class SampleTwo {
        /**
         * create by yangwm in Dec 17, 2009 5:53:43 PM
         * @param args
         */
        public static void main(String[] args) {
            PersonBean bean = new PersonBean("XStream1", 1, 
                    Arrays.asList(new String[] {"English", "中文"}), 
                    new PhoneNumber("0794", "8333846"));
            
            XStream sm = new XStream(new DomDriver());
            
            List<Class<?> > aliasList = new ArrayList<Class<?> >();
            aliasList.add(PersonBean.class);
            aliasList.add(bean.getLanguageList().getClass());
            for (Class<?> c : aliasList) {
                String[] temp = c.getName().split("\\.");
                sm.alias(temp[temp.length - 1], c);
            }
            
            System.out.println(sm.toXML(bean));
        }
    }

}

/* SampleOne: 
<xml.xstream.PersonBean>
  <name>XStream1</name>
  <age>1</age>
  <languageList class="java.util.Arrays$ArrayList">
    <a class="string-array">
      <string>English</string>
      <string>中文</string>
    </a>
  </languageList>
  <phoneNumber>
    <code>0794</code>
    <number>8333846</number>
  </phoneNumber>
</xml.xstream.PersonBean>

*/

/* SampleTwo: 
<PersonBean>
  <name>XStream1</name>
  <age>1</age>
  <languageList class="Arrays$ArrayList">
    <a class="string-array">
      <string>English</string>
      <string>中文</string>
    </a>
  </languageList>
  <phoneNumber>
    <code>0794</code>
    <number>8333846</number>
  </phoneNumber>
</PersonBean>

*/
