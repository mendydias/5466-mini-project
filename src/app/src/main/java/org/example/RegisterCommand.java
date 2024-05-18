package org.example;

import java.util.Scanner;

public class RegisterCommand implements Command {
    private EmployeeDTO employee;

    private final DbService service;

    public RegisterCommand(DbService service) {
        this.service = service;
    }

    private void listDepartments() {
            var departments = service.getAllDepartments();
            System.out.println("ID\tNAME");
            for (var department : departments) {
                System.out.print(department);
            }
    }

    private void init() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the following information to register:");
        System.out.println("Full Name:");
        String name = input.nextLine();
        System.out.println("Role:");
        String role = input.nextLine();
        Long dept = -2;
        while (dept == -2) {
            System.out.println("Department id: (Type -1 to list current departments)");
            dept = Long.parseLong(input.nextLine());
            if (dept == -1) {
                listDepartments();
            }
        }
        System.out.println("House number:");
        String houseNum = input.nextLine();
        System.out.println("Street:");
        String street = input.nextLine();
        Long cityId = -2;
        while (cityId == -2) {
            System.out.println("City id: (Type -1 to search)");
            cityId = Long.parseLong(input.nextLine());
            if (cityId == -1) {
                System.out.println("Country");
            }
        }
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
