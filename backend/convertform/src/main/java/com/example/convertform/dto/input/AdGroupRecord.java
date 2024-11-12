package com.example.convertform.dto.input;

import com.example.convertform.enum_class.adgp.AdgpStatus;
import com.gh.mygreen.xlsmapper.annotation.XlsColumn;
import com.gh.mygreen.xlsmapper.annotation.XlsEnumConverter;
import lombok.Data;

@Data
public class AdGroupRecord {
    @XlsColumn(columnName = "Status")
    @XlsEnumConverter(aliasMethod = "value")
    AdgpStatus status;
    @XlsColumn(columnName = "Campaign Name")
    String cName;
    @XlsColumn(columnName = "Ad Group Name")
    String adGpName;

}
