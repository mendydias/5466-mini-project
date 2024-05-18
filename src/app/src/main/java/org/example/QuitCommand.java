package org.example;

public class QuitCommand implements Command {
    private final Runnable callback;
    
    public QuitCommand(Runnable callback) {
        this.callback = callback;
    }
    
    @Override
    public String execute() {
        callback.run();
        return "";
    }
    
    @Override
    public String desc() {
        return "- Quits the program.";
    }
}
