package com.example.convertform.dto.input_sheet;

import com.example.convertform.dto.input.CampaignRecord;
import com.gh.mygreen.xlsmapper.annotation.LabelledCellType;
import com.gh.mygreen.xlsmapper.annotation.XlsHorizontalRecords;
import com.gh.mygreen.xlsmapper.annotation.XlsLabelledCell;
import com.gh.mygreen.xlsmapper.annotation.XlsSheet;
import lombok.Data;

import java.util.List;

@Data
@XlsSheet(name = "Campaign Entry Field")
public class CampaignSheet {
    @XlsLabelledCell(label = "Account Design ", type = LabelledCellType.Right)
    String labelCell;
    @XlsHorizontalRecords(headerRow = 3, headerColumn = 0, headerBottom = 2)
    List<CampaignRecord> campaignRecords;

    public String show() {
        StringBuilder ans = new StringBuilder();
        for (CampaignRecord campaignRecord : campaignRecords) {
            ans.append(campaignRecord.getCampaignArea());
            ans.append(" ");
            ans.append(campaignRecord.getStatus().value());
            ans.append("\n");
        }
        return ans.toString();
    }
}
