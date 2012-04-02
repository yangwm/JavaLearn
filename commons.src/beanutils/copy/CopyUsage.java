/**
 * 
 */
package beanutils.copy;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import util.EntityUtil;

/**
 * @author yangwm in Jun 2, 2009 11:39:36 PM
 */
public class CopyUsage {

    /**
     * BeanUtils.copyProperties(Object dest, Object orig)得到预期结果的前提条件:
     * Origin Bean需提供getter方法，Destination Bean需提供setter方法；
     * Origin Bean中的属性类型可与Destination Bean中属性类型不同。
     * 
     * Origin Bean中String类型的strDate转换为Destination BeanString中java.util.Date类型的strDate时会抛一下异常：
     * Exception in thread "main" org.apache.commons.beanutils.ConversionException: DateConverter does not support default String to 'Date' conversion.
     * at org.apache.commons.beanutils.converters.DateTimeConverter.toDate(DateTimeConverter.java:468)
     * 
     * create by yangwm in Jun 2, 2009 11:39:36 PM
     * @param args
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     */
    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException {
        OriginBean origBean = new OriginBean();
        origBean.setId(1);
        origBean.setName("杨武明name");
        origBean.setBeginDate(new java.util.Date());
        origBean.setInterval(5);
        origBean.setEndDate(new java.sql.Date(new java.util.Date().getTime()));
        origBean.setDescribe(new StringBuilder("describe"));
        origBean.setCreateDate(new java.sql.Timestamp(new java.util.Date().getTime()));
        origBean.setStrDate("2009-05-22 14:00:00");
        origBean.setSqlDate("2009-05-22");
        origBean.setSqlTimestamp("2009-05-22 14:00:00");
        System.out.println(EntityUtil.toString(origBean));
        
        DestinationBean destBean = new DestinationBean();
        BeanUtils.copyProperties(destBean, origBean);

        System.out.println(EntityUtil.toString(destBean));
    }

}
