package beanutils.sample;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;


public class SampleObjectUsage {

    /**
     * create by yangwm in Feb 23, 2009 3:45:49 PM
     * @param args
     */
    public static void main(String[] args) {
        SampleObject a = new SampleObject();
        a.setDisplay("first one");
        a.setName("A");
        a.setNum(5);
        a.setWords("goto".toCharArray());
        SampleObjectA b = new SampleObjectA();
        b.setDisplay("nested property");
        b.setNum(new Double(2.0));
        b.setName("sampleA");
        a.setSample(b);
        
        try {
            Map descMap = BeanUtils.describe(a);
            System.out.println(descMap);
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
            
    }
}

/*
{num=5, words=g, tag=false, name=A, map={port=80, home=localhost}, class=class beanutils.sample.SampleObject, display=first one, sample=beanutils.sample.SampleObjectA@eee36c}
*/
