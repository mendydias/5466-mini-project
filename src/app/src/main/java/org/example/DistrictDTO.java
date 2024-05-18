package org.example;

public record DistrictDTO(Long districtId, String name) {
    @Override
    public String toString() {
        return districtId() + "\t" + name();
    }
}
