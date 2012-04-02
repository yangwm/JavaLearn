/**
 * 
 */
package algorithm.max;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * max n algorithm beanch3
 * 
 * @author yangwm Oct 24, 2010 4:16:12 PM
 */
public class MaxNAlgorithmBeanch3 {

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
        Arrays.sort(inputArray, new PeopleComparator());
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
        resultArray = MaxNAlgorithm.<People>max(inputArray, n, new PeopleComparator());
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
    static class People {
        public int id;
        public String province;
        public int money;
        
        public People(int id, String province, int money) {
            this.id = id;
            this.province = province;
            this.money = money;
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

    static class PeopleComparator implements Comparator<People> {

        @Override
        public int compare(People o1, People o2) {
            if (o1 != null && o2.province == null) {
                return 1;
            } else if (o1.province == null && o2.province != null) {
                return -1;
            } else {
                if (o1.money > o2.money) {
                    return 1;
                } else if (o1.money < o2.money) {
                    return -1;
                } else {
                    if (o1.province == null && o2.province == null) {
                        return 0;
                    } else if (o1.province.equalsIgnoreCase("jx")) {
                        return 1;
                    } else if (o2.province.equalsIgnoreCase("jx")) {
                        return -1;
                    } else {
                        return o1.province.compareToIgnoreCase(o2.province);
                    }
                }
            }
        }

    }

}


/*
test begin inputTotal: 1000000, n: 100
test data inputList.size(): 1000000
-------JDK Arrays.sort max n with Comparable-------
cosume: 984 ms
resultArray.length: 100
resultArray of first ten : [id=999993 province=jx money=99999, id=999992 province=jx money=99999, id=999999 province=jx money=99999, id=999990 province=sh money=99999, id=999998 province=sh money=99999, id=999997 province=sh money=99999, id=999994 province=bj money=99999, id=999991 province=bj money=99999, id=999987 province=sh money=99998, id=999986 province=sh money=99998]
-------MaxNAlgorithm Array max n with Comparable-------
cosume: 94 ms
resultArray.length: 100
resultArray of first ten : [id=999999 province=jx money=99999, id=999992 province=jx money=99999, id=999993 province=jx money=99999, id=999990 province=sh money=99999, id=999998 province=sh money=99999, id=999997 province=sh money=99999, id=999994 province=bj money=99999, id=999991 province=bj money=99999, id=999987 province=sh money=99998, id=999986 province=sh money=99998]

*/
