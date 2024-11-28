package com.example.convertform.dto.output.shared_lib;

import com.example.convertform.dto.output.OutputExcelColumn;
import lombok.Data;

@Data
public class PlacementListOutput {
    @OutputExcelColumn(colName = "List Name", order = 1)
    String listName;

    @OutputExcelColumn(colName = "List Explanation", order = 2)
    String listExplanation;

    @OutputExcelColumn(colName = "List Type", order = 3)
    String listType;

    @OutputExcelColumn(colName = "URL", order = 4)
    String url;
}
