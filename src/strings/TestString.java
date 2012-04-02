package strings;


public class TestString {
    public static void main(String[] args) {
        /*
        String str1 = ".f";
        String str2 = ".";
        boolean isContain = false;
        String[] strArray= str1.split("\\.");
        System.out.println("strArray.length=" + strArray.length);
        int i = 0;
        for (String s : strArray) {
            System.out.println((++i) + "=" + s + ", ang length=" + s.length());
        }
        System.out.println(str1 + " isContain " + str2 + " = " + isContain);
        */
        String managerStr = "43001606.测试人员02";
        
        System.out.println("managerStr = " + managerStr);
        String[] strArray= managerStr.split("\\.");
        if (null != strArray[0] && strArray[0].equalsIgnoreCase("43001606")) {
            System.out.println("strArray[0] = " + strArray[0]);
        }
        
        managerStr = "43001606";
        System.out.println("managerStr = " + managerStr);
        strArray= managerStr.split("\\.");
        if (null != strArray[0] && strArray[0].equalsIgnoreCase("43001606")) {
            System.out.println("strArray[0] = " + strArray[0]);
        }
        
        managerStr = "43001606.测试人员02.ddf";
        System.out.println("managerStr = " + managerStr);
        strArray= managerStr.split("\\.");
        if (null != strArray[0] && strArray[0].equalsIgnoreCase("43001606")) {
            System.out.println("strArray[0] = " + strArray[0]);
        }
        
        System.out.println("-------------------");
        
        managerStr = ".";
        System.out.println("managerStr = " + managerStr);
        strArray= managerStr.split("\\.");
        System.out.println("strArray = " + strArray);
        System.out.println("strArray.length = " + strArray.length);
        if (strArray.length > 0 && null != strArray[0]  && strArray[0].equalsIgnoreCase("43001606")) {
            System.out.println("strArray[0] = " + strArray[0]);
        }
        
        
        managerStr = "";
        System.out.println("managerStr = " + managerStr);
        strArray= managerStr.split("\\.");
        System.out.println("strArray = " + strArray);
        System.out.println("strArray.length = " + strArray.length);
        if (strArray.length > 0 && null != strArray[0]  && strArray[0].equalsIgnoreCase("43001606")) {
            System.out.println("strArray[0] = " + strArray[0]);
        }

        
        String strTest = "43001606$测试人员02";
        
        System.out.println("strTest = " + strTest);
        String[] strTestArray = strTest.split("\\$");
        System.out.println("strTestArray.length = " + strTestArray.length);
        System.out.println("strTestArray[0] = " + strTestArray[0]);
    }
    
    static class TestStringIndex {
        public static void main(String[] args) {
            System.out.println("siteBox".indexOf("site2")); // 不匹配 
            System.out.println("siteBox".indexOf("site")); // 匹配 
            System.out.println("site".indexOf("siteBox")); // 不匹配 
            
            String str = "a4-bid=1,a4-bid=343,ou=bj";
            System.out.println(str.indexOf("ou="));
            if (str.indexOf("ou=") != -1) {
                str = str.substring(str.indexOf("ou=")+3,str.length());
            }
            System.out.println(str);
            
            System.out.println("Test substring------------");
            System.out.println("12345678".substring(0, 1));
            System.out.println("12345678".substring(1, 3));
            System.out.println("12345678".substring(0, 8));
            System.out.println("12345678".substring(0, 10));
            
        }
    }
    
    static class TestStringMatch {
        public static void main(String[] args) {
            System.out.println("siteBox".matches("site")); // 不匹配 
            System.out.println("site".matches("siteBox")); // 不匹配 
            System.out.println("site".matches("site")); // 匹配 
        }
    }
    
    static class TestStringEqual {
        public static void main(String[] args) {
            System.out.println("".equals(null));
        }
    }
    
    static class TestStringStartsWith {
        public static void main(String[] args) {
            System.out.println("123".startsWith("1"));
            System.out.println("123".startsWith("1", 1));
            System.out.println("123".startsWith("23"));
            System.out.println("123".startsWith("23", 1));
        }
    }
    
    static class TestStringEndsWith {
        public static void main(String[] args) {
            System.out.println("a4-bid=333".endsWith("a4-bid=333"));
            System.out.println("a4-bid=333,a4-bid=428".endsWith("a4-bid=333"));
            System.out.println("a4-bid=333,a4-bid=428".endsWith("a4-bid=428"));
            
            String str = (String)null;
        }
    }
    
    static class TestStringLength {
        public static void main(String[] args) {
            String[] strs = new String[] {};
            System.out.println(strs.length);
        }
    }
    
    
}