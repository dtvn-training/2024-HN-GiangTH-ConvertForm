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
import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileReadService implements IFileReadService {
    private final XlsMapper xlsMapper;

    @Override
    public Object[] readInputFile(InputStream file) throws IOException {

        return xlsMapper.loadMultiple(
                file,
                new Class[]{CampaignSheet.class, AdGroupSheet.class, AreaSheet.class, TextSheet.class, PlacementSheet.class, SearchTargetSheet.class, SiteCategorySheet.class}
        );
    }
}
