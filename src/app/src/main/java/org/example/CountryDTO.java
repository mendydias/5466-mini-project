package org.example;

public record CountryDTO(Long countryId, String name) {
    @Override
    public String toString() {
        return countryId() + "\t" + name();
    }
}
