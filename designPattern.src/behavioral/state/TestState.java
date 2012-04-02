//: behavioral/state/TestState.java

package behavioral.state;

public class TestState {
    public static void main(String[] args) {
        BBSUser u = new BBSUser();
        u.publish();
        u.setState("Normal");
        u.publish();
    }
}

class BBSUser {
    private String state = "Guest";

    public void setState(String state) {
        this.state = state;
    }

    public void publish() {
        if (state.equals("Guest")) {
            System.out.println("You are Guest, Please Login");
        }
        if (state.equals("Normal")) {
            System.out.println("You are Normal, Send OK");
        }
        if (state.equals("Blocked")) {
            System.out.println("You are Blocked, Send Failure");
        }
    }
}
