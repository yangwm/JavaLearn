package basics.learning.arrays;

import util.EntityUtil;

public class MultiDimArrayDemo {
    public static void main(String[] args) {
        String[][] names = {{"Mr. ", "Mrs. ", "Ms. "},
                            {"Smith", "Jones"}};
        System.out.println(names[0][0] + names[1][0]); //Mr. Smith
        System.out.println(names[0][2] + names[1][1]); //Ms. Jones
        
        System.out.println("-----length-----");
        System.out.println(names.length);
        System.out.println(names[0].length);
        System.out.println(names[1].length);
        
        String[][] names2 = new String[3][5];
        for (int i = 0; i < names2.length; i++) {
            for (int j = 0; j < names2[i].length; j++) {
                names2[i][j] = i + "" + j;
            }
        }
        System.out.println(EntityUtil.toString(names2));
        
        // throw java.lang.ArrayIndexOutOfBoundsException: 2
        //System.out.println(names[1][2]);
    }
}
