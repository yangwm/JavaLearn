package lang.math;

import org.apache.commons.lang.math.DoubleRange;
import org.apache.commons.lang.math.Fraction;
import org.apache.commons.lang.math.IntRange;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang.math.Range;

/**
 *  在org.apache.commons.lang.math包中，一共有11个类。可以分成四组：
    1. 处理分数的Fraction类；
    2.处理数值的NumberUtils和IEEE754rUtils类，这里IEEE745r代表的是IEEE 754的标准，是一种浮点数的处理标准。
    3.处理随机数的JVMRandom和RandomUtils类。
    4.处理数值范围的Range, DoubleRange, FloatRange, IntRange, LangRange, NumberRange类。
 *   
 * @author yangwm in Feb 20, 2009 4:14:57 PM
 */
public class MathTest {
    
    public static void main(String[] args) {
        //new MathTest().FractionDemo();
        //new MathTest().NumberUtilsDemo();
        //new MathTest().RandomUtilsDemo();
        new MathTest().NumberRangeDemo();
    }
    
    /**
     * Fraction类能很方便地处理分数，并能进行分数的约分，加减乘除和指数运算以及求相对值
     * 
     * create by yangwm in Feb 20, 2009 4:15:25 PM
     */
    private void FractionDemo() {
        System.out.println("-------------- FractionDemo -------------------");
        Fraction fraction = Fraction.getFraction(0.5);
        System.out.println(fraction.getNumerator());
        System.out.println(fraction.getDenominator());

        fraction = Fraction.getFraction("1/2");
        System.out.println(fraction.doubleValue());

        fraction = Fraction.getFraction(1, 2);
        System.out.println(fraction.doubleValue());

        fraction = Fraction.getFraction(1, 1, 2);
        System.out.println(fraction.doubleValue());

        fraction = Fraction.getFraction(2, 4);
        System.out.println(fraction.doubleValue());
        fraction = Fraction.getReducedFraction(2, 4);
        System.out.println(fraction.doubleValue());

        System.out.println(" mathematic : ");
        System.out.println(Fraction.getFraction(-1, 2).abs());
        System.out.println(Fraction.getFraction(1, -2).abs());
        System.out.println(Fraction.getFraction(1, 2).add(Fraction.getFraction(1, 2)));
        System.out.println(Fraction.getFraction(1, 2).subtract(Fraction.getFraction(1, 2)));
        System.out.println(Fraction.getFraction(1, 2).multiplyBy(Fraction.getFraction(1, 2)));
        System.out.println(Fraction.getFraction(1, 2).divideBy(Fraction.getFraction(1, 2)));
        System.out.println(Fraction.getFraction(1, 2).pow(2));
    }
    
    /**
     * NumberUtils的功能相对来说就有点鸡肋了，他能通过createXXX(String str)创建各种类型的数值，
     * 即使你传入的参数是0X123这样代表16进制的数，它也能正确解析出来。
     * 同时它还具有获取数组最大最小数的功能。
     * 
     * create by yangwm in Feb 20, 2009 4:19:34 PM
     */
    private void NumberUtilsDemo() {
        System.out.println("-------------- NumberUtilsDemo -------------------");
        System.out.println("Is 0x3F a number? " + NumberUtils.isNumber("0x3F"));
        double[] array = { 1.0, 3.4, 0.8, 7.1, 4.6 };
        double max = NumberUtils.max(array);
        double min = NumberUtils.min(array);
        System.out.println("Max of array is: " + max);
        System.out.println("Min of array is: " + min);
        System.out.println();
    }
    
    /**
     * JVMRandom继承了java.util.Random类，其功能和Random差不多，只不过封装了返回不同数据类型的方法而已。
     * 而RandomUtils则把JVMRandom的方法静态化了。
     * 
     * create by yangwm in Feb 20, 2009 4:22:35 PM
     */
    private void RandomUtilsDemo() {
        System.out.println("-------------- RandomUtilsDemo -------------------");
        for (int i = 0; i < 5; i++) {
            System.out.println(RandomUtils.nextInt(100));
        }
        System.out.println();
    }
    
    /**
     * Range是一个abstract类，主要处理数值范围的。
     * DoubleRange, FloatRange, IntRange, LangRange和NumberRange都继承了Range类，它们差不多，只是处理的数据类型不同而已。
     * 所以看一个类的使用方法就等于看了所有类的使用方法。
     * 
     * create by yangwm in Feb 20, 2009 4:23:13 PM
     */
    private void NumberRangeDemo() {
        System.out.println("-------------- NumberRangeDemo -------------------");
        Range normalScoreRange = new DoubleRange(90, 120);
        double score1 = 102.5;
        double score2 = 79.9;
        System.out.println("Normal score range is: " + normalScoreRange);
        System.out.println("Is " + score1 + " a normal score? " + normalScoreRange.containsDouble(score1));
        System.out.println("Is " + score2 + " a normal score? " + normalScoreRange.containsDouble(score2));
        System.out.println(normalScoreRange.overlapsRange(new IntRange(92,119)));
    }
    
}

/*
-------------- NumberRangeDemo -------------------
Normal score range is: Range[90.0,120.0]
Is 102.5 a normal score? true
Is 79.9 a normal score? false
true

*/