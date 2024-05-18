package org.example;

public record HodDTO(Long empId, String name) {
    @Override
    public String toString() {
        return empId() + "\t" + name();
    }
}
