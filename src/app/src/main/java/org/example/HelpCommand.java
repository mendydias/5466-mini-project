package org.example;

import java.util.Map;

public class HelpCommand implements Command {
    private final Map<String, Command> commands;

    public HelpCommand(Map<String, Command> commands) {
        this.commands = commands;
    }

    @Override
    public String execute() {
        StringBuilder helpmsg = new StringBuilder();
        helpmsg.append("The following commands are available:\n\n");
        for (Map.Entry<String, Command> command : commands.entrySet()) {
            String description = command.getValue().desc();
            if (description != null) {
                helpmsg.append(command.getKey() + " " + description + "\n");
            }
        }
        return helpmsg.append("\n")
                .toString();
    }
    
    @Override
    public String desc() {
        return "- Print out this message.";
    }
}
