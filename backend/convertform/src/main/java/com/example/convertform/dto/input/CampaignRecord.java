package com.example.convertform.dto.input;

import com.example.convertform.validation.annotation.SelectionRequired;
import com.example.convertform.validation.annotation.StartDateBeforeEndDate;
import com.gh.mygreen.xlsmapper.annotation.XlsColumn;
import com.gh.mygreen.xlsmapper.annotation.XlsEnumConverter;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    @RecordColIndex(colName = "B")
    String accountId;

    @XlsColumn(columnName = "Status")
    @NotNull(message = "Status is required")
    @SelectionRequired(values = {"New Request", "Pre-Request", "Published", "Pending", "Suspended"})
    @RecordColIndex(colName = "C")
    String status;

    @XlsColumn(columnName = "Campaign Name")
    @NotNull(message = "Campaign Name is required")
    @RecordColIndex(colName = "D")
    String cName;

    @XlsColumn(columnName = "Budget")
    @RecordColIndex(colName = "E")
    double budget;

    @XlsColumn(columnName = "Publication Period")
    @NotNull(message = "Start Date is required")
    @RecordColIndex(colName = "F")
    Date startDate;

    @XlsColumn(columnName = "Publication Period", headerMerged = 1)
    @NotNull(message = "Time is required")
    @Pattern(regexp = "^$|^[^0-9].*|^([01]?[0-9]|2[0-3]):[0-5][0-9]$",
            message = "Time must be in format HH:mm")
    @RecordColIndex(colName = "G")
    String startTime;

    @XlsColumn(columnName = "Publication Period", headerMerged = 2)
    @NotNull(message = "End Date is required")
    @RecordColIndex(colName = "H")
    Date endDate;

    @XlsColumn(columnName = "Publication Period", headerMerged = 3)
    @Pattern(regexp = "^$|^[^0-9].*|^([01]?[0-9]|2[0-3]):[0-5][0-9]$",
            message = "Time must be in format HH:mm")
    @RecordColIndex(colName = "I")
    String endTime;

    @XlsColumn(columnName = "Campaign Objectives")
    @SelectionRequired(values = {"CPM", "CPC", "CPV"})
    @NotNull(message = "Objective is required")
    @RecordColIndex(colName = "J")
    String objective;

    @XlsColumn(columnName = "Area")
    @SelectionRequired(values = {"Yes", "None"})
    @RecordColIndex(colName = "K")
    String campaignArea;

    @Override
    public int getNo() {
        return this.no;
    }
}
