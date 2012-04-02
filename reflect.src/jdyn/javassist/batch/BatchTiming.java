package jdyn.javassist.batch;

import javassist.*;

public class BatchTiming
{
    public static void main(String[] args) {
        if (args.length >= 2) {
            try {
                
                // set up class loader with translator
                Translator xlat = new TimingTranslator(args[0]);
                ClassPool pool = ClassPool.getDefault(xlat);
                Loader loader = new Loader(pool);
                    
                // invoke the "main" method of the application class
                String[] pargs = new String[args.length-2];
                System.arraycopy(args, 2, pargs, 0, pargs.length);
                loader.run(args[1], pargs);
                
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
            
        } else {
            System.out.println
                ("Usage: BatchTiming method-pattern main-class args...");
        }
    }
} 
