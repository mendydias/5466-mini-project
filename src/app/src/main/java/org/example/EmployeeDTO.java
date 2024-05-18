package org.example;

public record EmployeeDTO(String name, String role, Long dept, String houseNum, String street, Long cityId,
        String email, Long tel) {
}
