package com.example.convertform.dto.input_sheet;

import com.example.convertform.dto.input.PlacementTable;
import com.example.convertform.dto.input.target_list_item.PlacementRecord;
import com.gh.mygreen.xlsmapper.annotation.XlsIterateTables;
import com.gh.mygreen.xlsmapper.annotation.XlsSheet;
import lombok.Data;

import java.util.List;

@Data
@XlsSheet(name = "Placement")
public class PlacementSheet {
    @XlsIterateTables(tableLabel = "Placement Targeting", bottom = 4)
    List<PlacementTable> placementTables;

    public String show() {
        StringBuilder stringBuilder = new StringBuilder();
        for (PlacementTable placementTable : placementTables) {
            stringBuilder.append(placementTable.getTargetName()).append(" ").append(placementTable.getListName()).append(" ").append(placementTable.getInclude()).append("\n");
            if (placementTable.getPlacementRecords() != null) {
                for (PlacementRecord placementItem : placementTable.getPlacementRecords()) {
                    stringBuilder.append(" ").append(placementItem.getDomain()).append(" ");
                }
            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }
}
