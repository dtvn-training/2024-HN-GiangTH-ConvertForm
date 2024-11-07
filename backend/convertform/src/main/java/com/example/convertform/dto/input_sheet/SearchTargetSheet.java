package com.example.convertform.dto.input_sheet;

import com.example.convertform.dto.input.SearchTargetRecord;
import com.example.convertform.dto.input.target_list_item.SearchKeywordItem;
import com.gh.mygreen.xlsmapper.annotation.XlsIterateTables;
import com.gh.mygreen.xlsmapper.annotation.XlsSheet;

import java.util.List;

@XlsSheet(name = "Search Targeting")
public class SearchTargetSheet {
    @XlsIterateTables(tableLabel = "Search Targeting", bottom = 3)
    List<SearchTargetRecord> searchTargetRecords;

    public String show() {
        StringBuilder stringBuilder = new StringBuilder();
        for (SearchTargetRecord searchTargetRecord: searchTargetRecords) {
            stringBuilder.append(searchTargetRecord.getTargetName()).append(" ").append(searchTargetRecord.getListName()).append(" ").append("\n");
            if (searchTargetRecord.getSearchKeywordItems() != null) {
                for (SearchKeywordItem searchKeywordItem : searchTargetRecord.getSearchKeywordItems()) {
                    stringBuilder.append(" ").append(searchKeywordItem.getKeyword()).append(" ");
                }
            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }
}
