package com.example.convertform.dto.input;

import com.example.convertform.enum_class.adgp.AdgpAge;
import com.example.convertform.enum_class.adgp.AdgpDevice;
import com.example.convertform.enum_class.adgp.AdgpGender;
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
    @XlsColumn(columnName = "Device")
    @XlsEnumConverter(aliasMethod = "value")
    AdgpDevice device;
    @XlsColumn(columnName = "Gender")
    AdgpGender gender;
    @XlsColumn(columnName = "Age")
    @XlsEnumConverter(aliasMethod = "value")
    AdgpAge age;
}
