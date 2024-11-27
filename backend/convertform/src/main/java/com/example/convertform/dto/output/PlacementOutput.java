package com.example.convertform.dto.output;

import lombok.Data;

@Data
public class PlacementOutput {
    @OutputExcelColumn(colName = "Campaign Name", order = 1)
    String cName;

    @OutputExcelColumn(colName = "Ad Group Name", order = 2)
    String adGpName;

    @OutputExcelColumn(colName = "Placement List Name", order = 3)
    String placementListName;

    @OutputExcelColumn(colName = "Include/Exclude", order = 4)
    String include;

    @OutputExcelColumn(colName = "Component Type", order = 5)
    String componentType;

    @OutputExcelColumn(colName = "Targeting Type", order = 6)
    String targetingType;
}
