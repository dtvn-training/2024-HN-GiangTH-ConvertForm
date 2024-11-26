package com.example.convertform.validation.common;

import com.example.convertform.dto.input.AdGroupRecord;
import com.example.convertform.dto.input.PlacementTable;
import com.example.convertform.dto.input.SearchTargetTable;
import com.example.convertform.dto.input.target_list_item.TargetPair;
import com.example.convertform.dto.input_sheet.SiteCategorySheet;
import com.example.convertform.dto.response.ErrorFromRecordDTO;
import com.example.convertform.dto.response.ErrorFromTargetTableDTO;
import com.example.convertform.dto.response.ValidationErrorResponseDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

public class AdGroupTargetValidator {

    public static List<ErrorFromRecordDTO> validateAdGroupTargets(AdGroupRecord adGroupRecord,
                                                                  List<PlacementTable> placementTables,
                                                                  List<SearchTargetTable> searchTargetTables,
                                                                  SiteCategorySheet siteCategorySheet) {

        List<TargetPair> pairs = getTargetPairList(adGroupRecord);
        Map<String, List<String>> siteCategories = siteCategorySheet.getTargetCategory();
        List<ErrorFromRecordDTO> errorFromRecordDTOS = new ArrayList<>();

        for (TargetPair pair : pairs) {
            if (pair.getTargetName() == null || pair.getNumber() == null) continue;

            String type = getType(pair.getTargetName());
            boolean exist = switch (type) {
                case "Search" -> searchTargetTables.stream()
                        .anyMatch(target -> target.getTargetName().equals(pair.getPairName()));
                case "Placement" -> placementTables.stream()
                        .anyMatch(target -> target.getTargetName().equals(pair.getPairName()));
                case "Site" -> siteCategories.containsKey(pair.getPairName());
                default -> false;
            };

            if (!exist) errorFromRecordDTOS.add(new ErrorFromRecordDTO(adGroupRecord.getNo(), pair.getPairName(), "Invalid target: target not exist"));
        }

        return errorFromRecordDTOS;
    }

    public static void validateUnusedTarget(List<AdGroupRecord> adGroupRecords,
                                                                List<PlacementTable> placementTables,
                                                                List<SearchTargetTable> searchTargetTables,
                                                                SiteCategorySheet siteCategorySheet,
                                                                List<ValidationErrorResponseDTO> validationErrorResponseDTOS) {

        Set<String> usedInAdGroupTarget = new HashSet<>();

        for (AdGroupRecord adGroupRecord : adGroupRecords) {
            List<String> adGroupTarget = new ArrayList<>();
            for (TargetPair pair : getTargetPairList(adGroupRecord)) {
                if (pair.getTargetName() == null || pair.getNumber() == null) continue;
                adGroupTarget.add(pair.getPairName());
            }
            usedInAdGroupTarget.addAll(adGroupTarget);
        }

        validationErrorResponseDTOS.get(4).getErrorSheetDTOList().addAll(checkUnusedPlacementTarget(placementTables, usedInAdGroupTarget));
        validationErrorResponseDTOS.get(5).getErrorSheetDTOList().addAll(checkUnusedSearchTarget(searchTargetTables, usedInAdGroupTarget));
        validationErrorResponseDTOS.get(6).getErrorSheetDTOList().addAll(checkUnusedSiteCategory(siteCategorySheet, usedInAdGroupTarget));
    }

    public static List<ErrorFromTargetTableDTO> checkUnusedPlacementTarget(List<PlacementTable> placementTables,
                                             Set<String> used) {
        List<ErrorFromTargetTableDTO> error = new ArrayList<>();
        for (PlacementTable placementTable : placementTables) {
            if (!used.contains(placementTable.getTargetName()) && placementTable.getTargetName() != null)
                error.add(new ErrorFromTargetTableDTO("Unused in Ad Group", placementTable.getTargetName()));
        }
        return error;
    }

    public static List<ErrorFromTargetTableDTO> checkUnusedSearchTarget(List<SearchTargetTable> searchTargetTables,
                                                  Set<String> used) {
        List<ErrorFromTargetTableDTO> error = new ArrayList<>();
        for (SearchTargetTable searchTable : searchTargetTables) {
            if (!used.contains(searchTable.getTargetName()) && searchTable.getTargetName() != null)
                error.add(new ErrorFromTargetTableDTO("Unused in Ad Group", searchTable.getTargetName()));
        }
        return error;
    }

    public static List<ErrorFromTargetTableDTO> checkUnusedSiteCategory(SiteCategorySheet siteCategorySheet,
                                               Set<String> used) {
        List<ErrorFromTargetTableDTO> error = new ArrayList<>();
        for (String key : siteCategorySheet.getTargetCategory().keySet()) {
            if (!used.contains(key) && !siteCategorySheet.getTargetCategory().get(key).isEmpty())
                error.add(new ErrorFromTargetTableDTO("Unused in Ad Group", key));
        }
        return error;
    }

    public static List<TargetPair> getTargetPairList(AdGroupRecord adGroupRecord) {
        List<TargetPair> pairs = new ArrayList<>();
        pairs.add(new TargetPair(adGroupRecord.getTargetName1(), adGroupRecord.getNumber1()));
        pairs.add(new TargetPair(adGroupRecord.getTargetName2(), adGroupRecord.getNumber2()));
        pairs.add(new TargetPair(adGroupRecord.getTargetName3(), adGroupRecord.getNumber3()));
        pairs.add(new TargetPair(adGroupRecord.getTargetName4(), adGroupRecord.getNumber4()));
        pairs.add(new TargetPair(adGroupRecord.getTargetName5(), adGroupRecord.getNumber5()));
        pairs.add(new TargetPair(adGroupRecord.getTargetName6(), adGroupRecord.getNumber6()));
        pairs.add(new TargetPair(adGroupRecord.getTargetName7(), adGroupRecord.getNumber7()));
        pairs.add(new TargetPair(adGroupRecord.getTargetName8(), adGroupRecord.getNumber8()));
        pairs.add(new TargetPair(adGroupRecord.getTargetName9(), adGroupRecord.getNumber9()));
        pairs.add(new TargetPair(adGroupRecord.getTargetName10(), adGroupRecord.getNumber10()));
        return pairs;
    }

    public static String getType(String targetName) {
        if (targetName.contains("Site")) return "Site";
        if (targetName.contains("Search")) return "Search";
        if (targetName.contains("Placement")) return "Placement";
        throw new IllegalArgumentException("Unknown target type: " + targetName);
    }
}
