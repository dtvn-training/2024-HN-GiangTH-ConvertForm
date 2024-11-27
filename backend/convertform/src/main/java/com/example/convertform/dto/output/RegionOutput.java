package com.example.convertform.dto.output;

import lombok.Data;

@Data
public class RegionOutput {
    @OutputExcelColumn(colName = "Campaign Name", order = 1)
    String cName;

    @OutputExcelColumn(colName = "Ad Group Name", order = 2)
    String adGpName;

    @OutputExcelColumn(colName = "Region", order = 3)
    String region;

    @OutputExcelColumn(colName = "Radius", order = 4)
    double radius;

    @OutputExcelColumn(colName = "Component Type", order = 5)
    String componentType;

    @OutputExcelColumn(colName = "Targeting Type", order = 6)
    String targetingType;
}
