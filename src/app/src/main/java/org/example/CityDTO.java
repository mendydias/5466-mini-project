package org.example;

public record CityDTO(Long cityId, String name) {
    @Override
    public String toString() {
        return cityId() + "\t" + name();
    }
}
