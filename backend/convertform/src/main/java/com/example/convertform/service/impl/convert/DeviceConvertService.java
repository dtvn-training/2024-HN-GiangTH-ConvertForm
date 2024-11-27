package com.example.convertform.service.impl.convert;

import com.example.convertform.dto.input.AdGroupRecord;
import com.example.convertform.dto.input_sheet.AdGroupSheet;
import com.example.convertform.dto.input_sheet.CampaignSheet;
import com.example.convertform.dto.output.DeviceOutput;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceConvertService {
    public DeviceOutput itemConvert(AdGroupRecord adGroupRecord, CampaignSheet campaignSheet) {
        DeviceOutput output = new DeviceOutput();
        output.setComponentType("Targeting");
        output.setTargetingType("Device Targeting");;
        output.setAdGpName(adGroupRecord.getAdGpName());
        output.setDevice(getDeviceOutput(adGroupRecord.getDevice()));

        output.setCName(CampaignConvertService.getOutputName(
                CampaignConvertService.getCampaignByName(
                        campaignSheet.getCampaignRecords(),
                        adGroupRecord.getCName()
                ),
                campaignSheet.getLabelCell()
        ));

        return output;
    }

    public List<DeviceOutput> listConvert(AdGroupSheet adGroupSheet, CampaignSheet campaignSheet) {
        return adGroupSheet.getAdGroupRecordList().stream()
                .map(record -> itemConvert(record, campaignSheet))
                .toList();
    }

    private String getDeviceOutput(String device) {
        if (device == null || device.trim().isEmpty()) {
            device = "ALL";
        }

        return switch (device) {
            case "ALL" -> "PC, Smartphone, Tablet";
            case "PC/TB" -> "PC, Tablet";
            case "SP/TB" -> "Smartphone, Tablet";
            case "PC/SP" -> "PC, Smartphone";
            case "PC" -> "PC";
            case "TB" -> "Tablet";
            case "SP" -> "Smartphone";
            default -> "";
        };
    }
}
