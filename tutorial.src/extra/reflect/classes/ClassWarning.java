package extra.reflect.classes;

import java.lang.reflect.Method;

public class ClassWarning {
    void m() {
    try {
        Class c = ClassWarning.class;
        //@SuppressWarnings("unchecked")
        Method m = c.getMethod("m");  // warning

        // production code should handle this exception more gracefully
    } catch (NoSuchMethodException x) {
            x.printStackTrace();
        }
    }
}

