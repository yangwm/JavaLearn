package collections;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangwm in Sep 22, 2009 5:36:06 PM
 */
public class ListUtilsUsage {
    
    /**
     * create by yangwm in Sep 22, 2009 1:33:10 PM
     * @param args
     */
    public static void main(String[] args) {
//        String[] strs = new String[] {"yangwm", "hehe", "missile", "oo", "yangwm"};
//        List<String> strList1 = new ArrayList<String>(Arrays.asList(strs));
//        
//        String[] strs2 = new String[] {"yangwm", "wawa", "hehe", "jojo", "hehe"};
//        List<String> strList2 = new ArrayList<String>(Arrays.asList(strs2));
//        
//        System.out.println("strList1=" + strList1);
//        System.out.println("strList2=" + strList2);
//        
//        System.out.println("ListUtils------------------");
//        System.out.println("intersection:" + ListUtils.intersection(strList1, strList2));
//        System.out.println("removeAll:" + ListUtils.removeAll(strList1, strList2));
//        System.out.println("retainAll:" + ListUtils.retainAll(strList1, strList2));
//        System.out.println("subtract:" + ListUtils.subtract(strList1, strList2));
//        System.out.println("sum:" + ListUtils.sum(strList1, strList2));
//        System.out.println("union:" + ListUtils.union(strList1, strList2));
//
//        System.out.println("CollectionUtils------------------");
//        System.out.println("intersection:" + CollectionUtils.intersection(strList1, strList2));
//        System.out.println("removeAll:" + CollectionUtils.removeAll(strList1, strList2));
//        System.out.println("retainAll:" + CollectionUtils.retainAll(strList1, strList2));
//        System.out.println("subtract:" + CollectionUtils.subtract(strList1, strList2));
//        //System.out.println("sum:" + CollectionUtils.sum(strList1, strList2));
//        System.out.println("union:" + CollectionUtils.union(strList1, strList2));
        
        String uids = "1,2,3";
        
        System.out.println("uids:" + uids);
        String[] strUids = uids.split(",");
        long[] longUids = new long[strUids.length]; 
        for (int i = 0; i < strUids.length; i++) {
            longUids[i] = Long.parseLong(strUids[i]);
        }
        if (longUids.length < 2) {
            
        }
        
        int i = 0;
        long[] result = new long[] {1, 2};//friendService.getUltimateFriendIds(longUids[i], true);
        List<Long> resultList = new ArrayList<Long>(result.length);
        for (Long f : result) {
            resultList.add(f);
        }
        
        for (i = 1; i < longUids.length; i++) {
            long[] friends = new long[] {1, 2, 4};//friendService.getUltimateFriendIds(longUids[i], true);
            List<Long> friendsList = new ArrayList<Long>(friends.length);
            for (Long f : friends) {
                friendsList.add(f);
            }
            resultList = org.apache.commons.collections.ListUtils.intersection(resultList, friendsList);
        }
        
        StringBuilder value = new StringBuilder();
        for (Long l : resultList) {
            value.append(l).append(",");
        }
        value.setLength(value.length() - 1);
        System.out.println("intersectionUids:" + value);
        
    }

}

/*
strList1=[yangwm, hehe, missile, oo, yangwm]
strList2=[yangwm, wawa, hehe, jojo, hehe]
ListUtils------------------
intersection:[yangwm, hehe, hehe]
removeAll:[missile, oo]
retainAll:[yangwm, hehe, yangwm]
subtract:[missile, oo, yangwm]
sum:[missile, oo, yangwm, yangwm, wawa, jojo, hehe]
union:[yangwm, hehe, missile, oo, yangwm, yangwm, wawa, hehe, jojo, hehe]
CollectionUtils------------------
intersection:[hehe, yangwm]
removeAll:[yangwm, hehe, yangwm]
retainAll:[yangwm, hehe, yangwm]
subtract:[missile, oo, yangwm]
union:[missile, oo, hehe, hehe, wawa, jojo, yangwm, yangwm]
*/

