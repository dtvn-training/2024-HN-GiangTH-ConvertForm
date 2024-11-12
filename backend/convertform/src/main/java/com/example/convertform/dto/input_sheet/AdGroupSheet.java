package com.example.convertform.dto.input_sheet;

import com.example.convertform.dto.input.AdGroupRecord;
import com.gh.mygreen.xlsmapper.annotation.XlsHorizontalRecords;
import com.gh.mygreen.xlsmapper.annotation.XlsSheet;
import lombok.Data;

import java.util.List;

@Data
@XlsSheet(name = "Ad Group Field")
public class AdGroupSheet {
    @XlsHorizontalRecords(headerRow = 3, headerColumn = 0)
    List<AdGroupRecord> adGroupRecordList;
}
