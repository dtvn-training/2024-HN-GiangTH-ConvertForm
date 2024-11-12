package com.example.convertform.dto.input_sheet;

import com.example.convertform.dto.input.SiteCategoryRecord;
import com.gh.mygreen.xlsmapper.annotation.XlsHorizontalRecords;
import com.gh.mygreen.xlsmapper.annotation.XlsSheet;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@XlsSheet(name = "Site Category")
public class SiteCategorySheet {
    @XlsHorizontalRecords(headerColumn = 0, headerRow = 2)
    List<SiteCategoryRecord> siteCategoryRecords;

    public String serialize() {
        StringBuilder stringBuilder = new StringBuilder();

        for (SiteCategoryRecord siteCategoryRecord : siteCategoryRecords) {
            stringBuilder.append("Row ").append(siteCategoryRecord.getTick())
                    .append(" ").append(siteCategoryRecord.getTick2())
                    .append(" ").append(siteCategoryRecord.getTick3())
                    .append(" ").append(siteCategoryRecord.getCategory()).append("\n");
        }

        return stringBuilder.toString();
    }

    //count each row, if found tick "O" at Site, add correspond Category to CategoryList of this Site

    /**
     * Loop over the Sheet records <br />
     * Each record have SiteCategory(1->3) and Category <br/>
     * If found tick "O" in SiteCategory, add correspond Category in CategoryList of this Site
     * @return void
     */
    public String showCountCategory() {
        List<String> site1 = new ArrayList<>();
        List<String> site2 = new ArrayList<>();
        List<String> site3 = new ArrayList<>();
        for (SiteCategoryRecord siteCategoryRecord : siteCategoryRecords) {
            if (siteCategoryRecord.getTick() != null) site1.add(siteCategoryRecord.getCategory());
            if (siteCategoryRecord.getTick2() != null) site2.add(siteCategoryRecord.getCategory());
            if (siteCategoryRecord.getTick3() != null) site3.add(siteCategoryRecord.getCategory());
        }
        return site1.toString() + "\n" + site2.toString() + "\n" + site3.toString();
    }
}
