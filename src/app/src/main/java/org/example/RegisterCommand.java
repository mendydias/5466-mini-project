package org.example;

import java.util.Scanner;

/**
 * RegisterCommand
 */
public class RegisterCommand implements Command {
    private EmployeeDTO employee;

    private final DbService service;

    public RegisterCommand(DbService service) {
        this.service = service;
    }

    private void init() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the following information to register:");
        System.out.println("Full Name:");
        String name = input.nextLine();
        System.out.println("Role:");
        String role = input.nextLine();
        System.out.println("Department id:");
        Long dept = Long.parseLong(input.nextLine());
        System.out.println("House number:");
        String houseNum = input.nextLine();
        System.out.println("Street:");
        String street = input.nextLine();
        System.out.println("City id:");
        Long cityId = Long.parseLong(input.nextLine());
        System.out.println("Email:");
        String email = input.nextLine();
        System.out.println("Telephone number:");
        Long tel = Long.parseLong(input.nextLine());

        employee = new EmployeeDTO(name, role, dept, houseNum, street, cityId, email, tel);
    }

    @Override
    public String execute() {
        init();
        service.callRegisterProc(employee);
        return "";
    }

    @Override
    public String desc() {
        return "- this command registers a new user in the Employee Management Database";
    }
}
