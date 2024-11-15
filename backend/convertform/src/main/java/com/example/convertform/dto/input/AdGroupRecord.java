package com.example.convertform.dto.input;

import com.example.convertform.enum_class.adgp.AdgpAge;
import com.example.convertform.enum_class.adgp.AdgpDevice;
import com.example.convertform.enum_class.adgp.AdgpGender;
import com.example.convertform.enum_class.adgp.AdgpStatus;
import com.gh.mygreen.xlsmapper.annotation.XlsColumn;
import com.gh.mygreen.xlsmapper.annotation.XlsEnumConverter;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AdGroupRecord extends BaseRecord {
    @XlsColumn(columnName = "No")
    int no;

    @XlsColumn(columnName = "Status")
    @XlsEnumConverter(aliasMethod = "value")
    @NotNull(message = "Adgp status is required")
    AdgpStatus status;

    @XlsColumn(columnName = "Campaign Name")
    @NotNull(message = "Campaign Name is required")
    String cName;

    @XlsColumn(columnName = "Ad Group Name")
    @NotNull(message = "Ad Group Name is required")
    String adGpName;

    @XlsColumn(columnName = "Device")
    @XlsEnumConverter(aliasMethod = "value")
    AdgpDevice device;

    @XlsColumn(columnName = "Gender")
    AdgpGender gender;

    @XlsColumn(columnName = "Age")
    @XlsEnumConverter(aliasMethod = "value")
    AdgpAge age;

    @Override
    public int getNo() {
        return this.no;
    }
}
