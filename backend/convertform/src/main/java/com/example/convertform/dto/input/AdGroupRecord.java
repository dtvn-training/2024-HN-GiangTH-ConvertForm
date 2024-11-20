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
    String status;

    @XlsColumn(columnName = "Campaign Name")
    @NotNull(message = "Campaign Name is required")
    String cName;

    @XlsColumn(columnName = "Ad Group Name")
    @NotNull(message = "Ad Group Name is required")
    String adGpName;

    @XlsColumn(columnName = "Targeting Name 1")
    @SelectionRequired(values = {"Search Targeting", "Placement"})
    String targetName1;

    @XlsColumn(columnName = "Number 1")
    @SelectionRequired(values = {"①", "②", "③", "④", "⑤", "⑥", "⑦", "⑧", "⑨", "⑩"})
    String number1;

    @XlsColumn(columnName = "Targeting Name 2")
    @SelectionRequired(values = {"Search Targeting", "Placement"})
    String targetName2;

    @XlsColumn(columnName = "Number 2")
    String number2;

    @XlsColumn(columnName = "Targeting Name 3")
    @SelectionRequired(values = {"Search Targeting", "Placement"})
    String targetName3;

    @XlsColumn(columnName = "Number 3")
    String number3;

    @XlsColumn(columnName = "Targeting Name 4")
    @SelectionRequired(values = {"Search Targeting", "Placement"})
    String targetName4;

    @XlsColumn(columnName = "Number 4")
    String number4;

    @XlsColumn(columnName = "Targeting Name 5")
    String targetName5;

    @XlsColumn(columnName = "Number 5")
    String number5;

    @XlsColumn(columnName = "Targeting Name 6")
    String targetName6;

    @XlsColumn(columnName = "Number 6")
    String number6;

    @XlsColumn(columnName = "Targeting Name 7")
    String targetName7;

    @XlsColumn(columnName = "Number 7")
    String number7;

    @XlsColumn(columnName = "Targeting Name 8")
    String targetName8;

    @XlsColumn(columnName = "Number 8")
    String number8;

    @XlsColumn(columnName = "Targeting Name 9")
    String targetName9;

    @XlsColumn(columnName = "Number 9")
    String number9;

    @XlsColumn(columnName = "Targeting Name 10")
    String targetName10;

    @XlsColumn(columnName = "Number 10")
    String number10;

    @XlsColumn(columnName = "Device")
    @SelectionRequired(values = {"ALL", "PC/TB", "SP/TB", "PC/SP", "PC", "TB", "SP"})
    String device;

    @XlsColumn(columnName = "Gender")
    @SelectionRequired(values = {"ALL", "Male", "Female"})
    String gender;

    @XlsColumn(columnName = "Age")
    @SelectionRequired(values = {"ALL", "18+", "25+", "30+", "40+", "50+", "60+", "70+"})
    String age;

    @Override
    public int getNo() {
        return this.no;
    }
}
