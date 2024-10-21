package com.github.danilobandeira29.easy_funds.value_objects;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CPF {
    private final String value;
    private static final String CPF_PATTERN = "^\\d{3}.\\d{3}.\\d{3}-\\d{2}$";

    public CPF(String v) throws IllegalArgumentException {
        Pattern pattern = Pattern.compile(CPF_PATTERN);
        Matcher matcher = pattern.matcher(v);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(String.format("this cannot be a cpf %s", v));
        }
        value = v;
    }

    public String getWithMask() {
        return value.split("\\.")[0] + ".***.***-**";
    }

    public String getValue() {
        return value;
    }
}
