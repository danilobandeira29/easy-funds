package com.github.danilobandeira29.easy_funds.value_objects;

public class RazaoSocial {
    private final String value;

    public RazaoSocial(String v) throws IllegalArgumentException {
        if (v.isBlank()) {
            throw new IllegalArgumentException("cannot be blank");
        }
        if(v.split(" ", 2).length < 2) {
            throw new IllegalArgumentException("must be razao social");
        }
        value = v;
    }

    public String getValue() {
        return value;
    }
}
