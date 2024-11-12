package com.example.convertform.dto.input.target_list_item;

import com.gh.mygreen.xlsmapper.annotation.XlsColumn;
import lombok.Data;

@Data
public class PlacementRecord {
    @XlsColumn(columnName = "Domain")
    String domain;
}
