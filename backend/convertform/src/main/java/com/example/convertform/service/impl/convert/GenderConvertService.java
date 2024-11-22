package com.example.convertform.service.impl.convert;

import com.example.convertform.dto.input.AdGroupRecord;
import com.example.convertform.dto.input_sheet.AdGroupSheet;
import com.example.convertform.dto.input_sheet.CampaignSheet;
import com.example.convertform.dto.output.GenderOutput;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenderConvertService {
    public GenderOutput itemConvert(AdGroupRecord adGroupRecord, CampaignSheet campaignSheet) {
        GenderOutput output = new GenderOutput();
        output.setComponentType("Targeting");
        output.setTargetType("Gender Targeting");
        output.setGender(adGroupRecord.getGender());
        output.setAdGpName(adGroupRecord.getAdGpName());

        output.setCName(CampaignConvertService.getOutputName(
                CampaignConvertService.getCampaignByName(
                        campaignSheet.getCampaignRecords(),
                        adGroupRecord.getCName()
                ),
                campaignSheet.getLabelCell()
        ));

        return output;
    }

    public List<GenderOutput> listConvert(AdGroupSheet adGroupSheet, CampaignSheet campaignSheet) {
        return adGroupSheet.getAdGroupRecordList().stream()
                .map(record -> itemConvert(record, campaignSheet))
                .toList();
    }
}
