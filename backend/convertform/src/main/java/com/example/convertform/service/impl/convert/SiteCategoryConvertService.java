package com.example.convertform.service.impl.convert;

import com.example.convertform.dto.input.AdGroupRecord;
import com.example.convertform.dto.input.target_list_item.TargetPair;
import com.example.convertform.dto.input_sheet.AdGroupSheet;
import com.example.convertform.dto.input_sheet.CampaignSheet;
import com.example.convertform.dto.input_sheet.SiteCategorySheet;
import com.example.convertform.dto.output.SiteCategoryOutput;
import com.example.convertform.validation.common.AdGroupTargetValidator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SiteCategoryConvertService {
    /**
     *
     * @param categorySheet The Site Category Sheet Object that read from file
     * @param adGroupRecord 1 Ad Group record (or 1 row in file)
     * @param campaignSheet The Campaign Sheet Object that read from file
     * @return A list of Site Category Output (or 1 row in output file)
     */
    public List<SiteCategoryOutput> itemConvert(SiteCategorySheet categorySheet, AdGroupRecord adGroupRecord, CampaignSheet campaignSheet) {
        List<SiteCategoryOutput> res = new ArrayList<>();
        List<TargetPair> targetPairs = AdGroupTargetValidator.getTargetPairList(adGroupRecord);
        String campaignNameConverted = CampaignConvertService.getOutputName(
                CampaignConvertService.getCampaignByName(
                        campaignSheet.getCampaignRecords(),
                        adGroupRecord.getCName()
                ),
                campaignSheet.getLabelCell()
        );

        for (TargetPair pair : targetPairs) {
            if (pair.getTargetName() == null || pair.getNumber() == null) continue;

            categorySheet.getTargetCategory().forEach((key, value) -> {
                if (!value.isEmpty()) {
                    if (key.equals(pair.getPairName())) {
                        for (String category : value) {
                            SiteCategoryOutput output = new SiteCategoryOutput();
                            output.setCName(campaignNameConverted);
                            output.setAdGpName(adGroupRecord.getAdGpName());
                            output.setComponentType("Targeting");
                            output.setTargetingType("Site Category");
                            output.setCategory(category);

                            res.add(output);
                        }
                    }
                }
            });
        }

        return res;
    }

    public List<SiteCategoryOutput> listConvert(SiteCategorySheet categorySheet, AdGroupSheet adGroupSheet, CampaignSheet campaignSheet) {
        List<List<SiteCategoryOutput>> temp =  adGroupSheet.getAdGroupRecordList().stream()
                .map(record -> itemConvert(categorySheet, record, campaignSheet))
                .toList();

        List<SiteCategoryOutput> res = new ArrayList<>();
        temp.forEach(res::addAll);

        return res;
    }
}
