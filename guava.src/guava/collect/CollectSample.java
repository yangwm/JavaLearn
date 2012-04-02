/**
 * 
 */
package guava.collect;

import java.util.Arrays;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Multiset;
import com.google.common.primitives.Ints;

/**
 * 
 * 
 * @author yangwm Mar 19, 2012 12:01:36 AM
 */
public class CollectSample {
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        ImmutableList<Integer> abcList = ImmutableList.of(2, 2, 4, 6, 8, 15);
        System.out.println(abcList);
        
        ImmutableSet<String> abcSet = ImmutableSet.of("a", "b", "c", "a", "c");
        System.out.println(abcSet);
        System.out.println(ImmutableSet.copyOf(abcSet));
        
        ImmutableMap<String, Integer> abcMap = ImmutableMap.of("a", 1, "b", 2);
        System.out.println(abcMap);
        
        Multiset<Integer> abcMultiset = HashMultiset.create();
        abcMultiset.addAll(abcList);
        System.out.println(abcMultiset);
        
        List<String> theseElements = Lists.newArrayList("alpha", "beta", "gamma");
        System.out.println(theseElements);

        String lastAdded = Iterables.getLast(theseElements);
        System.out.println(lastAdded);
        
        System.out.println(Lists.reverse(theseElements));
        
        // concatenated has elements 1, 2, 3, 4, 5, 6
        Iterable<Integer> concatenated = Iterables.concat(
                Ints.asList(1, 2, 3), 
                Ints.asList(4, 5, 6));
        System.out.println(concatenated);
        
        BiMap<Long, String> biMap = HashBiMap.create();
        biMap.put(new Long(1), "yangwm");
        System.out.println(biMap.inverse().get("yangwm"));

        ImmutableSet<String> digits = ImmutableSet.of("zero", "one", "two", "three", "four",
                "five", "six", "seven", "eight", "nine");
        Function<String, Integer> lengthFunction = new Function<String, Integer>() {
            public Integer apply(String string) {
                return string.length();
            }
        };
        ImmutableListMultimap<Integer, String> digitsByLength = Multimaps.index(digits, lengthFunction);
          /*
           * digitsByLength maps:
           *  3 => {"one", "two", "six"}
           *  4 => {"zero", "four", "five", "nine"}
           *  5 => {"three", "seven", "eight"}
           */
        System.out.println(digitsByLength);
    }

}
