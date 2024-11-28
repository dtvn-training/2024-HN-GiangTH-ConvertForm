package com.example.convertform.dto.output.shared_lib;

import com.example.convertform.dto.output.OutputExcelColumn;
import lombok.Data;

@Data
public class KeywordListOutput {
    @OutputExcelColumn(colName = "List Name", order = 1)
    String listName;

    @OutputExcelColumn(colName = "List Explanation", order = 2)
    String listExplanation;

    @OutputExcelColumn(colName = "Number of searches", order = 3)
    String numOfSearch;

    @OutputExcelColumn(colName = "Validity Period", order = 4)
    String validityPeriod;

    @OutputExcelColumn(colName = "Search Keyword", order = 5)
    String keyword;
}
