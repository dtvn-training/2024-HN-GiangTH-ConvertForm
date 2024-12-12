package com.example.convertform.dto.input_sheet;

import com.example.convertform.dto.input.SiteCategoryRecord;
import com.gh.mygreen.xlsmapper.annotation.XlsHorizontalRecords;
import com.gh.mygreen.xlsmapper.annotation.XlsSheet;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Map<String, List<String>> getTargetCategory() {
        Map<String, List<String>> targetSite = new HashMap<>();

        for (SiteCategoryRecord siteCategoryRecord : siteCategoryRecords) {
            if (siteCategoryRecord.getTick() != null) targetSite.computeIfAbsent("Site Category①", k -> new ArrayList<>()).add(siteCategoryRecord.getCategory());
            if (siteCategoryRecord.getTick2() != null) targetSite.computeIfAbsent("Site Category②", k -> new ArrayList<>()).add(siteCategoryRecord.getCategory());
            if (siteCategoryRecord.getTick3() != null) targetSite.computeIfAbsent("Site Category③", k -> new ArrayList<>()).add(siteCategoryRecord.getCategory());
        }

        return targetSite;
    }
}
