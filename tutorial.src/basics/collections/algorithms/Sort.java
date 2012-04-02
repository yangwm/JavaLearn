
// {args:Sort i walk the line}
package basics.collections.algorithms;

import java.util.Arrays;
import java.util.List;
import java.util.Collections;

public class Sort {
    public static void main(String[] args) {
        List<String> list = Arrays.asList(args);
        Collections.sort(list);
        System.out.println(list);
    }
}/*
[Sort, i, line, the, walk]
*///~
