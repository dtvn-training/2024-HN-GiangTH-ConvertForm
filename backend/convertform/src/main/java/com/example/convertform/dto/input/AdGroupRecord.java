package com.example.convertform.dto.input;

import com.example.convertform.validation.annotation.SelectionRequired;
import com.gh.mygreen.xlsmapper.annotation.XlsColumn;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AdGroupRecord extends BaseRecord {
    @XlsColumn(columnName = "No")
    int no;

    @XlsColumn(columnName = "Status")
    @NotNull(message = "AdGp status is required")
    @SelectionRequired(values = {"New Request", "Pre-Request", "Published", "Pending", "Suspended"})
    @RecordColIndex(colName = "B")
    String status;

    @XlsColumn(columnName = "Campaign Name")
    @NotNull(message = "Campaign Name is required")
    @RecordColIndex(colName = "C")
    String cName;

    @XlsColumn(columnName = "Ad Group Name")
    @NotNull(message = "Ad Group Name is required")
    @RecordColIndex(colName = "D")
    String adGpName;

    @XlsColumn(columnName = "Targeting Name 1")
    @SelectionRequired(values = {"Search Targeting", "Placement", "Site Category"})
    @RecordColIndex(colName = "E")
    String targetName1;

    @XlsColumn(columnName = "Number 1")
    @SelectionRequired(values = {"①", "②", "③", "④", "⑤", "⑥", "⑦", "⑧", "⑨", "⑩"})
    @RecordColIndex(colName = "F")
    String number1;

    @XlsColumn(columnName = "Targeting Name 2")
    @SelectionRequired(values = {"Search Targeting", "Placement", "Site Category"})
    @RecordColIndex(colName = "G")
    String targetName2;

    @XlsColumn(columnName = "Number 2")
    @SelectionRequired(values = {"①", "②", "③", "④", "⑤", "⑥", "⑦", "⑧", "⑨", "⑩"})
    @RecordColIndex(colName = "H")
    String number2;

    @XlsColumn(columnName = "Targeting Name 3")
    @SelectionRequired(values = {"Search Targeting", "Placement", "Site Category"})
    @RecordColIndex(colName = "I")
    String targetName3;

    @XlsColumn(columnName = "Number 3")
    @SelectionRequired(values = {"①", "②", "③", "④", "⑤", "⑥", "⑦", "⑧", "⑨", "⑩"})
    @RecordColIndex(colName = "J")
    String number3;

    @XlsColumn(columnName = "Targeting Name 4")
    @SelectionRequired(values = {"Search Targeting", "Placement", "Site Category"})
    @RecordColIndex(colName = "K")
    String targetName4;

    @XlsColumn(columnName = "Number 4")
    @SelectionRequired(values = {"①", "②", "③", "④", "⑤", "⑥", "⑦", "⑧", "⑨", "⑩"})
    @RecordColIndex(colName = "L")
    String number4;

    @XlsColumn(columnName = "Targeting Name 5")
    @SelectionRequired(values = {"Search Targeting", "Placement", "Site Category"})
    @RecordColIndex(colName = "M")
    String targetName5;

    @XlsColumn(columnName = "Number 5")
    @SelectionRequired(values = {"①", "②", "③", "④", "⑤", "⑥", "⑦", "⑧", "⑨", "⑩"})
    @RecordColIndex(colName = "N")
    String number5;

    @XlsColumn(columnName = "Targeting Name 6")
    @SelectionRequired(values = {"Search Targeting", "Placement", "Site Category"})
    @RecordColIndex(colName = "O")
    String targetName6;

    @XlsColumn(columnName = "Number 6")
    @SelectionRequired(values = {"①", "②", "③", "④", "⑤", "⑥", "⑦", "⑧", "⑨", "⑩"})
    @RecordColIndex(colName = "P")
    String number6;

    @XlsColumn(columnName = "Targeting Name 7")
    @SelectionRequired(values = {"Search Targeting", "Placement", "Site Category"})
    @RecordColIndex(colName = "Q")
    String targetName7;

    @XlsColumn(columnName = "Number 7")
    @SelectionRequired(values = {"①", "②", "③", "④", "⑤", "⑥", "⑦", "⑧", "⑨", "⑩"})
    @RecordColIndex(colName = "R")
    String number7;

    @XlsColumn(columnName = "Targeting Name 8")
    @SelectionRequired(values = {"Search Targeting", "Placement", "Site Category"})
    @RecordColIndex(colName = "S")
    String targetName8;

    @XlsColumn(columnName = "Number 8")
    @SelectionRequired(values = {"①", "②", "③", "④", "⑤", "⑥", "⑦", "⑧", "⑨", "⑩"})
    @RecordColIndex(colName = "T")
    String number8;

    @XlsColumn(columnName = "Targeting Name 9")
    @SelectionRequired(values = {"Search Targeting", "Placement", "Site Category"})
    @RecordColIndex(colName = "U")
    String targetName9;

    @XlsColumn(columnName = "Number 9")
    @SelectionRequired(values = {"①", "②", "③", "④", "⑤", "⑥", "⑦", "⑧", "⑨", "⑩"})
    @RecordColIndex(colName = "V")
    String number9;

    @XlsColumn(columnName = "Targeting Name 10")
    @SelectionRequired(values = {"Search Targeting", "Placement", "Site Category"})
    @RecordColIndex(colName = "W")
    String targetName10;

    @XlsColumn(columnName = "Number 10")
    @SelectionRequired(values = {"①", "②", "③", "④", "⑤", "⑥", "⑦", "⑧", "⑨", "⑩"})
    @RecordColIndex(colName = "X")
    String number10;

    @XlsColumn(columnName = "Device")
    @SelectionRequired(values = {"ALL", "PC/TB", "SP/TB", "PC/SP", "PC", "TB", "SP"})
    @RecordColIndex(colName = "Y")
    String device;

    @XlsColumn(columnName = "Gender")
    @SelectionRequired(values = {"ALL", "Male", "Female"})
    @RecordColIndex(colName = "Z")
    String gender;

    @XlsColumn(columnName = "Age")
    @SelectionRequired(values = {"ALL", "18+", "25+", "30+", "40+", "50+", "60+", "70+"})
    @RecordColIndex(colName = "AA")
    String age;

    @Override
    public int getNo() {
        return this.no;
    }
}
