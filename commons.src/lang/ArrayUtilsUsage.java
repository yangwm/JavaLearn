package lang;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;

public class ArrayUtilsUsage {
    
    public static void main(String[] args) {
        // data setup
        int[] intArray1 = { 2, 4, 8, 16 };
        int[][] intArray2 = { { 1, 2 }, { 2, 4 }, { 3, 8 }, { 4, 16 } };
        Object[][] notAMap = {
                { "A", new Double(100) },
                { "B", new Double(80) },
                { "C", new Double(60) },
                { "D", new Double(40) },
                { "E", new Double(20) }
        };

        // printing arrays
        System.out.println("intArray1: " + ArrayUtils.toString(intArray1));
        System.out.println("intArray2: " + ArrayUtils.toString(intArray2));
        System.out.println("notAMap: " + ArrayUtils.toString(notAMap));
        
        // finding items
        System.out.println("intArray1 contains '8'? "
                + ArrayUtils.contains(intArray1, 8));
        System.out.println("intArray1 index of '8'? "
                + ArrayUtils.indexOf(intArray1, 8));
        System.out.println("intArray1 last index of '8'? "
                + ArrayUtils.lastIndexOf(intArray1, 8));

        // cloning and resversing
        int[] intArray3 = ArrayUtils.clone(intArray1);
        System.out.println("intArray3: " + ArrayUtils.toString(intArray3));
        ArrayUtils.reverse(intArray3);
        System.out.println("intArray3 reversed: "
                + ArrayUtils.toString(intArray3));

        // primitive to Object array
        Integer[] integerArray1 = ArrayUtils.toObject(intArray1);
        System.out.println("integerArray1: "
                + ArrayUtils.toString(integerArray1));

        // build Map from two dimensional array
        Map<String, Double> map = ArrayUtils.toMap(notAMap);
        System.out.println("get 'C' from map: " + map.get("C"));
    }

}

/*
intArray1: {2,4,8,16}
intArray2: {{1,2},{2,4},{3,8},{4,16}}
notAMap: {{A,100.0},{B,80.0},{C,60.0},{D,40.0},{E,20.0}}
intArray1 contains '8'? true
intArray1 index of '8'? 2
intArray1 last index of '8'? 2
intArray3: {2,4,8,16}
intArray3 reversed: {16,8,4,2}
integerArray1: {2,4,8,16}
get 'C' from map: 60.0
*/
