/**
 * 
 */
package algorithm.max;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * max n algorithm beanch2
 * 
 * @author yangwm Oct 23, 2010 11:58:58 PM
 */
public class MaxNAlgorithmBeanch2 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        /*
         * 
         */
        int inputTotal = 1000000;
        int n = 100;
        
        /*
         * test data
         */
        List<People> inputList = new ArrayList<People>(inputTotal);
        for (int i = 0; i < inputTotal; i++) {
            People people = null;
            int money = i / 10;
            int factor = new Random().nextInt(5) + 1;
            if (factor == 1) {
                people = new People(i, "jx", money);
            } else if (factor == 2) {
                people = new People(i, "bj", money);
            } else if (factor == 3) {
                people = new People(i, "sh", money);
            } else {
                people = new People(i, null, money);
            }
            
            inputList.add(people);
        }
        Collections.shuffle(inputList);
        System.out.println("test begin inputTotal: " + inputTotal + ", n: " + n);
        System.out.println("test data inputList.size(): " + inputList.size());

        
        //-----------------------------------------------------------------

        People[] inputArray = new People[inputList.size()];
        for (int i = 0; i < inputArray.length; i++) {
            inputArray[i] = inputList.get(i);
        }
        System.out.println("-------JDK Arrays.sort max n with Comparable-------");
        
        /*
         * max and test print
         */
        long begin = System.currentTimeMillis();
        Arrays.sort(inputArray);
        People[] resultArray = (People[]) Array.newInstance(People.class, n);
        for (int i = inputArray.length - 1, resultIdx = 0; i >= inputArray.length - n; i--) {
            resultArray[resultIdx++] = inputArray[i];
        }
        long end = System.currentTimeMillis();
        System.out.println("cosume: " + (end - begin) + " ms");
        System.out.println("resultArray.length: " + resultArray.length);
        System.out.println("resultArray of first ten : " + Arrays.toString(Arrays.copyOf(resultArray, 10)));

        
        //-----------------------------------------------------------------

        inputArray = new People[inputList.size()];
        for (int i = 0; i < inputArray.length; i++) {
            inputArray[i] = inputList.get(i);
        }
        System.out.println("-------MaxNAlgorithm Array max n with Comparable-------");
        
        /*
         * max and test print
         */
        begin = System.currentTimeMillis();
        resultArray = MaxNAlgorithm.<People>max(inputArray, n);
        end = System.currentTimeMillis();
        System.out.println("cosume: " + (end - begin) + " ms");
        System.out.println("resultArray.length: " + resultArray.length);
        System.out.println("resultArray of first ten : " + Arrays.toString(Arrays.copyOf(resultArray, 10)));

    }

    /**
     * test object for max n algorithm beanch
     * 
     * @author yangwm Oct 24, 2010 4:16:58 PM
     */
    static class People implements Comparable<People> {
        public int id;
        public String province;
        public int money;
        
        public People(int id, String province, int money) {
            this.id = id;
            this.province = province;
            this.money = money;
        }
        
        @Override
        public int compareTo(People o) {
            if (province != null && o.province == null) {
                return 1;
            } else if (province == null && o.province != null) {
                return -1;
            } else {
                if (money > o.money) {
                    return 1;
                } else if (money < o.money) {
                    return -1;
                } else {
                    if (province == null && o.province == null) {
                        return 0;
                    } if (province.equalsIgnoreCase("jx")) {
                        return 1;
                    } else if (o.province.equalsIgnoreCase("jx")) {
                        return -1;
                    } else {
                        return province.compareToIgnoreCase(o.province);
                    }
                }
            }
        }
        
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("id=");
            sb.append(id);
            sb.append(" province=");
            sb.append(province);
            sb.append(" money=");
            sb.append(money);
            return sb.toString();
        }

    }

}


/*
test begin inputTotal: 1000000, n: 100
test data inputList.size(): 1000000
-------JDK Arrays.sort max n with Comparable-------
cosume: 1234 ms
resultArray.length: 100
resultArray of first ten : [id=999999 province=jx money=99999, id=999994 province=jx money=99999, id=999993 province=sh money=99999, id=999992 province=sh money=99999, id=999991 province=bj money=99999, id=999982 province=jx money=99998, id=999983 province=jx money=99998, id=999986 province=jx money=99998, id=999988 province=sh money=99998, id=999984 province=bj money=99998]
-------MaxNAlgorithm Array max n with Comparable-------
cosume: 93 ms
resultArray.length: 100
resultArray of first ten : [id=999994 province=jx money=99999, id=999999 province=jx money=99999, id=999993 province=sh money=99999, id=999992 province=sh money=99999, id=999991 province=bj money=99999, id=999983 province=jx money=99998, id=999982 province=jx money=99998, id=999986 province=jx money=99998, id=999988 province=sh money=99998, id=999984 province=bj money=99998]

*/
