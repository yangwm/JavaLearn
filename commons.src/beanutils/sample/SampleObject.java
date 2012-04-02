package beanutils.sample;

import java.util.HashMap;
import java.util.Map;


/**
 * SampleObject contains some types of member
 * varaibles:String,int,Array,Map,Object(self defined),just for test usaged of
 * 
 * @author yangwm in Feb 23, 2009 3:26:08 PM
 */
public class SampleObject {
    String name = null;
    String display = null;
    int num = -1;
    char[] words = { 'a', 'b', 'c', 'd' };
    boolean tag = false;
    Map<String, String> map = new HashMap<String, String>();
    SampleObjectA sample = null;

    /**
     * default constructor. initialized members of map and sample.
     */
    public SampleObject() {
        map.put("home", "localhost");
        map.put("port", "80");
    }

    // the following is getters and setters
    /**
     * @return Returns the display.
     */
    public String getDisplay() {
        return display;
    }

    /**
     * @param display
     *            The display to set.
     */
    public void setDisplay(String display) {
        this.display = display;
    }

    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Returns the num.
     */
    public int getNum() {
        return num;
    }

    /**
     * @param num The num to set.
     */
    public void setNum(int num) {
        this.num = num;
    }

    /**
     * @return Returns the words.
     */
    public char[] getWords() {
        return words;
    }

    /**
     * @param words The words to set.
     */
    public void setWords(char[] words) {
        this.words = words;
    }

    /**
     * @return Returns the tag.
     */
    public boolean isTag() {
        return tag;
    }

    /**
     * @param tag The tag to set.
     */
    public void setTag(boolean tag) {
        this.tag = tag;
    }

    /**
     * @return Returns the map.
     */
    public Map<String, String> getMap() {
        return map;
    }

    /**
     * @param map The map to set.
     */
    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    /**
     * @return Returns the sample.
     */
    public SampleObjectA getSample() {
        return sample;
    }

    /**
     * @param sample The sample to set.
     */
    public void setSample(SampleObjectA sample) {
        this.sample = sample;
    }
    
}
