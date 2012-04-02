package security;
import java.security.Provider;
import java.security.Security;

/**
 * Test JCE, find providers and algorithms supported.
 * <p/>
 * .
 *
 * @author Roedy Green, Canadian Mind Products
 * @version 1.0 2009-01-01 - initial version
 * @since 2009-01-01
 */

public class TestJCE {

    /**
     * Prepare a list of providers and services.
     * 
     * create by yangwm in Apr 26, 2009 12:10:04 AM
     * @param args
     */
    public static void main(String[] args) {
        System.out.println( "Algorithms Supported in Java "
                          + System.getProperty( "java.version" )
                          + " JCE." );
        System.out.println( "====================" );
        // heading
        System.out.println( "Provider: type.algorithm -> className"
                     + "\n  aliases:"
                     + "\n  attributes:\n" );
        
        // discover providers
        Provider[] providers = Security.getProviders();
        for ( Provider provider : providers )
        {
            System.out.println( "<><><>" + provider + "<><><>\n" );
            // discover services of each provider
            for ( Provider.Service service : provider.getServices() )
            {
                System.out.println( service );
            }
            System.out.println();
        }

    }

}
