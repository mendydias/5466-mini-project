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

    private void listCountries() {
        var countries = service.getAllCountries();
        System.out.println("ID\tNAME");
        for (var country : countries) {
            System.out.println(country);
        }
    }

    private void listProvincesFor(Long countryId) {
        var entities = service.getProvincesFor(countryId);
        System.out.println("ID\tNAME");
        for (var entity : entities) {
            System.out.println(entity);
        }
    }

    private void listDistrictsFor(Long provinceId) {
        var entities = service.getDistrictsFor(provinceId);
        System.out.println("ID\tNAME");
        for (var entity : entities) {
            System.out.println(entity);
        }
    }

    private void listCitiesFor(Long districtId) {
        var entities = service.getCitiesFor(districtId);
        System.out.println("ID\tNAME");
        for (var entity : entities) {
            System.out.println(entity);
        }
    }

    private void init() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the following information to register:");
        System.out.println("Full Name:");
        String name = input.nextLine();
        System.out.println("Role:");
        String role = input.nextLine();
<<<<<<< HEAD
        System.out.println("Department id:");
        Long dept = Long.parseLong(input.nextLine());
=======
        Long dept = -2L;
        while (dept == -2) {
            System.out.println("Department id: (Type -1 to list current departments)");
            dept = Long.parseLong(input.nextLine());
            if (dept == -1) {
                listDepartments();
                dept = -2L;
            }
        }
>>>>>>> city-search
        System.out.println("House number:");
        String houseNum = input.nextLine();
        System.out.println("Street:");
        String street = input.nextLine();
        System.out.println("City id: (Type -1 to search)");
        Long cityId = Long.parseLong(input.nextLine());
        if (cityId == -1) {
            System.out.println("Country id: (Type -1 to list Countries)");
            Long countryId = Long.parseLong(input.nextLine());
            if (countryId == -1) {
                listCountries();
                System.out.println("Country id: ");
                countryId = Long.parseLong(input.nextLine());
            }
            System.out.println("Provinces for Country id: " + countryId);
            listProvincesFor(countryId);
            System.out.println("Select Provinces id: ");
            Long provinceId = Long.parseLong(input.nextLine());
            System.out.println("Districts for Province id: " + provinceId);
            listDistrictsFor(provinceId);
            System.out.println("Select District id: ");
            Long districtId = Long.parseLong(input.nextLine());
            System.out.println("Cities for District: " + districtId);
            listCitiesFor(districtId);
            System.out.println("Select City id: ");
            cityId = Long.parseLong(input.nextLine());
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
