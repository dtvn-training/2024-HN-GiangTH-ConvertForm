package com.example.convertform.dto.output;

import lombok.Data;

@Data
public class AgeOutput {
    @OutputExcelColumn(colName = "Campaign Name", order = 1)
    String cName;

    @OutputExcelColumn(colName = "Ad Group Name", order = 2)
    String adGpName;

    @OutputExcelColumn(colName = "Age", order = 3)
    String age;

    @OutputExcelColumn(colName = "Component Type", order = 4)
    String componentType;

    @OutputExcelColumn(colName = "Targeting Type", order = 5)
    String targetingType;
}
