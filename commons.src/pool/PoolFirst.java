/**
 * 
 */
package pool;

import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.log4j.Logger;

/**
 * 
 * 
 * @author yangwm May 15, 2011 10:29:36 PM
 */
public class PoolFirst {

    /**
     * 
     */
    private static final Logger log = Logger.getLogger(PoolFirst.class);
    
    private final GenericObjectPool internalPool;
    
    public PoolFirst(int minConnCount, int maxConnCount) {
        this(newCustomPoolConfig(minConnCount, maxConnCount), 
                new CustomJedisFactory());
    }

    public PoolFirst(GenericObjectPool.Config poolConfig, CustomJedisFactory factory) {
        this.internalPool = new GenericObjectPool(factory, poolConfig);
    }

    public Object getResource() {
        try {
            return (Object)internalPool.borrowObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void returnResource(Object o, boolean isBroken) {
        try {
            if (isBroken) {
                internalPool.invalidateObject(o);
            } else {
                internalPool.returnObject(o);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
        try {
            internalPool.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    /**
     * GenericObjectPool.Config custom new.
     */
    public static GenericObjectPool.Config newCustomPoolConfig(int minConnCount, int maxConnCount) {
        GenericObjectPool.Config poolConfig = new GenericObjectPool.Config();
        
        poolConfig.maxActive = maxConnCount;

        poolConfig.maxIdle = minConnCount; // when return obj,the obj may be
        // destroy if current idle > maxIdle

        poolConfig.minIdle = minConnCount;// this may have no effect,cause the evictor is
        // disabled by default

        poolConfig.maxWait = 500;
        poolConfig.whenExhaustedAction = GenericObjectPool.WHEN_EXHAUSTED_BLOCK;

        poolConfig.lifo = false;
        
        /*
         * validateObject
         */
        //poolConfig.testOnBorrow = true;
        //poolConfig.testOnReturn = true;
        //poolConfig.testWhileIdle = false;
        
        return poolConfig;
    }

    
    /**
     * PoolableObjectFactory custom impl.
     */
    private static class CustomJedisFactory extends BasePoolableObjectFactory {

        public CustomJedisFactory() {
            super();
        }

        @Override
        public Object makeObject() throws Exception {
            Object o = new Object();
            System.out.println("CustomJedisFactory makeObject");
            return o;
        }

        @Override
        public void destroyObject(final Object obj) throws Exception {
            System.out.println("CustomJedisFactory destroyObject");
        }

        @Override
        public boolean validateObject(final Object obj) {
            System.out.println("CustomJedisFactory validateObject");
            return false; // if return false will call destroyObject 
        }
    }


    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        PoolFirst pool = new PoolFirst(5, 10);
        for (int i = 0; i < 2; i++) {
            boolean isBroken = false;
            Object o = null;
            try {
                o = pool.getResource();
            } catch (Exception e) {
                isBroken = true;
                throw e;
            } finally {
                pool.returnResource(o, isBroken);
            }
        }
        Thread.sleep(2000);
    }

}
