/**
 * 
 */
package xml.xstream;

import java.util.List;

/**
 * xstream bean 
 * 
 * @author yangwm in Dec 17, 2009 5:56:46 PM
 */
public class PersonBean {
    
    private String name;
    private int age;
    private List<String> languageList;
    private PhoneNumber phoneNumber;
    
    public PersonBean() {}
    
    public PersonBean(String name, int age, 
            List<String> languageList, PhoneNumber phoneNumber) {
        this.name = name;
        this.age = age;
        this.languageList = languageList;
        this.phoneNumber = phoneNumber;
    }
    
    public String toString() {
        return "PersonBean[name='" + name + "',age='" + age + "',languageList='" 
                + languageList + "',phoneNumber='" + phoneNumber + "']";
    }
    
    // getter setter 
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }   
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public List<String> getLanguageList() {
        return languageList;
    }
    public void setLanguageList(List<String> languageList) {
        this.languageList = languageList;
    }
    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}

class PhoneNumber{
    
    private String code;
    private String number;
    
    public PhoneNumber() {}
    
    public PhoneNumber(String code, String number) {
        this.code = code;
        this.number = number;
    }
    
    public String toString() {
        return "PhoneNumber[code='" + code + "',number='" + number + "']";
    }

    // getter setter 

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    
} 
