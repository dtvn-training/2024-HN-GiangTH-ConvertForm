package com.example.convertform.dto.input_sheet;

import com.example.convertform.dto.input.CampaignRecord;
import com.example.convertform.validation.annotation.SelectionRequired;
import com.gh.mygreen.xlsmapper.annotation.LabelledCellType;
import com.gh.mygreen.xlsmapper.annotation.XlsHorizontalRecords;
import com.gh.mygreen.xlsmapper.annotation.XlsLabelledCell;
import com.gh.mygreen.xlsmapper.annotation.XlsSheet;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
@XlsSheet(name = "Campaign Entry Field")
public class CampaignSheet {

    @XlsLabelledCell(label = "Account Design ", type = LabelledCellType.Right)
    @NotNull(message = "label cannot be blank")
    @SelectionRequired(values = {"Yes", "No"})
    String labelCell;

    @XlsHorizontalRecords(headerRow = 3, headerColumn = 0, headerBottom = 2)
    @Valid
    @NotNull(message = "campaign cannot be null")
    List<CampaignRecord> campaignRecords;
}
