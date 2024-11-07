package com.example.convertform.dto.input_sheet;

import com.example.convertform.dto.input.AreaRecord;
import com.gh.mygreen.xlsmapper.annotation.XlsHorizontalRecords;
import com.gh.mygreen.xlsmapper.annotation.XlsSheet;

import java.util.List;

@XlsSheet(name = "Area")
public class AreaSheet {
    @XlsHorizontalRecords(headerRow = 1, headerColumn = 0)
    List<AreaRecord> areaRecords;
}
