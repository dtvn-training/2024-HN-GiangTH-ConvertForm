package com.example.convertform.dto.input.target_list_item;

import com.gh.mygreen.xlsmapper.annotation.XlsColumn;
import lombok.Data;

@Data
public class SearchKeywordItem {
    @XlsColumn(columnName = "Keyword")
    String keyword;
}
