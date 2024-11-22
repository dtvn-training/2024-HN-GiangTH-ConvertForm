package com.example.convertform.dto.input_sheet;

import com.example.convertform.dto.input.PlacementTable;
import com.example.convertform.dto.input.target_list_item.PlacementRecord;
import com.gh.mygreen.xlsmapper.annotation.XlsIterateTables;
import com.gh.mygreen.xlsmapper.annotation.XlsSheet;
import jakarta.validation.Valid;
import lombok.Data;

import java.util.List;

@Data
@XlsSheet(name = "Placement")
public class PlacementSheet {
    @XlsIterateTables(tableLabel = "Placement Targeting", bottom = 4)
    @Valid
    List<PlacementTable> placementTables;
}
