package com.example.convertform.dto.input_sheet;

import com.example.convertform.dto.input.SearchTargetTable;
import com.example.convertform.dto.input.target_list_item.SearchKeywordRecord;
import com.gh.mygreen.xlsmapper.annotation.XlsIterateTables;
import com.gh.mygreen.xlsmapper.annotation.XlsSheet;
import jakarta.validation.Valid;
import lombok.Data;

import java.util.List;

@Data
@XlsSheet(name = "Search Targeting")
public class SearchTargetSheet {
    @XlsIterateTables(tableLabel = "Search Targeting", bottom = 3)
    @Valid
    List<SearchTargetTable> searchTargetTables;
}
