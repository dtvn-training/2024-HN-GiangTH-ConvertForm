package com.example.convertform.service.impl;

import com.example.convertform.dto.input.*;
import com.example.convertform.dto.input_sheet.*;
import com.example.convertform.dto.response.ValidationErrorResponseDTO;
import com.example.convertform.dto.response.ErrorFromRecordDTO;
import com.example.convertform.service.IFileValidateService;
import com.example.convertform.validation.common.AdGroupTargetValidator;
import com.example.convertform.validation.common.ListValidator;
import com.example.convertform.validation.common.ValidationUtils;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FileValidateService implements IFileValidateService {
    @Override
    public List<ValidationErrorResponseDTO> validateSingleFieldData(Object[] objects) throws ValidationException{
        List<ValidationErrorResponseDTO> validationErrorResponseDTOList = new ArrayList<>();

        for (Object o : objects) {
            Set<ConstraintViolation<Object>> violations = ValidationUtils.validate(o);
            ValidationErrorResponseDTO validationErrorResponseDTO = ValidationUtils.buildResponseDTO(o, violations);
            validationErrorResponseDTO.getErrorRecordDTOList().sort(Comparator.comparing(ErrorFromRecordDTO::getRecordNo));
            validationErrorResponseDTOList.add(validationErrorResponseDTO);
        }
        return validationErrorResponseDTOList;
    }

    @Override
    public void validateRelated(Object[] sheets,
                                List<ValidationErrorResponseDTO> validationErrorResponseDTOList) {

        //Get sheets data to validate
        List<CampaignRecord> cp = ((CampaignSheet) sheets[0]).getCampaignRecords();
        List<AdGroupRecord> adGp = ((AdGroupSheet) sheets[1]).getAdGroupRecordList();
        List<AreaRecord> ar = ((AreaSheet) sheets[2]).getAreaRecords();
        List<TextRecord> tr = ((TextSheet) sheets[3]).getTextRecords();
        List<SearchTargetTable> searchTables = ((SearchTargetSheet) sheets[5]).getSearchTargetTables();
        List<PlacementTable> placementTables = ((PlacementSheet) sheets[4]).getPlacementTables();
        SiteCategorySheet siteCategorySheet = (SiteCategorySheet) sheets[6];

        //Validate CampaignSheet
        validationErrorResponseDTOList.getFirst().getErrorRecordDTOList().addAll(ListValidator.validateDuplicateCampaignList(cp));
        validationErrorResponseDTOList.getFirst().getErrorRecordDTOList().addAll(ListValidator.validateTwoLists(cp, ar, ListValidator::checkAreaInCpName, "Area"));

        //Validate AdGroupSheet
        validationErrorResponseDTOList.get(1).getErrorRecordDTOList().addAll(ListValidator.validateDuplicateAdGroupList(adGp));
        validationErrorResponseDTOList.get(1).getErrorRecordDTOList().addAll(ListValidator.validateTwoLists(adGp, cp, ListValidator::checkCpNameInAdGroup, "CP name"));

        for (AdGroupRecord adGroupRecord : adGp) {
            validationErrorResponseDTOList.get(1).getErrorRecordDTOList().addAll(AdGroupTargetValidator.validateAdGroupTargets(adGroupRecord, placementTables, searchTables, siteCategorySheet));
        }

        //Validate PlacementSheet, SearchSheet & SiteCategorySheet with Targets existed in AdGroup
        AdGroupTargetValidator.validateUnusedTarget(adGp, placementTables, searchTables, siteCategorySheet, validationErrorResponseDTOList);

        //Validate AreaSheet
        validationErrorResponseDTOList.get(2).getErrorRecordDTOList().addAll(ListValidator.validateTwoLists(ar, cp, ListValidator::checkCpNameInArea, "CP name"));
        validationErrorResponseDTOList.get(2).getErrorRecordDTOList().addAll(ListValidator.validateTwoLists(ar, adGp, ListValidator::checkAdGpNameInArea, "Ad Group name"));

        //Validate TextSheet
        validationErrorResponseDTOList.get(3).getErrorRecordDTOList().addAll(ListValidator.validateTwoLists(tr, cp, ListValidator::checkCpNameInText, "CP name"));
        validationErrorResponseDTOList.get(3).getErrorRecordDTOList().addAll(ListValidator.validateTwoLists(tr, adGp, ListValidator::checkAdGpNameInText, "Ad Group name"));
    }
}
