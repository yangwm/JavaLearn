/**
 * 
 */
package cglib.bean;

import java.lang.reflect.InvocationTargetException;

import net.sf.cglib.beans.BeanCopier;

import org.apache.commons.beanutils.BeanUtils;

import util.EntityUtil;

/**
 * 
 * 
 * @author yangwm Apr 16, 2010 9:56:17 AM
 */
public class BeanCopyTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        CopyBean source = new CopyBean();
        source.setId(1);
        source.setName("杨武明name");
        source.setBeginDate(new java.util.Date());
        source.setInterval(5);
        source.setDescribe(new StringBuilder("describe"));
        source.setStrDate("2009-05-22 14:00:00");
        System.out.println("source=" + EntityUtil.toString(source));
        
        BeanCopier copy = BeanCopier.create(CopyBean.class, CopyBean.class, false); 
        CopyBean target = new CopyBean(); 
        long t = System.currentTimeMillis(); 
        for (int i = 0; i < 100000; i++) { 
            copy.copy(source, target, null); 
        } 
        System.out.println((System.currentTimeMillis() - t) + ", target=" + EntityUtil.toString(target));
        
        CopyBean target2 = new CopyBean();
        t = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            try {
                BeanUtils.copyProperties(target2, source);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } 
        } 
        System.out.println((System.currentTimeMillis() - t) + ", target2=" + EntityUtil.toString(target2));
    }

}

/*
source=cglib.bean.CopyBean[<id=1,name=杨武明name,beginDate=Sat Apr 17 15:32:14 CST 2010,interval=5,describe=describe,strDate=2009-05-22 14:00:00>]
net.sf.cglib.beans.BeanCopier$BeanCopierKey$$KeyFactoryByCGLIB$$f32401fd
net.sf.cglib.empty.Object$$BeanCopierByCGLIB$$d27a2c22
15, target=cglib.bean.CopyBean[<id=1,name=杨武明name,beginDate=Sat Apr 17 15:32:14 CST 2010,interval=5,describe=describe,strDate=2009-05-22 14:00:00>]
5000, target2=cglib.bean.CopyBean[<id=1,name=杨武明name,beginDate=Sat Apr 17 15:32:14 CST 2010,interval=5,describe=describe,strDate=2009-05-22 14:00:00>]

*/

