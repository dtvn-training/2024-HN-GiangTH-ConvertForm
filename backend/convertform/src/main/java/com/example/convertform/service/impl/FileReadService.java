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
    @Override
    public Object[] readInputFileDemo() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("C:\\Users\\GiangTH\\Downloads\\input_true.xlsx");

        return xlsMapper.loadMultiple(
                fileInputStream,
                new Class[]{CampaignSheet.class, AdGroupSheet.class, AreaSheet.class, TextSheet.class, PlacementSheet.class, SearchTargetSheet.class, SiteCategorySheet.class}
        );
    }

    @Override
    public Object[] readInputFile(FileInputStream fileInputStream) {
        return new Object[0];
    }
}
