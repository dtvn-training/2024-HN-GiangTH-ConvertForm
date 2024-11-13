package com.example.convertform.validation;

import com.example.convertform.dto.input.CampaignRecord;
import com.example.convertform.validation.annotation.StartDateBeforeEndDate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Date;

public class StartBeforeEndDateValidator implements ConstraintValidator<StartDateBeforeEndDate, CampaignRecord> {
    @Override
    public boolean isValid(CampaignRecord campaignRecord, ConstraintValidatorContext constraintValidatorContext) {
        if (campaignRecord.getStartDate() == null || campaignRecord.getEndDate() == null) return true;
        Date startDate = campaignRecord.getStartDate();
        Date endDate = campaignRecord.getEndDate();

        return startDate.before(endDate);
    }
}
