package org.example;

public class ListEmployeesCommand implements Command {
    private final DbService service;

    public ListEmployeesCommand(DbService service) {
        this.service = service;
    }
    
    @Override
    public String execute() {
        var employees = service.getAllEmployees();
        System.out.println(String.format("%-30s %-30s %-50s", "NAME", "ROLE", "EMAIL"));
        for (var employee:employees) {
            System.out.println(employee);
        }
        return "";
    }

    @Override
    public String desc() {
        return "- Lists all employees in the system.";
    }
}
