package basics.learning.annotations;
import java.lang.annotation.*; // import this to use @Documented

@Documented
@interface ClassPreamble {
    String author();
    String date();
    int currentRevision() default 1;
    String lastModified() default "N/A";
    String lastModifiedBy() default "N/A";
    String[] reviewers();  // Note use of array
}
