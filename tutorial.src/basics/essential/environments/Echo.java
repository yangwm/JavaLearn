
package basics.essential.environments;

// {args:java basics.essential.environments.Echo "Drink Hot Java"}
public class Echo {

    /**
     * @param args
     */
    public static void main(String[] args) {
        for (String arg : args) {
            System.out.println(arg);
        }
    }

}
