package com.example.convertform.service.impl.convert;

import com.example.convertform.dto.input.AreaRecord;
import com.example.convertform.dto.input_sheet.AreaSheet;
import com.example.convertform.dto.input_sheet.CampaignSheet;
import com.example.convertform.dto.output.RegionOutput;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionConvertService {
    public RegionOutput itemConvert(AreaRecord areaRecord, CampaignSheet campaignSheet) {
        RegionOutput output = new RegionOutput();
        output.setComponentType("Targeting");
        output.setTargetingType("Geographic Targeting");
        output.setRadius(extractRadius(areaRecord.getRadius()));
        output.setRegion(areaRecord.getLocation());
        output.setAdGpName(areaRecord.getAdGpName());

        output.setCName(CampaignConvertService.getOutputName(
                CampaignConvertService.getCampaignByName(
                        campaignSheet.getCampaignRecords(),
                        areaRecord.getCName()
                ),
                campaignSheet.getLabelCell()
        ));

        return output;
    }

    public List<RegionOutput> listConvert(AreaSheet areaSheet, CampaignSheet campaignSheet) {
        return areaSheet.getAreaRecords().stream()
                .map(record -> itemConvert(record, campaignSheet))
                .toList();
    }

    private double extractRadius(String src) {
        if (src != null) return Double.parseDouble(src.substring(0, src.length() - 2));
        return 0.0;
    }
}
