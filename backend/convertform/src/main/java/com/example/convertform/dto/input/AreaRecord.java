package com.example.convertform.dto.input;

import com.gh.mygreen.xlsmapper.annotation.XlsColumn;

public class AreaRecord {
    @XlsColumn(columnName = "Campaign Name")
    String cName;
    @XlsColumn(columnName = "Ad Group Name")
    String adGpName;
    @XlsColumn(columnName = "Location")
    String location;
    @XlsColumn(columnName = "Radius")
    String radius;
}
