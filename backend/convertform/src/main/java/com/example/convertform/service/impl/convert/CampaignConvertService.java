package com.example.convertform.service.impl.convert;

import com.example.convertform.dto.input.CampaignRecord;
import com.example.convertform.dto.input_sheet.CampaignSheet;
import com.example.convertform.dto.output.CampaignOutput;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CampaignConvertService {
    public CampaignOutput itemConvert(CampaignRecord source, String accountDesign) {
        CampaignOutput output = new CampaignOutput();
        output.setAccount(source.getAccountId());
        output.setCName(getOutputName(source, accountDesign));
        output.setCObjective(getObjective(source.getObjective()));
        output.setDeliverySetting("Off");
        output.setStartDate(source.getStartDate());
        output.setEndDate(source.getEndDate());
        output.setComponentType("Campaign");
        output.setDailyBudget(String.valueOf(calculateDailyBudget(source.getStartDate(), source.getEndDate(), source.getBudget())));
        return output;
    }

    public List<CampaignOutput> listConvert(CampaignSheet campaignSheet) {
        return campaignSheet.getCampaignRecords().stream()
                .map(item -> itemConvert(item, campaignSheet.getLabelCell()))
                .collect(Collectors.toList());
    }

    public static String getOutputName(CampaignRecord source, String accountDes) {
        String r = source.getCName() != null ? source.getCName() : "";
        if (accountDes.equals("Yes")) {
            return String.format("%s [%s]", r.replace("_", "").replace("-", "~"),
                    getFormatedDate(source.getStartDate(), source.getEndDate()));
        }
        return r;
    }

    public static CampaignRecord getCampaignByName(List<CampaignRecord> campaignRecords, String cName) {
        return campaignRecords.stream()
                .filter(record -> record.getCName().equals(cName))
                .findFirst()
                .orElse(new CampaignRecord());
    }

    public static String getFormatedDate(Date start, Date end) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return String.format("%s~%s",
                start.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(dateTimeFormatter),
                end.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(dateTimeFormatter));
    }

    private double calculateDailyBudget(Date start, Date end, double budget) {
        long day = Duration.between(start.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
                                    end.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
                                    ).toDays();
        return budget / day;
    }

    private String getObjective(String input) {
        return switch (input) {
            case "CPM" -> "Cost Per Mille";
            case "CPC" -> "Cost Per Click";
            case "CPV" -> "Cost Per View";
            default -> "";
        };
    }
}
