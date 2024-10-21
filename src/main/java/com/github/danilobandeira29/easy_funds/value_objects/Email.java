package com.github.danilobandeira29.easy_funds.value_objects;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Email {
    private final String value;
    private static final String EMAIL_PATTERN = "(.*)@(.*)";

    public Email(String v) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(v);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(String.format("value can't be a email %s", v));
        }
        value = v;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
