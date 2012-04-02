/**
 * 
 */
package guice.print;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;

/**
 * @author yangwm in Jan 3, 2010 9:02:48 PM
 */
public class PrintModule implements Module {

    @Override
    public void configure(Binder binder) {
        //binder.bind(PrintService.class).to(PrintServiceImpl.class);
        //binder.bind(PrintService.class).toInstance(new PrintServiceImpl());
        binder.bind(PrintService.class).to(PrintServiceImpl.class).in(Scopes.SINGLETON);
        //binder.bind(PrintService.class).annotatedWith(Print.class).to(PrintServiceImpl.class).in(Scopes.SINGLETON);
        //binder.bind(PrintService.class).annotatedWith(Names.named("printNamed")).to(PrintServiceImpl.class).in(Scopes.SINGLETON);
    }
    
}
