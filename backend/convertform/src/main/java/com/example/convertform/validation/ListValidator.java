package com.example.convertform.validation;

import com.example.convertform.dto.input.AdGroupRecord;
import com.example.convertform.dto.input.AreaRecord;
import com.example.convertform.dto.input.CampaignRecord;
import com.example.convertform.dto.input.TextRecord;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;

public class ListValidator {
    public static <T, U> void validateTwoLists(
            List<T> list1,
            List<U> list2,
            BiPredicate<T, List<U>> condition,
            String errorMsg
    ) {
        for (T item : list1) {
            if (!condition.test(item, list2)) {
                System.out.println(errorMsg);
            }
        }
    }

    public static void validateDuplicateCampaignList(
            List<CampaignRecord> list,
            String errorMsg
    ) {
        Set<String> stringSet = new HashSet<>();
        for (CampaignRecord record : list) {
            String name = record.getCName();
            if (!stringSet.add(name)) System.out.println(errorMsg);;
        }
    }

    public static void validateDuplicateAdGroupList(
            List<AdGroupRecord> list,
            String errorMsg
    ) {
        Set<String> stringSet = new HashSet<>();
        for (AdGroupRecord record : list) {
            String name = record.getAdGpName();
            if (!stringSet.add(name)) System.out.println(errorMsg);
        }
    }

    public static boolean checkCpNameInArea(CampaignRecord c, List<AreaRecord> lA) {
        boolean exists = lA.stream().anyMatch(A -> A.getCName().equals(c.getCName()));
        if ("Yes".equals(String.valueOf(c.getCampaignArea()))) {
            return exists;
        } else if ("None".equals(String.valueOf(c.getCampaignArea()))) {
            return !exists;
        }
        return true;
    };

    public static boolean checkCpNameInAdGroup(AdGroupRecord adGp, List<CampaignRecord> cL) {
        return cL.stream().anyMatch(c -> c.getCName().equals(adGp.getCName()));
    }

    public static boolean checkCpNameInArea(AreaRecord aR, List<CampaignRecord> cL) {
        return cL.stream().anyMatch(c -> c.getCName().equals(aR.getCName()));
    }

    public static boolean checkAdGpNameInArea(AreaRecord aR, List<AdGroupRecord> adGpR) {
        return adGpR.stream().anyMatch(c -> c.getCName().equals(aR.getCName()));
    }

    public static boolean checkCpNameInText(TextRecord tR, List<CampaignRecord> cL) {
        return cL.stream().anyMatch(c -> c.getCName().equals(tR.getCName()));
    }

    public static boolean checkAdGpNameInText(TextRecord tR, List<AdGroupRecord> adGpR) {
        return adGpR.stream().anyMatch(c -> c.getCName().equals(tR.getCName()));
    }
}
