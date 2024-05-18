package org.example;

import java.util.Scanner;

public class CalculateTenureCommand implements Command {
    private final DbService service;

    public CalculateTenureCommand(DbService service) {
        this.service = service;
    }

    private void listAllHods() {
        var hods = service.getHods();
        System.out.println("ID\tNAME");
        for(var hod:hods) {
            System.out.println(hod);
        }
    }
    
    @Override
    public String execute() {
        System.out.println("Enter Employee id: (Type -1 to list all Heads of Departments)");
        Scanner input = new Scanner(System.in);
        Long empId = Long.parseLong(input.nextLine());
        if (empId == -1) {
            listAllHods();
            System.out.println("Select Employee id:");
            empId = Long.parseLong(input.nextLine());
        }
        return service.calculateTenure(empId);
    }

    @Override
    public String desc() {
        return "- This calculates the number of months the employee has worked as the head of the department.";
    }
}
