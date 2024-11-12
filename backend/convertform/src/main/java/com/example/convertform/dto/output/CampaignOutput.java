package com.example.convertform.dto.output;

import lombok.Data;

import java.util.Date;

@Data
public class CampaignOutput {
    String account;
    String cName;
    String cObjective;
    String dailyBudget;
    Date startDate;
    Date endDate;
    String deliverySetting;
    String componentType;
}
