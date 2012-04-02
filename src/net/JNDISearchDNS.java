
package net;

import java.util.Hashtable;
import java .util.Enumeration;

import javax.naming.*;
import javax.naming.directory.*;

/*
 * 用 JNDI 进行 DNS 查询 
 * IP信息和主机操作系统信息。MX对应邮件接受服务器，IP信息对应A 
 * 
 * 参见: http://www.blogjava.net/beansoft/archive/2007/09/22/147302.html
 */
public class JNDISearchDNS {

    public static void main(String args[]) {
       try {
           // Hashtable for environmental information
           Hashtable env = new Hashtable();

           // Specify which class to use for our JNDI provider
           env.put(Context. INITIAL_CONTEXT_FACTORY ,
                  "com.sun.jndi.dns.DnsContextFactory" );
           env.put(Context. PROVIDER_URL , "dns://202.106.46.151/"); // 把这个地址修改为你当前的DNS服务器的地址 
           //env.put(Context. PROVIDER_URL , "dns://100.4.0.100/");
           String dns_attributes[] = { "MX" , "A" , "HINFO" };

           // Get a reference to a directory context
           DirContext ctx = new InitialDirContext(env);
           Attributes attrs1 = ctx.getAttributes("google.com", dns_attributes);

           if (attrs1 == null ) {
              System. out .println( "主机没有上述信息" );
           } else {
              for ( int i = 0; i < dns_attributes.length ; i++) {
                  Attribute attr = attrs1.get(dns_attributes[i]);

                  if (attr != null ) {
                     System. out .print(dns_attributes[i] + ": \n" );
                     for (Enumeration vals = attr.getAll(); vals
                            .hasMoreElements();) {
                         System. out .println(vals.nextElement());
                     }
                  }

                  System. out .println( " " );
              }
           }
       } catch (Exception e) {
           e.printStackTrace();
           System.exit(1);
       }
    }
} 

/*
MX: 
10 google.com.s9b2.psmtp.com.
10 google.com.s9b1.psmtp.com.
10 google.com.s9a1.psmtp.com.
10 google.com.s9a2.psmtp.com.
 
A: 
64.233.189.104
64.233.189.147
64.233.189.99
64.233.189.103
 
 
*/
