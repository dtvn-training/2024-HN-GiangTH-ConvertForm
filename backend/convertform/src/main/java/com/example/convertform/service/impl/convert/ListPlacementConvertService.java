package com.example.convertform.service.impl.convert;

import com.example.convertform.dto.input.PlacementTable;
import com.example.convertform.dto.input.target_list_item.PlacementRecord;
import com.example.convertform.dto.input_sheet.PlacementSheet;
import com.example.convertform.dto.output.shared_lib.PlacementListOutput;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ListPlacementConvertService {
    public List<PlacementListOutput> itemConvert(PlacementTable table) {
        List<PlacementListOutput> list = new ArrayList<>();

        for (PlacementRecord record : table.getPlacementRecords()) {
            if (record.getDomain() == null || record.getDomain().isEmpty()) continue;

            PlacementListOutput output = new PlacementListOutput();
            output.setListExplanation("");
            output.setListName(table.getListName() != null ? table.getListName() : "None");
            output.setUrl(record.getDomain());
            if (table.getPlacementRecords().size() > 10) output.setListType("Exclusive");
            else output.setListType("Standard");

            list.add(output);
        }

        return list;
    }

    public List<PlacementListOutput> listConvert(PlacementSheet sheet) {
        List<List<PlacementListOutput>> temp = sheet.getPlacementTables().stream()
                .map(this::itemConvert)
                .toList();

        List<PlacementListOutput> res = new ArrayList<>();
        temp.forEach(res::addAll);

        return res;
    }
}
