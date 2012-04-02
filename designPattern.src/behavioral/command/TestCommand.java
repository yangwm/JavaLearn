//: behavioral/chain/TestCommand.java
package behavioral.command;

import java.util.HashMap;
import java.util.Map;

public class TestCommand {

    public static void main(String[] args) {
        CommandManager.executeCommand("go");
        CommandManager.executeCommand("startup");
    }

}

abstract class Command {
    public abstract void execute();
}
class StartupCommand extends Command {
    @Override
    public void execute() {
        System.out.println("startup system");
    }
}
class ShutdownCommand extends Command {
    @Override
    public void execute() {
        System.out.println("shutdown system");
    }
}

class CommandManager {
    private static Map<String, Command> map = new HashMap<String, Command>();
    
    static {
        map.put("startup", new StartupCommand());
        map.put("shutdown", new ShutdownCommand());
    }
    
    public static void executeCommand(String cmd) {
        Command command = map.get(cmd);
        if (null != command) {
            command.execute();
        } else {
            System.out.println("no have this command, warn!!!");
        }
    }
    
}
