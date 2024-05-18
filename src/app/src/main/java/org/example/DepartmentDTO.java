package org.example;

public record DepartmentDTO (Long dept_id, String name) {
    @Override
    public String toString() {
        return dept_id() + "\t" + name() + "\n";
    }
}
