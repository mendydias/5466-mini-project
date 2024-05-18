package org.example;

public record EmployeeDTO(String name, String role, Long dept, String houseNum, String street, Long cityId,
        String email, Long tel) {
        
        @Override
        public String toString() {
                return String.format("%-30s %-30s %-50s", name(), role(), email());
        }
}
