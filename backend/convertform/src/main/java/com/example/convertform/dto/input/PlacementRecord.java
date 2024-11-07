package com.example.convertform.dto.input;

import com.example.convertform.dto.input.target_list_item.PlacementItem;
import com.gh.mygreen.xlsmapper.annotation.LabelledCellType;
import com.gh.mygreen.xlsmapper.annotation.RecordTerminal;
import com.gh.mygreen.xlsmapper.annotation.XlsHorizontalRecords;
import com.gh.mygreen.xlsmapper.annotation.XlsLabelledCell;
import lombok.Data;

import java.util.List;

//Mỗi record là 1 table trong sheet
@Data
public class PlacementRecord {
    @XlsHorizontalRecords(tableLabel = "Placement Targeting", bottom = 4, terminal = RecordTerminal.Border)
    List<PlacementItem> placementItems;
    @XlsLabelledCell(label = "Targeting Name", type = LabelledCellType.Right)
    String targetName;
    @XlsLabelledCell(label = "List Name", type = LabelledCellType.Right)
    String listName;
    @XlsLabelledCell(label = "Include/Exclude", type = LabelledCellType.Right)
    String include;
}
