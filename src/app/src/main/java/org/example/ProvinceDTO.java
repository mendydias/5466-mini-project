package org.example;

public record ProvinceDTO(Long provinceId, String name) {
    @Override
    public String toString() {
        return provinceId() + "\t" + name();
    }
}
