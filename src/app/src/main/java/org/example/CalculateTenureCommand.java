package org.example;

import java.util.Scanner;

public class CalculateTenureCommand implements Command {
    private final DbService service;

    public CalculateTenureCommand(DbService service) {
        this.service = service;
    }

    @Override
    public String execute() {
        System.out.println("Enter Employee id:");
        Scanner input = new Scanner(System.in);
        Long empId = Long.parseLong(input.nextLine());
        return service.calculateTenure(empId);
    }

    @Override
    public String desc() {
        return "- This calculates the number of months the employee has worked as the head of the department.";
    }
}
