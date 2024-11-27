package com.example.convertform.dto.output;

import lombok.Data;

@Data
public class SiteCategoryOutput {
    @OutputExcelColumn(colName = "Campaign Name", order = 1)
    String cName;

    @OutputExcelColumn(colName = "Ad Group Name", order = 2)
    String adGpName;

    @OutputExcelColumn(colName = "Category", order = 3)
    String category;

    @OutputExcelColumn(colName = "Component Type", order = 4)
    String componentType;

    @OutputExcelColumn(colName = "Targeting Type", order = 5)
    String targetingType;
}
