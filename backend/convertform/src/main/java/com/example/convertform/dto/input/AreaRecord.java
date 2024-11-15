package com.example.convertform.dto.input;

import com.example.convertform.validation.annotation.RadiusLimit;
import com.gh.mygreen.xlsmapper.annotation.XlsColumn;
import com.gh.mygreen.xlsmapper.annotation.XlsDefaultValue;
import com.gh.mygreen.xlsmapper.annotation.XlsTrim;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AreaRecord extends BaseRecord {
    @XlsColumn(columnName = "No.")
    int no;

    @XlsColumn(columnName = "Campaign Name")
    @NotNull(message = "Campaign Name is required")
    String cName;

    @XlsColumn(columnName = "Ad Group Name")
    @NotNull(message = "Campaign Name is required")
    String adGpName;

    @XlsColumn(columnName = "Location")
    String location;

    @XlsColumn(columnName = "Radius")
    @RadiusLimit(message = "radius must be >=1 and <= 80")
    String radius;

    @Override
    public int getNo() {
        return this.no;
    }
}
