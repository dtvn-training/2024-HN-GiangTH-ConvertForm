package com.example.convertform.service.impl;

import com.example.convertform.dto.input.CampaignRecord;
import com.example.convertform.dto.input_sheet.*;
import com.example.convertform.service.IFileReadService;
import com.example.convertform.validation.ValidationUtils;
import com.gh.mygreen.xlsmapper.XlsMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FileReadService implements IFileReadService {
    private final XlsMapper xlsMapper;
    private final FileValidateService fileValidateService;
    @Override
    public String readInputFileDemo() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("C:\\Users\\GiangTH\\Downloads\\input_true.xlsx");

        Object[] sheets = xlsMapper.loadMultiple(
                fileInputStream,
                new Class[]{CampaignSheet.class, AdGroupSheet.class, AreaSheet.class, TextSheet.class, PlacementSheet.class, SearchTargetSheet.class, SiteCategorySheet.class}
        );

        for (Object sheet : sheets) {
            System.out.println(sheet.toString());
        }

        CampaignSheet cSheet = (CampaignSheet) sheets[0];
        StringBuilder validateResult = new StringBuilder();
        for (CampaignRecord campaignRecord : cSheet.getCampaignRecords()) {
            String ans = fileValidateService.validateData(campaignRecord);
            validateResult.append(ans);
        }

        if (!Objects.equals(validateResult.toString(), "no problem")) throw new ValidationException(validateResult.toString());

//        fileValidateService.validateData(cSheet);

        System.out.println(((CampaignSheet) sheets[0]).show());
        return "dfasf";
    }

    @Override
    public Object[] readInputFile(FileInputStream fileInputStream) throws IOException {
        return new Object[0];
    }

//    private void validateCampaignSheet(CampaignSheet campaignSheet) throws ValidationException {
//        Set<ConstraintViolation<CampaignSheet>> violations = ValidationUtils.validate(campaignSheet);
//
//        if (!violations.isEmpty()) {
//            StringBuilder sb = new StringBuilder("Validation errors:\n");
//            for (ConstraintViolation<CampaignSheet> violation : violations) {
//                sb.append("- ").append(violation.getPropertyPath()).append(": ").append(violation.getMessage()).append("\n");
//            }
//            throw new ValidationException(sb.toString());
//        }
//    }
}
