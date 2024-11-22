package com.example.convertform.dto.input;

import com.gh.mygreen.xlsmapper.annotation.XlsColumn;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TextRecord extends BaseRecord {
    @XlsColumn(columnName = "No.")
    int no;

    @XlsColumn(columnName = "Status")
    String status;

    @XlsColumn(columnName = "Start Date")
    String startDate;

    @XlsColumn(columnName = "End Date")
    String endDate;

    @XlsColumn(columnName = "Campaign Name")
    @NotNull(message = "Campaign Name is required")
    String cName;

    @XlsColumn(columnName = "Ad Group Name")
    @NotNull(message = "Ad Group Name is required")
    String adGpName;

    @XlsColumn(columnName = "Ad Name")
    @NotNull(message = "Ad Name is required")
    String aName;

    @XlsColumn(columnName = "Label")
    String label;

    @XlsColumn(columnName = "Title")
    @NotNull(message = "Title is required")
    String title;

    @XlsColumn(columnName = "Description 1")
    @NotNull(message = "Description 1 is required")
    String description1;

    @XlsColumn(columnName = "Description 2")
    String description2;

    @XlsColumn(columnName = "URL")
    @NotNull(message = "URL is required")
    String url;

    @Override
    public int getNo() {
        return this.no;
    }
}
