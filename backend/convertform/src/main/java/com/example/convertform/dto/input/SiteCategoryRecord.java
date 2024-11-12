package com.example.convertform.dto.input;

import com.gh.mygreen.xlsmapper.annotation.XlsColumn;
import lombok.Data;

@Data
public class SiteCategoryRecord {
    @XlsColumn(columnName = "Site Category①")
    String tick;
    @XlsColumn(columnName = "Site Category②")
    String tick2;
    @XlsColumn(columnName = "Site Category③")
    String tick3;
    @XlsColumn(columnName = "Category")
    String category;
}
