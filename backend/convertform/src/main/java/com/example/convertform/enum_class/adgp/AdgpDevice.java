package com.example.convertform.enum_class.adgp;

public enum AdgpDevice {
    all("ALL"),
    pctb("PC/TB"),
    sptb("SP/TB"),
    pcsp("PC/SP"),
    pc("PC"),
    tb("TB"),
    sp("SP");

    private final String value;

    AdgpDevice(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
