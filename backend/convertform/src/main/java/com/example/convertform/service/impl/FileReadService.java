package com.example.convertform.service.impl;

import com.example.convertform.dto.input_sheet.*;
import com.example.convertform.dto.response.ValidationErrorResponseDTO;
import com.example.convertform.service.IFileReadService;
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
        StringBuilder stringBuilder = new StringBuilder("\n");
        for (ValidationErrorResponseDTO validationErrorResponseDTO : validateSingleFieldDataResult) {
            stringBuilder.append(validationErrorResponseDTO.toString());
        }

        if (!validateSingleFieldDataResult.isEmpty()) fileWriteService.writeErrorFile(validateSingleFieldDataResult);
        throw new ValidationException(stringBuilder.toString());
    }

    @Override
    public Object[] readInputFile(FileInputStream fileInputStream) throws IOException {
        return new Object[0];
    }

}
