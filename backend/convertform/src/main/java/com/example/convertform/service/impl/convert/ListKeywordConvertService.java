package com.example.convertform.service.impl.convert;

import com.example.convertform.dto.input.SearchTargetTable;
import com.example.convertform.dto.input.target_list_item.SearchKeywordRecord;
import com.example.convertform.dto.input_sheet.SearchTargetSheet;
import com.example.convertform.dto.output.shared_lib.KeywordListOutput;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ListKeywordConvertService {
    public List<KeywordListOutput> itemConvert(SearchTargetTable table) {
        List<KeywordListOutput> list = new ArrayList<>();

        for (SearchKeywordRecord record : table.getSearchKeywordRecords()) {
            if (record.getKeyword() == null || record.getKeyword().isEmpty()) continue;

            KeywordListOutput output = new KeywordListOutput();
            output.setListExplanation("");
            output.setNumOfSearch("5 or more time");
            output.setValidityPeriod("Within 10 days");
            output.setListName(table.getListName() != null ? table.getListName() : "None");
            output.setKeyword(record.getKeyword());

            list.add(output);
        }

        return list;
    }

    public List<KeywordListOutput> listConvert(SearchTargetSheet sheet) {
        List<List<KeywordListOutput>> temp = sheet.getSearchTargetTables().stream()
                .map(this::itemConvert)
                .toList();

        List<KeywordListOutput> res = new ArrayList<>();
        temp.forEach(res::addAll);

        return res;
    }
}
