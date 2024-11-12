package com.example.convertform.dto.input;

import com.gh.mygreen.xlsmapper.annotation.XlsColumn;
import lombok.Data;

@Data
public class AdGroupRecord {
    @XlsColumn(columnName = "Status")
    String status;
    @XlsColumn(columnName = "Campaign Name")
    String cName;
    @XlsColumn(columnName = "Ad Group Name")
    String adGpName;

}
