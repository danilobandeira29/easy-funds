package com.github.danilobandeira29.easy_funds.value_objects;

import com.fasterxml.jackson.annotation.JsonValue;

public class FullName {
    private final String value;
    private final String firstName;
    private final String lastName;

    public FullName(String f) throws IllegalArgumentException {
        if (f.isBlank()) {
            throw new IllegalArgumentException("cannot be blank");
        }
        if(f.split(" ", 2).length < 2) {
            throw new IllegalArgumentException("must be full name");
        }
        value = f;
        String[] names = f.split(" ", 2);
        firstName = names[0];
        lastName = names[1];
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getValue() {
        return value;
    }
}
