package com.example.convertform.dto.input_sheet;

import com.example.convertform.dto.input.SearchTargetTable;
import com.example.convertform.dto.input.target_list_item.SearchKeywordRecord;
import com.gh.mygreen.xlsmapper.annotation.XlsIterateTables;
import com.gh.mygreen.xlsmapper.annotation.XlsSheet;
import lombok.Data;

import java.util.List;

@Data
@XlsSheet(name = "Search Targeting")
public class SearchTargetSheet {
    @XlsIterateTables(tableLabel = "Search Targeting", bottom = 3)
    List<SearchTargetTable> searchTargetTables;

    public String show() {
        StringBuilder stringBuilder = new StringBuilder();
        for (SearchTargetTable searchTargetTable : searchTargetTables) {
            stringBuilder.append(searchTargetTable.getTargetName()).append(" ").append(searchTargetTable.getListName()).append(" ").append("\n");
            if (searchTargetTable.getSearchKeywordRecords() != null) {
                for (SearchKeywordRecord searchKeywordRecord : searchTargetTable.getSearchKeywordRecords()) {
                    stringBuilder.append(" ").append(searchKeywordRecord.getKeyword()).append(" ");
                }
            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }
}
