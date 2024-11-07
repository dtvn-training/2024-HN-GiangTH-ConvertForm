package com.example.convertform.dto.input_sheet;

import com.example.convertform.dto.input.PlacementRecord;
import com.example.convertform.dto.input.target_list_item.PlacementItem;
import com.gh.mygreen.xlsmapper.annotation.XlsIterateTables;
import com.gh.mygreen.xlsmapper.annotation.XlsSheet;

import java.util.List;

@XlsSheet(name = "Placement")
public class PlacementSheet {
    @XlsIterateTables(tableLabel = "Placement Targeting", bottom = 4)
    List<PlacementRecord> placementRecords;

    public String show() {
        StringBuilder stringBuilder = new StringBuilder();
        for (PlacementRecord placementRecord : placementRecords) {
            stringBuilder.append(placementRecord.getTargetName()).append(" ").append(placementRecord.getListName()).append(" ").append(placementRecord.getInclude()).append("\n");
            if (placementRecord.getPlacementItems() != null) {
                for (PlacementItem placementItem : placementRecord.getPlacementItems()) {
                    stringBuilder.append(" ").append(placementItem.getDomain()).append(" ");
                }
            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }
}
