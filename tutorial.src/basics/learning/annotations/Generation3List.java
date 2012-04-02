package basics.learning.annotations;

@ClassPreamble (
    author = "John Doe",
    date = "3/17/2002",
    currentRevision = 6,
    lastModified = "4/12/2004",
    lastModifiedBy = "Jane Doe",
    reviewers = {"Alice", "Bob", "Cindy"} // Note array notation
)
public class Generation3List extends Generation2List {

// class code goes here
    
// Javadoc comment follows
    /**
     * @deprecated
     * explanation of why it was deprecated
     */
    @Deprecated
    static void deprecatedMethod() { }

    // use a deprecated method and tell 
    // compiler not to generate a warning
    @SuppressWarnings({"unchecked", "deprecation", "static-access"})
    void useDeprecatedMethod() {
        this.deprecatedMethod(); //deprecation warning - suppressed
    }

    // mark method as a superclass method
    // that has been overridden
    @Override 
    int overriddenMethod() { return 0; }

}


