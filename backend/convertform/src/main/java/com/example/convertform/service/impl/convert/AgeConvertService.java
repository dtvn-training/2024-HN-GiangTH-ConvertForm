package com.example.convertform.service.impl.convert;

import com.example.convertform.dto.input.AdGroupRecord;
import com.example.convertform.dto.input.CampaignRecord;
import com.example.convertform.dto.input_sheet.AdGroupSheet;
import com.example.convertform.dto.input_sheet.CampaignSheet;
import com.example.convertform.dto.output.AgeOutput;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgeConvertService {
    public AgeOutput itemConvert(AdGroupRecord adGroupRecord, CampaignSheet campaignSheet) {
        AgeOutput output = new AgeOutput();
        output.setComponentType("Targeting");
        output.setTargetingType("Age Targeting");;
        output.setAdGpName(adGroupRecord.getAdGpName());
        output.setAge(getAgeOutput(adGroupRecord.getAge()));

        output.setCName(CampaignConvertService.getOutputName(
                getCampaignByName(
                        campaignSheet.getCampaignRecords(),
                        adGroupRecord.getCName()
                ),
                campaignSheet.getLabelCell()
        ));

        return output;
    }

    public List<AgeOutput> listConvert(AdGroupSheet adGroupSheet, CampaignSheet campaignSheet) {
        return adGroupSheet.getAdGroupRecordList().stream()
                .map(record -> itemConvert(record, campaignSheet))
                .toList();
    }

    private CampaignRecord getCampaignByName(List<CampaignRecord> campaignRecords, String cName) {
        return campaignRecords.stream()
                .filter(record -> record.getCName().equals(cName))
                .findFirst()
                .orElse(new CampaignRecord());
    }

    private String getAgeOutput(String age) {
        return switch (age) {
            case "ALL" -> "For all age";
            case "18+" -> "18 or higher";
            case "25+" -> "25 or higher";
            case "30+" -> "30 or higher";
            case "40+" -> "40 or higher";
            case "50+" -> "50 or higher";
            case "60+" -> "60 or higher";
            case "70+" -> "70 or higher";
            case null -> "";
            default -> age;
        };
    }
}
