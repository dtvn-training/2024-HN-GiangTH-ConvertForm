package com.example.convertform.enum_class.adgp;

public enum AdgpAge {
    all("ALL"),
    p18("18+"),
    p25("25+"),
    p30("30+"),
    p40("40+"),
    p50("50+"),
    p60("60+"),
    p70("70+");

    private final String value;

    AdgpAge(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
