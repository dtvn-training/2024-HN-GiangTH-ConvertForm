package com.example.convertform.dto.input;

import com.gh.mygreen.xlsmapper.annotation.XlsColumn;

public class CampaignRecord {
    @XlsColumn(columnName = "Account ID")
    String accountId;
    @XlsColumn(columnName = "Status")
    String status;
    @XlsColumn(columnName = "Campaign Name")
    String cName;
    @XlsColumn(columnName = "Budget")
    String budget;
    @XlsColumn(columnName = "Publication Period")
    String startDate;
    @XlsColumn(columnName = "Publication Period", headerMerged = 1)
    String startTime;
    @XlsColumn(columnName = "Publication Period", headerMerged = 2)
    String endDate;
    @XlsColumn(columnName = "Publication Period", headerMerged = 3)
    String endTime;
    @XlsColumn(columnName = "Campaign Objectives")
    String objective;
    @XlsColumn(columnName = "Area")
    String area;
}
