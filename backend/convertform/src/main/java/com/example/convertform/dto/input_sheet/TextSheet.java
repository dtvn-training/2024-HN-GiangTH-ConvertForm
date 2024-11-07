package com.example.convertform.dto.input_sheet;

import com.example.convertform.dto.input.TextRecord;
import com.gh.mygreen.xlsmapper.annotation.XlsHorizontalRecords;
import com.gh.mygreen.xlsmapper.annotation.XlsSheet;

import java.util.List;

@XlsSheet(name = "Text")
public class TextSheet {
    @XlsHorizontalRecords(headerColumn = 0, headerRow = 2)
    List<TextRecord> textRecords;
}
