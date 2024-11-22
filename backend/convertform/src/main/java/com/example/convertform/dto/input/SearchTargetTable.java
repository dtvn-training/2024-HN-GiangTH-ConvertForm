package com.example.convertform.dto.input;

import com.example.convertform.dto.input.target_list_item.SearchKeywordRecord;
import com.example.convertform.validation.annotation.KeywordListNameRequired;
import com.gh.mygreen.xlsmapper.annotation.LabelledCellType;
import com.gh.mygreen.xlsmapper.annotation.XlsHorizontalRecords;
import com.gh.mygreen.xlsmapper.annotation.XlsLabelledCell;
import lombok.Data;

import java.util.List;

//Mỗi record là 1 table trong sheet
@Data
@KeywordListNameRequired
public class SearchTargetTable {
    @XlsHorizontalRecords(tableLabel = "Search Targeting", bottom = 3)
    List<SearchKeywordRecord> searchKeywordRecords;
    @XlsLabelledCell(label = "Targeting Name", type = LabelledCellType.Right)
    String targetName;
    @XlsLabelledCell(label = "Search Keyword List Name", type = LabelledCellType.Right)
    String listName;
}
