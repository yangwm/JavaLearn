package jref.begin;


public class SampleExample {
    /**
     * create by yangwm in Nov 28, 2009 8:15:18 PM
     * @param args
     * @throws ClassNotFoundException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        if (args.length != 1) {
            System.out.println("args.length != 1, use java className");
            return;
        }
        
        String className = args[0];
        
        Class<?> cls = Class.forName(className);
        Object obj = cls.newInstance();
        System.out.println("obj.getClass().getName()=" + obj.getClass().getName());
        
        if (cls.getInterfaces().length >= 1) {
            System.out.println("cls.getInterfaces()[0]=" + cls.getInterfaces()[0]);
        }
        
    }
        
}

/*
java java.lang.String
obj.getClass().getName()=java.lang.String
cls.getInterfaces()[0]=interface java.io.Serializable
*/

/*
java java.lang.Object
obj.getClass().getName()=java.lang.Object
*/

/*
obj.getClass().getName()=jref.hello4.MessageProviderXml
cls.getInterfaces()[0]=interface jref.hello4.MessageProvider
*/
