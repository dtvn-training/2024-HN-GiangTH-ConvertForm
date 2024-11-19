package com.example.convertform.validation;

import com.example.convertform.dto.input.AdGroupRecord;
import com.example.convertform.dto.input.PlacementTable;
import com.example.convertform.dto.input.SearchTargetTable;
import com.example.convertform.dto.input.target_list_item.TargetPair;
import com.example.convertform.dto.input_sheet.SiteCategorySheet;

import java.util.*;

public class AdGroupTargetValidator {

    public static String validateAdGroupTargets(AdGroupRecord adGroupRecord,
                                              List<PlacementTable> placementTables,
                                              List<SearchTargetTable> searchTargetTables,
                                              SiteCategorySheet siteCategorySheet) {

        List<TargetPair> pairs = getTargetPairList(adGroupRecord);
        Map<String, List<String>> siteCategories = siteCategorySheet.getTargetCategory();
        StringBuilder stringBuilder = new StringBuilder("AdGroup Target Validate: \n");

        for (TargetPair pair : pairs) {
            if (pair.getTargetName() == null || pair.getNumber() == null) continue;
            System.out.println(pair.getPairName());

            String type = getType(pair.getTargetName());
            boolean exist = switch (type) {
                case "Search" -> searchTargetTables.stream()
                        .anyMatch(target -> target.getTargetName().equals(pair.getPairName()));
                case "Placement" -> placementTables.stream()
                        .anyMatch(target -> target.getTargetName().equals(pair.getPairName()));
                case "Site" -> siteCategories.containsKey(pair.getPairName());
                default -> false;
            };

            if (!exist) stringBuilder.append("Ad Group No ").append(adGroupRecord.getNo()).append(" contains an invalid target : ")
                    .append(pair.getTargetName()).append(pair.getNumber()).append("\n");
        }

        return stringBuilder.toString();
    }

    public static String validateUnusedTarget(List<AdGroupRecord> adGroupRecords,
                                       List<PlacementTable> placementTables,
                                       List<SearchTargetTable> searchTargetTables,
                                       SiteCategorySheet siteCategorySheet) {

        StringBuilder stringBuilder = new StringBuilder("Unused (not exist in AdGroup) targets: \n");
        Set<String> usedInAdGroupTarget = new HashSet<>();

        for (AdGroupRecord adGroupRecord : adGroupRecords) {
            List<String> adGroupTarget = new ArrayList<>();
            for (TargetPair pair : getTargetPairList(adGroupRecord)) {
                if (pair.getTargetName() == null || pair.getNumber() == null) continue;
                adGroupTarget.add(pair.getPairName());
            }
            usedInAdGroupTarget.addAll(adGroupTarget);
        }

        checkUnusedPlacementTarget(placementTables, usedInAdGroupTarget, stringBuilder);
        checkUnusedSearchTarget(searchTargetTables, usedInAdGroupTarget, stringBuilder);

        return stringBuilder.toString();
    }

    public static void checkUnusedPlacementTarget(List<PlacementTable> placementTables,
                                             Set<String> used,
                                             StringBuilder stringBuilder) {
        for (PlacementTable placementTable : placementTables) {
            if (!used.contains(placementTable.getTargetName())) stringBuilder.append(placementTable.getTargetName()).append("\n");
        }
    }

    public static void checkUnusedSearchTarget(List<SearchTargetTable> searchTargetTables,
                                                  Set<String> used,
                                                  StringBuilder stringBuilder) {
        for (SearchTargetTable searchTable : searchTargetTables) {
            if (!used.contains(searchTable.getTargetName())) stringBuilder.append(searchTable.getTargetName()).append("\n");
        }
    }

    public static void checkUnusedSiteCategory(SiteCategorySheet siteCategorySheet,
                                               Set<String> used,
                                               StringBuilder stringBuilder) {
        for (String key : siteCategorySheet.getTargetCategory().keySet()) {
            if (!used.contains(key)) stringBuilder.append(key).append("\n");
        }
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
