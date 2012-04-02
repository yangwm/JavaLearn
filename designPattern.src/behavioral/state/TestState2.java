//: behavioral/state/TestState2.java

package behavioral.state;

public class TestState2 {
    public static void main(String[] args) {
        BBSUser2 u = new BBSUser2();
        u.publish();

        u.setState(new NormalState());
        u.publish();

        u.setState(new BlockedState());
        u.publish();

        u.setState(new NewComerState());
        u.publish();
    }
}

abstract class State {
    public abstract void action();
}
class GuestState extends State {
    public void action() {
        System.out.println("You are Guest, Please Login");
    }
}
class NormalState extends State {
    public void action() {
        System.out.println("You are Normal, Send OK");
    }
}
class BlockedState extends State {
    public void action() {
        System.out.println("You are Blocked, Send Failure");
    }
}
class NewComerState extends State {
    public void action() {
        System.out.println("You are new man, Please study , three day can be Send");
    }
}

class BBSUser2 {
    private State state;

    public BBSUser2() {
        this.state = new GuestState();
    }

    public BBSUser2(State state) {
        this.state = state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void publish() {
        state.action();
    }
}
