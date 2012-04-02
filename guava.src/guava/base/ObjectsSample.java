/**
 * 
 */
package guava.base;

import json.ext.Content;

import com.google.common.base.Objects;

/**
 * 
 * 
 * @author yangwm Mar 18, 2012 6:46:22 PM
 */
public class ObjectsSample {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Content content1 = new Content(1, "a");
        
        String value = Objects.toStringHelper(content1)
            .add("contentType", content1.getContentType())
            .add("contentDetails", content1.getContentDetails()).toString();
        System.out.println(value);
        
        Content content2 = new Content(1, "a");
        System.out.println(Objects.hashCode(content1, 1, 2, 3));
        System.out.println(Objects.hashCode(content2, 1, 2, 3));
        
        System.out.println(Objects.equal(content1, content2));
    }

}
