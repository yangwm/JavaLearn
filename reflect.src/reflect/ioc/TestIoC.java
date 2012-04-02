package reflect.ioc;

public class TestIoC {
	public static void main(String[] args) throws Exception{
		/*
		Employee e=new Employee();
		e.setAge(30);
		e.setName("Zhang3");
		e.setSalary(5000.37);
		*/
		BeanFactory f=new BeanFactory();
		Employee e=(Employee)f.getBean("b1");
		System.out.println(e.getName());
		System.out.println(e.getAge());
		System.out.println(e.getSalary());
		System.out.println(e.getAddr().getStreet());
		System.out.println(e.getAddr().getNo());
	}
}

/*
field.type=java.lang.String, propertyName=name, propertyValue=a, propertyRef=
field.type=int, propertyName=age, propertyValue=30, propertyRef=
field.type=double, propertyName=salary, propertyValue=5000.37, propertyRef=
field.type=java.lang.String, propertyName=street, propertyValue=5Daokou, propertyRef=
field.type=java.lang.String, propertyName=no, propertyValue=B19, propertyRef=
field.type=reflect.ioc.Address, propertyName=addr, propertyValue=, propertyRef=b2
a
30
5000.37
5Daokou
B19

*/
