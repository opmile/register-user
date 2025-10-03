package com.opmile.register_user.model;

public enum Gender {
    FEMALE("female"),
    MALE("male");

    private final String genderApi;

    Gender(String genderApi) {
        this.genderApi = genderApi;
    }

    public String getGenderApi() {
        return genderApi;
    }

    public static Gender fromString(String text) {
        for (Gender g : values()) {
            if (g.getGenderApi().equalsIgnoreCase(text)) {
                return g;
            }
        }
        throw new IllegalArgumentException("Nenhuma correspondência de gênero para " + text);
    }
}
