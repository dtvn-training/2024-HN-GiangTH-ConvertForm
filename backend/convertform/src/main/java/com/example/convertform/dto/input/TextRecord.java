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
    @RecordColIndex(colName = "B")
    String status;

    @XlsColumn(columnName = "Start Date")
    @RecordColIndex(colName = "C")
    String startDate;

    @XlsColumn(columnName = "End Date")
    @RecordColIndex(colName = "D")
    String endDate;

    @XlsColumn(columnName = "Campaign Name")
    @NotNull(message = "Campaign Name is required")
    @RecordColIndex(colName = "E")
    String cName;

    @XlsColumn(columnName = "Ad Group Name")
    @NotNull(message = "Ad Group Name is required")
    @RecordColIndex(colName = "F")
    String adGpName;

    @XlsColumn(columnName = "Ad Name")
    @NotNull(message = "Ad Name is required")
    @RecordColIndex(colName = "G")
    String aName;

    @XlsColumn(columnName = "Label")
    @RecordColIndex(colName = "H")
    String label;

    @XlsColumn(columnName = "Title")
    @NotNull(message = "Title is required")
    @RecordColIndex(colName = "I")
    String title;

    @XlsColumn(columnName = "Description 1")
    @NotNull(message = "Description 1 is required")
    @RecordColIndex(colName = "J")
    String description1;

    @XlsColumn(columnName = "Description 2")
    @RecordColIndex(colName = "K")
    String description2;

    @XlsColumn(columnName = "URL")
    @NotNull(message = "URL is required")
    @RecordColIndex(colName = "L")
    String url;

    @Override
    public int getNo() {
        return this.no;
    }
}
