package com.example.convertform.dto.output;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdGroupOutput {
    @OutputExcelColumn(colName = "Campaign Name", order = 1)
    String cName;

    @OutputExcelColumn(colName = "Ad Group Name", order = 2)
    String adGpName;

    @OutputExcelColumn(colName = "Delivery Settings", order = 3)
    String deliverySetting;

    @OutputExcelColumn(colName = "Component Type", order = 4)
    String componentType;
}
