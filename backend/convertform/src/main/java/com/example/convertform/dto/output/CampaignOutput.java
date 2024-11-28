package com.example.convertform.dto.output;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class CampaignOutput {
    @OutputExcelColumn(colName = "Account", order = 1)
    String account;

    @OutputExcelColumn(colName = "Campaign Name", order = 2)
    String cName;

    @OutputExcelColumn(colName = "Campaign Objectives", order = 3)
    String cObjective;

    @OutputExcelColumn(colName = "Daily Budget", order = 4)
    String dailyBudget;

    @OutputExcelColumn(colName = "Start Date", order = 5)
    Date startDate;

    @OutputExcelColumn(colName = "End Date", order = 6)
    Date endDate;

    @OutputExcelColumn(colName = "Delivery Settings", order = 7)
    String deliverySetting;

    @OutputExcelColumn(colName = "Component Type", order = 8)
    String componentType;
}
