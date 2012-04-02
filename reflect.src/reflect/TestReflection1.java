package reflect;
//:TestReflection1
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class TestReflection1 {
	public static void main(String[] args) {
		try {
			Class<?>[] cs = {String.class, Integer.class, Double.class};
			Constructor<StudentA> cons = StudentA.class.getConstructor(cs);
			Object[] obs = {"yangwm", 23, 78.8};
			Object ob = cons.newInstance(obs);
			boolean b = ValidateTool.validate(ob);
            System.out.println("ValidateTool.validate(ob)=" + b);
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}
}
/*
yangwm
23
78.8
ValidateTool.validate(ob)=true
*/

class StudentA{
	private String name;
	private Integer age;
	private Double mark;
	
	public StudentA() {
	}
	public StudentA(String name, Integer age, Double mark) {
		this.name = name;
		this.age = age;
		this.mark = mark;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Double getMark() {
		return mark;
	}
	public void setMark(Double mark) {
		this.mark = mark;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}

class ValidateTool{
	public static boolean validate(Object o){
		try {
			Field[] fs=o.getClass().getDeclaredFields();
			for(Field f:fs){
				f.setAccessible(true);
				Object value=f.get(o);
				
				System.out.println(value);
				if (value==null) {
				    return false;
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
