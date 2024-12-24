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
    @RecordColIndex(colName = "B")
    String cName;

    @XlsColumn(columnName = "Ad Group Name")
    @NotNull(message = "Ad Group Name is required")
    @RecordColIndex(colName = "C")
    String adGpName;

    @XlsColumn(columnName = "Location")
    @RecordColIndex(colName = "D")
    String location;

    @XlsColumn(columnName = "Radius")
    @RadiusLimit(message = "radius must be >=1 and <= 80")
    @RecordColIndex(colName = "E")
    String radius;

    @Override
    public int getNo() {
        return this.no;
    }
}
