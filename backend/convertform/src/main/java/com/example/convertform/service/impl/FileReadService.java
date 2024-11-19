package com.example.convertform.service.impl;

import com.example.convertform.dto.input.*;
import com.example.convertform.dto.input_sheet.*;
import com.example.convertform.service.IFileReadService;
import com.example.convertform.validation.AdGroupTargetValidator;
import com.example.convertform.validation.ListValidator;
import com.gh.mygreen.xlsmapper.XlsMapper;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

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

        //Validate single field
        String validateResult = fileValidateService.validateSingleFieldData(sheets);

        List<CampaignRecord> cp = ((CampaignSheet) sheets[0]).getCampaignRecords();
        List<AdGroupRecord> adGp = ((AdGroupSheet) sheets[1]).getAdGroupRecordList();
        List<AreaRecord> ar = ((AreaSheet) sheets[2]).getAreaRecords();
        List<SearchTargetTable> searchTables = ((SearchTargetSheet) sheets[5]).getSearchTargetTables();
        List<PlacementTable> placementTables = ((PlacementSheet) sheets[4]).getPlacementTables();
        SiteCategorySheet siteCategorySheet = (SiteCategorySheet) sheets[6];

        //If no error in field, check related validate
        ListValidator.validateTwoLists(cp, ar, ListValidator::checkCpNameInArea, "Double check ");
        ListValidator.validateTwoLists(adGp, cp, ListValidator::checkCpNameInAdGroup, "Triple check ");
        ListValidator.validateDuplicateCampaignList(cp, "Campaign have duplicate value");
        ListValidator.validateDuplicateAdGroupList(adGp, "Ad Group List have duplicate value");

        for (AdGroupRecord adGroupRecord : adGp) {
            System.out.println(AdGroupTargetValidator.validateAdGroupTargets(adGroupRecord, placementTables, searchTables, siteCategorySheet));
        }

        System.out.println(AdGroupTargetValidator.validateUnusedTarget(adGp, placementTables, searchTables, siteCategorySheet));

        if (!Objects.equals(validateResult, "no problem")) throw new ValidationException(validateResult);

//        List<CampaignRecord> cp = ((CampaignSheet) sheets[0]).getCampaignRecords();
//        List<AreaRecord> ar = ((AreaSheet) sheets[2]).getAreaRecords();
//
//        //If no error in field, check related validate
//        ListValidator.validateLists(cp, ar, ListValidator::checkCpNameInArea, "Loop loop poop poop");

        return "dfasf";
    }

    @Override
    public Object[] readInputFile(FileInputStream fileInputStream) throws IOException {
        return new Object[0];
    }

}
