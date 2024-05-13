package org.example;

public class DefaultCommand implements Command {
    @Override
    public String execute() {
        return "Unexpected command. Type help to see a list of available commands or quit to exit.\n";
    }
    
    @Override
    public String desc() {
        return null;
    }
}
