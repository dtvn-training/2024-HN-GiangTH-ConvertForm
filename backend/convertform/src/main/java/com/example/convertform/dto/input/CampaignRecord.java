package com.example.convertform.dto.input;

import com.example.convertform.enum_class.campaign.CampaignArea;
import com.example.convertform.enum_class.campaign.CampaignStatus;
import com.example.convertform.validation.annotation.StartDateBeforeEndDate;
import com.example.convertform.validation.annotation.ValidTimeFormat;
import com.gh.mygreen.xlsmapper.annotation.XlsColumn;
import com.gh.mygreen.xlsmapper.annotation.XlsEnumConverter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@StartDateBeforeEndDate
public class CampaignRecord extends BaseRecord {
    @XlsColumn(columnName = "No")
    int no;

    @XlsColumn(columnName = "Account ID")
    @NotNull(message = "Account ID is required")
    String accountId;

    @XlsColumn(columnName = "Status")
    @XlsEnumConverter(aliasMethod = "value")
    @NotNull(message = "CName is required")
    CampaignStatus status;

    @XlsColumn(columnName = "Campaign Name")
    @NotNull(message = "Campaign Name is required")
    @Size(min = 15, message = "must be more than 15 char")
    String cName;

    @XlsColumn(columnName = "Budget")
    double budget;

    @XlsColumn(columnName = "Publication Period")
    @NotNull(message = "Start Date is required")
    Date startDate;

    @XlsColumn(columnName = "Publication Period", headerMerged = 1)
    @NotNull(message = "Time is required")
    @ValidTimeFormat
    String startTime;

    @XlsColumn(columnName = "Publication Period", headerMerged = 2)
    @NotNull(message = "End Date is required")
    Date endDate;

    @XlsColumn(columnName = "Publication Period", headerMerged = 3)
    @ValidTimeFormat
    String endTime;

    @XlsColumn(columnName = "Campaign Objectives")
    @NotNull(message = "Objective is required")
    String objective;

    @XlsColumn(columnName = "Area")
    CampaignArea campaignArea;

    @Override
    public int getNo() {
        return this.no;
    }
}
