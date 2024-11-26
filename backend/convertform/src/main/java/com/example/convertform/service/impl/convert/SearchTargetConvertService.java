package com.example.convertform.service.impl.convert;

import com.example.convertform.dto.input.AdGroupRecord;
import com.example.convertform.dto.input.SearchTargetTable;
import com.example.convertform.dto.input.target_list_item.TargetPair;
import com.example.convertform.dto.input_sheet.AdGroupSheet;
import com.example.convertform.dto.input_sheet.CampaignSheet;
import com.example.convertform.dto.input_sheet.SearchTargetSheet;
import com.example.convertform.dto.output.SearchTargetOutput;
import com.example.convertform.validation.common.AdGroupTargetValidator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchTargetConvertService {
    /**
     *
     * @param targetSheet The Search Target Sheet Object that read from file
     * @param adGroupRecord 1 Ad Group record (or 1 row in file)
     * @param campaignSheet The Campaign Sheet Object that read from file
     * @return A list of Search Target Output (or 1 row in output file)
     */
    public List<SearchTargetOutput> itemConvert(SearchTargetSheet targetSheet, AdGroupRecord adGroupRecord, CampaignSheet campaignSheet) {
        List<SearchTargetOutput> res = new ArrayList<>();
        List<TargetPair> targetPairs = AdGroupTargetValidator.getTargetPairList(adGroupRecord);
        String campaignNameConverted = CampaignConvertService.getOutputName(
                                            CampaignConvertService.getCampaignByName(
                                                    campaignSheet.getCampaignRecords(),
                                                    adGroupRecord.getCName()
                                            ),
                                            campaignSheet.getLabelCell()
                                        );

        outerLoop: for (TargetPair pair : targetPairs) {
            if (pair.getTargetName() == null || pair.getNumber() == null) continue;

            for (SearchTargetTable table : targetSheet.getSearchTargetTables()) {
                if (table.getTargetName() == null || table.getTargetName().isEmpty() || table.getTargetName().equals("Choose a targeting name")) continue;

                if (pair.getPairName().equals(table.getTargetName())) {
                    SearchTargetOutput output = new SearchTargetOutput();
                    output.setComponentType("Targeting");
                    output.setTargetingType("Search Targeting");
                    output.setAdGpName(adGroupRecord.getAdGpName());
                    output.setKeywordListName(table.getListName());
                    output.setCName(campaignNameConverted);

                    res.add(output);
                    //If found the matching Target, add to result List then move to next Ad Group target pair
                    continue outerLoop;
                }
            }
        }

        return res;
    }

    public List<SearchTargetOutput> listConvert(SearchTargetSheet targetSheet, AdGroupSheet adGroupSheet, CampaignSheet campaignSheet) {
        List<List<SearchTargetOutput>> temp = adGroupSheet.getAdGroupRecordList().stream()
                .map(record -> itemConvert(targetSheet, record, campaignSheet))
                .toList();

        List<SearchTargetOutput> res = new ArrayList<>();
        temp.forEach(res::addAll);

        return res;
    }
}
