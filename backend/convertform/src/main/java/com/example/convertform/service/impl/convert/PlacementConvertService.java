package com.example.convertform.service.impl.convert;

import com.example.convertform.dto.input.AdGroupRecord;
import com.example.convertform.dto.input_sheet.AdGroupSheet;
import com.example.convertform.dto.input_sheet.CampaignSheet;
import com.example.convertform.dto.output.PlacementOutput;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlacementConvertService {
    public PlacementOutput itemConvert(AdGroupRecord adGroupRecord, CampaignSheet campaignSheet) {
        PlacementOutput output = new PlacementOutput();
        output.setComponentType("Targeting");
        output.setTargetingType("Placement");
        output.setAdGpName("");

        output.setCName(CampaignConvertService.getOutputName(
                CampaignConvertService.getCampaignByName(
                        campaignSheet.getCampaignRecords(),
                        adGroupRecord.getCName()
                ),
                campaignSheet.getLabelCell()
        ));

        return output;
    }

    public List<PlacementOutput> listConvert(AdGroupSheet adGroupSheet, CampaignSheet campaignSheet) {
        return adGroupSheet.getAdGroupRecordList().stream()
                .map(record -> itemConvert(record, campaignSheet))
                .toList();
    }
}
