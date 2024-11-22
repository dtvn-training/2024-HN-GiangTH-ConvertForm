package com.example.convertform.service.impl.convert;

import com.example.convertform.dto.input.TextRecord;
import com.example.convertform.dto.input_sheet.CampaignSheet;
import com.example.convertform.dto.input_sheet.TextSheet;
import com.example.convertform.dto.output.AdOutput;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class AdConvertService {
    public AdOutput itemConvert(TextRecord textRecord, CampaignSheet campaignSheet) throws ParseException {
        AdOutput output = new AdOutput();
        output.setDeliverySetting(getDeliverySetting(textRecord));
        output.setComponentType("Advertisement");
        output.setAdGpName(textRecord.getAdGpName());
        output.setLabel(textRecord.getLabel());
        output.setUrl(textRecord.getUrl());
        output.setTitle(textRecord.getTitle());
        output.setDescription1(textRecord.getDescription1());
        output.setDescription2(textRecord.getDescription2());

        output.setCName(CampaignConvertService.getOutputName(
                CampaignConvertService.getCampaignByName(
                        campaignSheet.getCampaignRecords(),
                        textRecord.getCName()
                ),
                campaignSheet.getLabelCell()
        ));

        output.setAdName(getOutputAdName(textRecord, campaignSheet.getLabelCell()));
        return output;
    }

    public List<AdOutput> listConvert(TextSheet textSheet, CampaignSheet campaignSheet) {
        return textSheet.getTextRecords().stream()
                .map(record -> {
                    try {
                        return itemConvert(record, campaignSheet);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
    }

    private String getDeliverySetting(TextRecord textRecord) {
        if (textRecord.getStatus().equals("Pre-Request")) return "Off";
        return "On";
    }

    private String getOutputAdName(TextRecord textRecord, String accountDes) throws ParseException {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        DateFormat outDf = new SimpleDateFormat("yyyyMMdd");
        if (accountDes.equals("Yes")) {
            try {
                String newDate = outDf.format(df.parse(textRecord.getStartDate()));
                return newDate + "_TXT_" + textRecord.getAName();
            } catch (ParseException e) {
                throw new ParseException("Exception when Convert [Text -> AdOutput] when parse startDate", 1);
            }
        }
        return textRecord.getAName();
    }
}
