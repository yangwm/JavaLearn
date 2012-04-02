package jdyn.javassist.batch;

import java.util.ArrayList;
import java.util.List;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.NotFoundException;
import javassist.Translator;

/**
 * Translator to add timing information to method calls. Each method in each
 * class is matched against the supplied pattern, and those matching the
 * pattern are modified. The pattern can be in several forms, including
 * "method-name", "return-type method-name", "method-name(parm-type,...)", and
 * "method-name{parm-type,...}" (where the last variation lists only required
 * parameters, which may be in any order).
 * 
 * @author yangwm in Dec 8, 2009 2:07:58 PM
 */
public class TimingTranslator implements Translator
{
    private final MatchPattern m_namePattern;
    private final String m_returnMatch;
    private final String[] m_argPositionMatches;
    private final String[] m_argFloatingMatches;
    
    /**
     * Constructor.
     *
     * @param pattern match pattern for methods to be modified
     */
    public TimingTranslator(String pattern) {
        
        // first check for space delimitor between return type and method name
        String remain = pattern;
        int split = remain.indexOf(' ');
        if (split > 0) {
            m_returnMatch = remain.substring(0, split);
            remain = remain.substring(split+1);
        } else {
            m_returnMatch = null;
        }
        
        // find split between method name and optional parameter type matches
        split = remain.indexOf('(');
        boolean positional;
        char close;
        if (split >= 0) {
            positional = true;
            close = ')';
        } else {
            split = remain.indexOf('[');
            positional = false;
            close = ']';
        }
        
        // split off remainder for method name
        if (split < 0) {
            
            // method name only, no parameter pattern
            m_namePattern = new MatchPattern(remain);
            m_argPositionMatches = null;
            m_argFloatingMatches = null;
            
        } else {
            
            // set method name pattern
            if (split == 0) {
                m_namePattern = null;
            } else {
                m_namePattern = new MatchPattern(remain.substring(0, split));
                remain = remain.substring(split+1);
            }
            
            // make sure the parameter list is well-formed
            if (remain.charAt(remain.length()-1) == close) {
                
                // process match patterns for method parameters
                remain = remain.substring(0, remain.length()-1);
                List<String> matches = new ArrayList<String>();
                while (remain != null) {
                    split = remain.indexOf(',');
                    String param;
                    if (split >= 0) {
                        param = remain.substring(0, split).trim();
                        remain = remain.substring(split+1).trim();
                    } else {
                        param = remain;
                        remain = null;
                    }
                    if (param.length() > 0) {
                        matches.add(param);
                    } else {
                        matches.add(null);
                    }
                }
                
                // convert result to array and store as appropriate type
                String[] types = new String[matches.size()];
                types = (String[])matches.toArray(types);
                if (positional) {
                    m_argPositionMatches = types;
                    m_argFloatingMatches = null;
                } else {
                    m_argPositionMatches = null;
                    m_argFloatingMatches = types;
                }
                
            } else {
                throw new IllegalArgumentException
                    ("Invalid method match pattern: " + pattern);
            }
        }
    }
    
    /**
     * Check if the method return type matches the supplied type.
     *
     * @param meth method to be checked for return type match
     */
    private boolean matchType(CtMethod meth) throws NotFoundException {
        if (m_returnMatch == null) {
            return true;
        } else {
            return m_returnMatch.equals(meth.getReturnType().getName());
        }
    }
    
    /**
     * Check if the parameters for a method match the supplied pattern.
     *
     * @param meth method to be checked for parameter match
     */
    private boolean matchParameters(CtMethod meth) throws NotFoundException {
                
        // check parameter types against pattern
        CtClass[] args = meth.getParameterTypes();
        if (m_argPositionMatches != null) {
            
            // verify fixed parameter matches
            if (m_argPositionMatches.length == args.length) {
                for (int i = 0; i < m_argPositionMatches.length; i++) {
                    if (!m_argPositionMatches[i].equals(args[i].getName())) {
                        return false;
                    }
                }
            } else {
                return false;
            }
            
        }
        if (m_argFloatingMatches != null &&
            m_argFloatingMatches.length <= args.length) {
            
            // get parameter type names as array
            String[] types = new String[args.length];
            for (int i = 0; i < m_argFloatingMatches.length; i++) {
                types[i] = args[i].getName();
            }
            
            // compare parameters with floating parameter matches
            boolean[] founds = new boolean[m_argFloatingMatches.length];
            for (int i = 0; i < m_argFloatingMatches.length; i++) {
                for (int j = 0; j < args.length; j++) {
                    if (m_argFloatingMatches[i].equals(types[j])) {
                        founds[i] = true;
                        break;
                    }
                }
            }
            
            // check if all floating parameters found
            for (int i = 0; i < founds.length; i++) {
                if (!founds[i]) {
                    return false;
                }
            }
            
        }
        return true;
    }
    
    /**
     * Check if the fully-qualified class and method name matches the supplied
     * pattern.
     *
     * @param meth method to be checked for name pattern match
     */
    private boolean matchName(CtMethod meth) {
        if (m_namePattern == null) {
            return true;
        } else {
            return m_namePattern.match(meth.getDeclaringClass().getName() +
                '.' + meth.getName());
        }
    }
    
    /**
     * Add timing information for method. This actually renames the original
     * method and replaces it with a timing wrapper.
     *
     * @param meth method to have added timing information
     */
    private void addTiming(CtMethod meth)
        throws NotFoundException, CannotCompileException {
        
        // rename old method and duplicate with original name
        String mname = meth.getName();
        meth.setName(mname+"$impl");
        CtClass clas = meth.getDeclaringClass();
        CtMethod mnew = CtNewMethod.copy(meth, mname, clas, null);
        
        // build body text of method to wrap call with timing
        String type = meth.getReturnType().getName();
        String body;
        if ("void".equals(type)) {
            body = "{ long start = System.currentTimeMillis();\n" +
                mname + "$impl($$);\nSystem.out.println" +
                "(\"Call to method " + mname + " took \" +" +
                "(System.currentTimeMillis()-start) + \" ms.\"); }";
        } else {
            body = "{ long start = System.currentTimeMillis();\n" +
                type + " result = " + mname + "$impl($$);\n" +
                "System.out.println(\"Call to method " + mname +
                " took \" + (System.currentTimeMillis()-start) + " +
                "\" ms.\");\nreturn result; }";
        }
        
        // add new method to class
        mnew.setBody(body);
        clas.addMethod(mnew);
    }
    
    /* (non-Javadoc)
     * @see javassist.Translator#start(javassist.ClassPool)
     */
    @Override
    public void start(ClassPool pool) {}

    /* (non-Javadoc)
     * @see javassist.Translator#onWrite(javassist.ClassPool, java.lang.String)
     */
    @Override
    public void onWrite(ClassPool pool, String cname)
        throws NotFoundException, CannotCompileException {
        
        // loop through all methods declared in class
        CtClass clas = pool.get(cname);
        CtMethod[] meths = clas.getDeclaredMethods();
        for (int i = 0; i < meths.length; i++) {
            
            // check if method matches return type, parameters, and name pattern
            CtMethod meth = meths[i];
            if (matchType(meth) && matchParameters(meth) && matchName(meth)) {
                
                // handle the actual timing modification
                addTiming(meth);
            }
        }
    }
    
}
