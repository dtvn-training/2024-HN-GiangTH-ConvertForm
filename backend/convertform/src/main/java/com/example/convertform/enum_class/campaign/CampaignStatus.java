package com.example.convertform.enum_class.campaign;

public enum CampaignStatus {
    new_request("New Request"),
    pre_request("Pre-Request"),
    published("Published"),
    pending("Pending"),
    suspended("Suspended");

    private final String value;

    CampaignStatus(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
