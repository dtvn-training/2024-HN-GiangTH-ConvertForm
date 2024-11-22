package com.example.convertform.service.impl;

import com.example.convertform.dto.input.AdGroupRecord;
import com.example.convertform.dto.input_sheet.*;
import com.example.convertform.dto.output.AdGroupOutput;
import com.example.convertform.dto.output.AdOutput;
import com.example.convertform.dto.output.CampaignOutput;
import com.example.convertform.dto.response.ValidationErrorResponseDTO;
import com.example.convertform.service.IFileReadService;
import com.example.convertform.service.impl.convert.AdConvertService;
import com.example.convertform.service.impl.convert.AdGroupConvertService;
import com.example.convertform.service.impl.convert.CampaignConvertService;
import com.gh.mygreen.xlsmapper.XlsMapper;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileReadService implements IFileReadService {
    private final XlsMapper xlsMapper;
    private final FileValidateService fileValidateService;
    private final FileWriteService fileWriteService;
    private final CampaignConvertService campaignConvertService;
    private final AdGroupConvertService adGroupConvertService;
    private final AdConvertService adConvertService;
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

        //Validate single field
        List<ValidationErrorResponseDTO> validateSingleFieldDataResult = fileValidateService.validateSingleFieldData(sheets);

        //Related check, then add them into ValidationErrorResponseDTO above
        fileValidateService.validateRelated(sheets, validateSingleFieldDataResult);

        //After validate, convert data into Output DTOs
        List<CampaignOutput> campaignOutputs = campaignConvertService.listConvert((CampaignSheet) sheets[0]);
        List<AdGroupOutput> adGroupOutputs = adGroupConvertService.listConvert(((AdGroupSheet) sheets[1]), (CampaignSheet) sheets[0]);
        List<AdOutput> adOutputs = adConvertService.listConvert(((TextSheet) sheets[3]), (CampaignSheet) sheets[0]);

        if (!validateSingleFieldDataResult.isEmpty()) fileWriteService.writeErrorFile(validateSingleFieldDataResult);
        throw new ValidationException(adOutputs.toString());
    }

    @Override
    public Object[] readInputFile(FileInputStream fileInputStream) throws IOException {
        return new Object[0];
    }

}
