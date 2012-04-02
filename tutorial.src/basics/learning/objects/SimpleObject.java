package basics.learning.objects;

public class SimpleObject {
    
    public static boolean equate(String s1, String s2) {
        if (s1 == null) {
            s1 = "";
        }
        if (s2 == null) {
            s2 = "";
        }
        System.out.println("String inner equate s1 " + s1);
        System.out.println("String inner equate s2 " + s2);
        return s1 == s2;
    }
    
    public static boolean equate(Integer i1, Integer i2) {
        if (i1 == null) {
            i1 = new Integer(0);
        }
        if (i2 == null) {
            i2 = new Integer(0);
        }
        System.out.println("Integer inner equate i1 " + i1);
        System.out.println("Integer inner equate i2 " + i2);
        return i1 == i2;
    }
    
    public static boolean equate(StringBuilder s1, StringBuilder s2) {
        if (s1 == null) {
            s1 = new StringBuilder("a");
        }
        if (s2 == null) {
            s2 = new StringBuilder("b");
        } else {
            s2.append("yangwm");
        }
        System.out.println("StringBuilder inner equate s1 " + s1);
        System.out.println("StringBuilder inner equate s2 " + s2);
        return s1.equals(s2);
    }
    
    public static boolean equate(Point p1, Point p2) {
        if (p1 == null) {
            p1 = new Point(100,200);
        }
        if (p2 == null) {
            p2 = new Point(100,100);
        } else {
            p2.y += 400;
        }
        System.out.println("Point inner equate s1 " + p1);
        System.out.println("Point inner equate s2 " + p2);
        return p1.equals(p2);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("String equate ... ");
        String str1 = null;
        String str2 = null;
        System.out.println("equate(str1, str2)=" + equate(str1, str2));
        System.out.println("main str1 " + str1);
        System.out.println("main str2 " + str2);
        
        System.out.println("Integer equate ... ");
        Integer int1 = null;
        Integer int2 = null;
        System.out.println("equate(int1, int2)=" + equate(int1, int2));
        System.out.println("main int1 " + int1);
        System.out.println("main int2 " + int2);
        
        System.out.println("StringBuilder equate ... ");
        StringBuilder strB1 = null;
        StringBuilder strB2 = new StringBuilder();
        System.out.println("equate(strB1, strB2)=" + equate(strB1, strB2));
        System.out.println("main strB1 " + strB1);
        System.out.println("main strB1 " + strB2);
        
        System.out.println("Point equate ... ");
        Point point1 = null;
        Point point2 = new Point(100, 100);
        System.out.println("equate(point1, point2)=" + equate(point1, point2));
        System.out.println("main point1 " + point1);
        System.out.println("main point2 " + point2);
    }

}
