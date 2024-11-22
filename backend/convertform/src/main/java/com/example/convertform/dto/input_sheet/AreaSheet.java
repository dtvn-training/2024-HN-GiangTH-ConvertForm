package com.example.convertform.dto.input_sheet;

import com.example.convertform.dto.input.AreaRecord;
import com.gh.mygreen.xlsmapper.annotation.XlsHorizontalRecords;
import com.gh.mygreen.xlsmapper.annotation.XlsSheet;
import jakarta.validation.Valid;
import lombok.Data;

import java.util.List;

@Data
@XlsSheet(name = "Area")
public class AreaSheet {
    @XlsHorizontalRecords(headerRow = 1, headerColumn = 0)
    @Valid
    List<AreaRecord> areaRecords;
}
