package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class AppState {
    private boolean running = true;

    public boolean isRunning() {
        return running;
    }

    public void stop() {
        running = false;
    }
}

public class Composer {
    private static final Map<String, Command> CMD = new HashMap<>();
    private final AppState state = new AppState();

    public Composer() {
        CMD.put("register", new RegisterCommand(new DbService()));
        CMD.put("calcTenure", new CalculateTenureCommand(new DbService()));
        CMD.put("quit", new QuitCommand(() -> state.stop()));
        CMD.put("help", new HelpCommand(CMD));
    }

    public void greet() {
        System.out.println("Simple Employee Management System");
        System.out.println("Name: N. W. L. U. R. D. Nanayakakra");
        System.out.println("Reg. no.: 321424374");
        System.out.println("\nType your commands after the prompt. Type help to see available commands.");
    }

    public void play() {
        try (Scanner input = new Scanner(System.in)) {
            while (state.isRunning()) {
                System.out.print("> ");
                parseExecute(input.next());
            }
        }
    }

    private void parseExecute(String cmd) {
        Command command = CMD.getOrDefault(cmd, new DefaultCommand());

        System.out.println(command.execute() + "\n");
    }
}
