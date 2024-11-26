package com.example.convertform.service.impl.convert;

import com.example.convertform.dto.input.AdGroupRecord;
import com.example.convertform.dto.input.PlacementTable;
import com.example.convertform.dto.input.target_list_item.TargetPair;
import com.example.convertform.dto.input_sheet.AdGroupSheet;
import com.example.convertform.dto.input_sheet.CampaignSheet;
import com.example.convertform.dto.input_sheet.PlacementSheet;
import com.example.convertform.dto.output.PlacementOutput;
import com.example.convertform.validation.common.AdGroupTargetValidator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlacementConvertService {
    /**
     *
     * @param placementSheet The Placement Sheet Object that read from file
     * @param adGroupRecord 1 Ad Group record (or 1 row in file)
     * @param campaignSheet The Campaign Sheet Object that read from file
     * @return A list of Placement Output (or 1 row in output file)
     */
    public List<PlacementOutput> itemConvert(PlacementSheet placementSheet, AdGroupRecord adGroupRecord, CampaignSheet campaignSheet) {
        List<PlacementOutput> res = new ArrayList<>();
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

            for (PlacementTable table : placementSheet.getPlacementTables()) {
                if (table.getTargetName() == null || table.getTargetName().isEmpty() || table.getTargetName().equals("Choose a targeting name")) continue;

                if (pair.getPairName().equals(table.getTargetName())) {
                    PlacementOutput output = new PlacementOutput();
                    output.setComponentType("Targeting");
                    output.setTargetingType("Placement");
                    output.setAdGpName(adGroupRecord.getAdGpName());
                    output.setCName(campaignNameConverted);
                    output.setPlacementListName(table.getListName());
                    output.setInclude(table.getInclude());

                    res.add(output);
                    //If found the matching Target, add to result List then move to next Ad Group target pair
                    continue outerLoop;
                }
            }
        }

        return res;
    }

    public List<PlacementOutput> listConvert(PlacementSheet placementSheet, AdGroupSheet adGroupSheet, CampaignSheet campaignSheet) {
        List<List<PlacementOutput>> temp = adGroupSheet.getAdGroupRecordList().stream()
                .map(record -> itemConvert(placementSheet, record, campaignSheet))
                .toList();

        List<PlacementOutput> res = new ArrayList<>();
        temp.forEach(res::addAll);

        return res;
    }
}
