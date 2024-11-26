package com.example.convertform.service.impl;

import com.example.convertform.dto.input_sheet.*;
import com.example.convertform.dto.output.*;
import com.example.convertform.dto.response.ValidationErrorResponseDTO;
import com.example.convertform.service.IFileReadService;
import com.example.convertform.service.impl.convert.*;
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
    private final SearchTargetConvertService searchTargetConvertService;
    private final PlacementConvertService placementConvertService;
    private final SiteCategoryConvertService siteCategoryConvertService;
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
        List<SearchTargetOutput> searchTargetOutputs = searchTargetConvertService.listConvert((SearchTargetSheet) sheets[5], (AdGroupSheet) sheets[1], (CampaignSheet) sheets[0]);
        List<PlacementOutput> placementOutputs = placementConvertService.listConvert((PlacementSheet) sheets[4], (AdGroupSheet) sheets[1], (CampaignSheet) sheets[0]);
        List<SiteCategoryOutput> siteCategoryOutputs = siteCategoryConvertService.listConvert((SiteCategorySheet) sheets[6], (AdGroupSheet) sheets[1], (CampaignSheet) sheets[0]);

//        if (!validateSingleFieldDataResult.isEmpty()) fileWriteService.writeErrorFile(validateSingleFieldDataResult);
        throw new ValidationException(siteCategoryOutputs.toString());
    }

    @Override
    public Object[] readInputFile(FileInputStream fileInputStream) throws IOException {
        return new Object[0];
    }

}
