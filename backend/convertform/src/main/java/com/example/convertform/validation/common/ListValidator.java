package com.example.convertform.validation.common;

import com.example.convertform.dto.input.*;
import com.example.convertform.dto.response.ErrorFromRecordDTO;

import java.util.*;
import java.util.function.BiFunction;

public class ListValidator {
    public static <T, U> List<ErrorFromRecordDTO> validateTwoLists(
            List<T> list1,
            List<U> list2,
            BiFunction<T, List<U>, Optional<String>> condition,
            String fieldName
    ) {
        List<ErrorFromRecordDTO> errorFromRecordDTOS = new ArrayList<>();
        for (T item : list1) {
            if (item == null) continue;
            Optional<String> validateResult = condition.apply(item, list2);

            if (validateResult.isPresent()) {
                BaseRecord baseRecord = (BaseRecord) item;
                errorFromRecordDTOS.add(new ErrorFromRecordDTO(baseRecord.getNo(), fieldName, validateResult.get()));
            }
        }
        return errorFromRecordDTOS;
    }

    public static List<ErrorFromRecordDTO> validateDuplicateCampaignList(
            List<CampaignRecord> list
    ) {
        List<ErrorFromRecordDTO> recordDTOS = new ArrayList<>();
        Set<String> stringSet = new HashSet<>();
        for (CampaignRecord record : list) {
            String name = record.getCName();
            if (!stringSet.add(name)) recordDTOS.add(new ErrorFromRecordDTO(record.getNo(), "Campaign Name", "Campaign name existed"));
        }
        return recordDTOS;
    }

    public static List<ErrorFromRecordDTO> validateDuplicateAdGroupList(
            List<AdGroupRecord> list
    ) {
        List<ErrorFromRecordDTO> recordDTOS = new ArrayList<>();
        Set<String> stringSet = new HashSet<>();
        for (AdGroupRecord record : list) {
            String name = record.getAdGpName();
            if (!stringSet.add(name)) recordDTOS.add(new ErrorFromRecordDTO(record.getNo(),  "Ad Group Name", "Ad Group Name existed"));
        }
        return recordDTOS;
    }

    public static Optional<String> checkAreaInCpName(CampaignRecord c, List<AreaRecord> lA) {
        boolean exists = lA.stream().anyMatch(A -> A.getCName().equals(c.getCName()));

        if ("Yes".equals(String.valueOf(c.getCampaignArea())) && !exists) {
            return Optional.of("Area is Yes but CP name not exist in area sheet");
        } else if ("None".equals(String.valueOf(c.getCampaignArea())) && exists) {
            return Optional.of("Area is none but found CP name in area sheet");
        }
        return Optional.empty();
    }

    public static Optional<String> checkCpNameInAdGroup(AdGroupRecord adGp, List<CampaignRecord> cL) {
        boolean exist = cL.stream().anyMatch(c -> c.getCName().equals(adGp.getCName()));

        if (!exist) return Optional.of("CP name not found in CP sheet");

        return Optional.empty();
    }

    public static Optional<String> checkCpNameInArea(AreaRecord aR, List<CampaignRecord> cL) {
        if (aR.getCName() == null) return Optional.empty();
        boolean exist = cL.stream().anyMatch(c -> c.getCName().equals(aR.getCName()));

        if (!exist) return Optional.of("CP name not found in CP Sheet");

        return Optional.empty();
    }

    public static Optional<String> checkAdGpNameInArea(AreaRecord aR, List<AdGroupRecord> adGpR) {
        if (aR.getAdGpName() == null) return Optional.empty();
        boolean exist = adGpR.stream().anyMatch(c -> c.getAdGpName().equals(aR.getAdGpName()));

        if (!exist) return Optional.of("Ad Group name not found in Ad Group Sheet");

        return Optional.empty();
    }

    public static Optional<String> checkCpNameInText(TextRecord tR, List<CampaignRecord> cL) {
        if (tR.getCName() == null) return Optional.empty();
        boolean exist = cL.stream().anyMatch(c -> c.getCName().equals(tR.getCName()));

        if (!exist) return Optional.of("CP name not found in CP Sheet");

        return Optional.empty();
    }

    public static Optional<String> checkAdGpNameInText(TextRecord tR, List<AdGroupRecord> adGpR) {
        if (tR.getAdGpName() == null) return Optional.empty();
        boolean exist = adGpR.stream().anyMatch(c -> c.getAdGpName().equals(tR.getAdGpName()));

        if (!exist) return Optional.of("Ad Group name not found in Ad Group Sheet");

        return Optional.empty();
    }
}
