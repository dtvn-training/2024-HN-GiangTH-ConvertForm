package com.example.convertform.enum_class.adgp;

public enum AdgpStatus {
    new_request("New Request"),
    pre_request("Pre-Request"),
    published("Published"),
    pending("Pending"),
    suspended("Suspended");

    private final String value;

    AdgpStatus(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
