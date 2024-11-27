package com.example.convertform.dto.output;

import lombok.Data;

@Data
public class AdOutput {
    @OutputExcelColumn(colName = "Campaign Name", order = 1)
    String cName;

    @OutputExcelColumn(colName = "Ad Group Name", order = 2)
    String adGpName;

    @OutputExcelColumn(colName = "Delivery Settings", order = 3)
    String deliverySetting;

    @OutputExcelColumn(colName = "Label", order = 4)
    String label;

    @OutputExcelColumn(colName = "Ad Name", order = 5)
    String adName;

    @OutputExcelColumn(colName = "Title", order = 6)
    String title;

    @OutputExcelColumn(colName = "Description 1", order = 7)
    String description1;

    @OutputExcelColumn(colName = "Description 2", order = 8)
    String description2;

    @OutputExcelColumn(colName = "URL", order = 9)
    String url;

    @OutputExcelColumn(colName = "Component Type", order = 10)
    String componentType;
}
