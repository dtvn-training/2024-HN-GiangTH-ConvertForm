package com.example.convertform.dto.output;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
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
