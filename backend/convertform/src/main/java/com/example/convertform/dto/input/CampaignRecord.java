package com.example.convertform.dto.input;

import com.gh.mygreen.xlsmapper.annotation.XlsColumn;
import lombok.Data;

import java.util.Date;

@Data
public class CampaignRecord {
    @XlsColumn(columnName = "Account ID")
    String accountId;
    @XlsColumn(columnName = "Status")
    String status;
    @XlsColumn(columnName = "Campaign Name")
    String cName;
    @XlsColumn(columnName = "Budget")
    double budget;
    @XlsColumn(columnName = "Publication Period")
    Date startDate;
    @XlsColumn(columnName = "Publication Period", headerMerged = 1)
    String startTime;
    @XlsColumn(columnName = "Publication Period", headerMerged = 2)
    Date endDate;
    @XlsColumn(columnName = "Publication Period", headerMerged = 3)
    String endTime;
    @XlsColumn(columnName = "Campaign Objectives")
    String objective;
    @XlsColumn(columnName = "Area")
    String area;
}
