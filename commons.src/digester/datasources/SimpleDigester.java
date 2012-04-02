package digester.datasources;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.digester.Digester;

/**
 * Digester使用SAX来解析XML文档 
 * Digester引入了三个重要的概念：元素匹配模式、处理规则和对象栈。
 * 
 * @author yangwm in Mar 15, 2009 11:02:07 AM
 */
public class SimpleDigester {
    private static Set<DataSource> datasources = new HashSet<DataSource>();

    public void addDataSource(String name, String driver, String url, String username, String password) {
        DataSource source = new DataSource();
        source.setName(name);
        source.setDriver(driver);
        source.setUrl(url);
        source.setUsername(username);
        source.setPassword(password);
        
        datasources.add(source);
    }

    public void run() throws Exception {
        Digester digester = new Digester();
        digester.setValidating(false); // default false 
        
        digester.push(this);
        
        digester.addCallMethod("datasources/datasource", "addDataSource", 5);
        digester.addCallParam("datasources/datasource/name", 0);
        digester.addCallParam("datasources/datasource/driver", 1);
        digester.addCallParam("datasources/datasource/url", 2);
        digester.addCallParam("datasources/datasource/username", 3);
        digester.addCallParam("datasources/datasource/password", 4);
        
        digester.parse(SimpleDigester.class.getResourceAsStream("datasource.xml"));
    }

    public static void main(String[] args) throws Exception {
        SimpleDigester di = new SimpleDigester();

        di.run();

        for (DataSource data : datasources) {
            System.out.println("--------------data:");
            System.out.println("Name = " + data.getName());
            System.out.println("Driver = " + data.getDriver());
            System.out.println("Url = " + data.getUrl());
            System.out.println("Username = " + data.getUsername());
            System.out.println("Password = " + data.getPassword());
        }
    }
}
