package com.example.convertform.service.impl.convert;

import com.example.convertform.dto.input.AdGroupRecord;
import com.example.convertform.dto.input_sheet.AdGroupSheet;
import com.example.convertform.dto.input_sheet.CampaignSheet;
import com.example.convertform.dto.output.AdGroupOutput;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdGroupConvertService {
    public AdGroupOutput itemConvert(AdGroupRecord adGroupRecord, CampaignSheet campaignSheet) {
        AdGroupOutput output = new AdGroupOutput();
        output.setDeliverySetting(getDeliverySetting(adGroupRecord));
        output.setComponentType("Ad Group");

        output.setCName(CampaignConvertService.getOutputName(
                CampaignConvertService.getCampaignByName(
                        campaignSheet.getCampaignRecords(),
                        adGroupRecord.getCName()
                ),
                campaignSheet.getLabelCell()
        ));

        output.setAdGpName(getAdGroupName(adGroupRecord.getAdGpName(), campaignSheet.getLabelCell()));
        return output;
    }

    public List<AdGroupOutput> listConvert(AdGroupSheet adGroupSheet, CampaignSheet campaignSheet) {
        return adGroupSheet.getAdGroupRecordList().stream()
                .map(record -> itemConvert(record, campaignSheet))
                .toList();
    }

    private String getDeliverySetting(AdGroupRecord adGroupRecord) {
        if (adGroupRecord.getStatus().equals("Pre-Request")) return "Off";
        return "On";
    }

    private String getAdGroupName(String src, String accountDes) {
        if (accountDes.equals("Yes")) {
            return src.replace("_", "").replace("-", "~");
        }
        return src;
    }
}
