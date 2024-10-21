package com.github.danilobandeira29.easy_funds.value_objects;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CNPJ {
    private final String value;
    private static final String CNPJ_PATTERN = "^\\d{2}.\\d{3}.\\d{3}/\\d{4}-\\d{2}$";

    public CNPJ(String v) throws IllegalArgumentException {
        Pattern pattern = Pattern.compile(CNPJ_PATTERN);
        Matcher matcher = pattern.matcher(v);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(String.format("this cannot be a cnpj %s", v));
        }
        value = v;
    }

    public String getWithMask() {
        return value.split("\\.")[0] + ".***.***/****-**";
    }

    public String getValue() {
        return value;
    }
}
