package basics.essential.environments;

// args{java basics.essential.environments.Env JAVA_HOME}
public class Env {

    /**
     * @param args
     */
    public static void main(String[] args) {
        for (String env: args) {
            String value = System.getenv(env);
            if (value != null) {
                System.out.format("%s=%s%n", env, value);
            } else {
                System.out.format("%s is not assigned.%n", env);
            }
        }
    }

}
/*
Java_Home=D:\Program Files\Java\jdk1.6.0_01
*///~
