package com.example.convertform.dto.input;

import com.gh.mygreen.xlsmapper.annotation.XlsColumn;

public class TextRecord {
    @XlsColumn(columnName = "Status")
    String status;
    @XlsColumn(columnName = "Start Date")
    String startDate;
    @XlsColumn(columnName = "End Date")
    String endDate;
    @XlsColumn(columnName = "Campaign Name")
    String cName;
    @XlsColumn(columnName = "Ad Group Name")
    String adGpName;
    @XlsColumn(columnName = "Ad Name")
    String aName;
    @XlsColumn(columnName = "Label")
    String label;
    @XlsColumn(columnName = "Title")
    String title;
    @XlsColumn(columnName = "Description 1")
    String description1;
    @XlsColumn(columnName = "Description 2")
    String description2;
    @XlsColumn(columnName = "URL")
    String url;
}
