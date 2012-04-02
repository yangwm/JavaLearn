package lang.text;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.text.StrSubstitutor;

public class TextTest {

    /**
     * create by yangwm in Feb 21, 2009 1:32:49 PM
     * @param args
     */
    public static void main(String[] args) {
        new TextTest().strSubstitutorDemo();
    }

    public void strSubstitutorDemo() {
        String systemString = StrSubstitutor.replaceSystemProperties(
                "You are running with java.version = ${java.version} and os.name = ${os.name}.");
        System.out.println(systemString);
        
        Map<String, String> valuesMap = new HashMap<String, String>();
        valuesMap.put("animal", "quick brown fox");
        valuesMap.put("target", "lazy dog");
        String templateString = "The ${animal} jumped over the ${target}.";
        StrSubstitutor sub = new StrSubstitutor(valuesMap);
        String resolvedString = sub.replace(templateString);
        System.out.println(resolvedString);
    }
    
}

/*
You are running with java.version = 1.6.0_10 and os.name = Windows XP.
The quick brown fox jumped over the lazy dog.
*/