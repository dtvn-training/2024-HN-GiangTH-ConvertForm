package com.example.convertform.dto.input_sheet;

import com.example.convertform.dto.input.CampaignRecord;
import com.gh.mygreen.xlsmapper.annotation.LabelledCellType;
import com.gh.mygreen.xlsmapper.annotation.XlsHorizontalRecords;
import com.gh.mygreen.xlsmapper.annotation.XlsLabelledCell;
import com.gh.mygreen.xlsmapper.annotation.XlsSheet;

import java.util.List;

@XlsSheet(name = "Campaign Entry Field")
public class CampaignSheet {
    @XlsLabelledCell(label = "", type = LabelledCellType.Right)
    String labelCell;
    @XlsHorizontalRecords(headerRow = 3, headerColumn = 0, headerBottom = 2)
    List<CampaignRecord> campaignRecords;
}
