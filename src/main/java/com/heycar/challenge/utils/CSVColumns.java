package com.heycar.challenge.utils;

public enum CSVColumns {

    CODE("code"),
    MAKE_MODEL("make/model"),
    POWER_IN_PS("power-in-ps"),
    YEAR("year"),
    COLOR("color"),
    PRICE("price");

    private final String value;

    private CSVColumns(String column) {
        value = column;
    }

    public String getValue() {
        return this.value;
    }
}
