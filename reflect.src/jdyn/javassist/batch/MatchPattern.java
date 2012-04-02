package jdyn.javassist.batch;

import java.util.ArrayList;
import java.util.List;

/**
 * Pattern matcher for simple strings with '*' wildcard characters. The supplied
 * template may take the form "xxx" for an exact match, "xxx*" for a match on
 * leading characters only, "*xxx" to match on trailing characters only, or
 * "xxx*yyy" to match on both leading and trailing. It can also include multiple
 * '*' characters when more than one part of the match is wildcarded.
 * 
 * @author yangwm in Dec 8, 2009 2:07:58 PM
 */
public class MatchPattern
{
    /** Text components to be matched. */
    private final String[] m_components;
    
    /** Flag for leading text to be matched. */
    private final boolean m_isLeadText;
    
    /** Flag for trailing text to be matched. */
    private final boolean m_isTrailText;
    
    /**
     * Constructor.
     *
     * @param template match text template
     */
    public MatchPattern(String template) {
        int mark = template.indexOf('*');
        List<String> comps = new ArrayList<String>();
        if (mark < 0) {
            
            // set up for exact match
            comps.add(template);
            m_isLeadText = true;
            m_isTrailText = true;
            
        } else {
            
            // handle leading wildcard
            int base = 0;
            if (mark == 0) {
                m_isLeadText = false;
                base = 1;
                mark = template.indexOf('*', 1);
            } else {
                m_isLeadText = true;
            }
            
            // loop for all text components to be matched
            int limit = template.length() - 1;
            while (mark > 0) {
                comps.add(template.substring(base, mark));
                base = mark + 1;
                if (mark == limit) {
                    break;
                }
                mark = template.indexOf('*', base);
            }
            comps.add(template.substring(base));
            m_isTrailText = mark != limit;
            
        }
        
        // save array of text components to be matched
        m_components = (String[])comps.toArray(new String[comps.size()]);
    }
    
    /**
     * Comparison test.
     *
     * @param match text to be matched against template
     */
    public boolean match(String match) {
        
        // first check for required leading text
        int start = 0;
        int end = match.length();
        int index = 0;
        if (m_isLeadText) {
            if (match.startsWith(m_components[0])) {
                start = m_components[0].length();
                index = 1;
            } else {
                return false;
            }
        }
        
        // next check for required trailing text
        int limit = m_components.length;
        if (m_isTrailText) {
            if (match.endsWith(m_components[--limit])) {
                end -= m_components[limit].length();
            } else {
                return false;
            }
        }
        
        // finally match all floating comparison components
        while (index < limit) {
            String comp = m_components[index++];
            start = match.indexOf(comp, start);
            if (start >= 0) {
                start += comp.length();
                if (start > end) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }
}
