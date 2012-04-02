package basics.essential.ios;

import java.io.Console;
import java.util.Arrays;
import java.io.IOException;

public class Password {
    
    public static void main (String args[]) throws IOException {

        Console c = System.console();
        if (c == null) {
            System.err.println("No console.");
            System.exit(1);
        }

        String login = c.readLine("Enter your login: ");
        char [] oldPassword = c.readPassword("Enter your old password: ");

        if (verify(login, oldPassword)) {
            boolean noMatch;
            do {
                char [] newPassword1 =
                    c.readPassword("Enter your new password: ");
                char [] newPassword2 =
                    c.readPassword("Enter new password again: ");
                noMatch = ! Arrays.equals(newPassword1, newPassword2);
                if (noMatch) {
                    c.format("Passwords don't match. Try again.%n");
                } else {
                    change(login, newPassword1);
                    c.format("Password for %s changed.%n", login);
                }
                Arrays.fill(newPassword1, ' ');
                Arrays.fill(newPassword2, ' ');
            } while (noMatch);
        }

        Arrays.fill(oldPassword, ' ');

    }

    //Dummy verify method. 
    static boolean verify(String login, char[] password) {
        return true;
    }

    //Dummy change method.
    static void change(String login, char[] password) {}
}/*:
Enter your login: yangwm
Enter your old password:
Enter your new password:
Enter new password again:
Passwords don't match. Try again.
Enter your new password:
Enter new password again:
Password for yangwm changed.
*/
