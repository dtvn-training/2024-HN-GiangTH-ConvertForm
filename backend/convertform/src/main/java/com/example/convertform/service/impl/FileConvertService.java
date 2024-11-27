package com.example.convertform.service.impl;

import com.example.convertform.dto.input_sheet.*;
import com.example.convertform.dto.output.*;
import com.example.convertform.dto.output.shared_lib.KeywordListOutput;
import com.example.convertform.dto.output.shared_lib.PlacementListOutput;
import com.example.convertform.service.IFileConvertService;
import com.example.convertform.service.impl.convert.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileConvertService implements IFileConvertService {
    //Main sheets convert services
    private final AdConvertService adConvertService;
    private final AdGroupConvertService adGroupConvertService;
    private final AgeConvertService ageConvertService;
    private final CampaignConvertService campaignConvertService;
    private final DeviceConvertService deviceConvertService;
    private final GenderConvertService genderConvertService;
    private final PlacementConvertService placementConvertService;
    private final RegionConvertService regionConvertService;
    private final SearchTargetConvertService searchTargetConvertService;
    private final SiteCategoryConvertService siteCategoryConvertService;

    //Shared lib convert services
    private final ListKeywordConvertService listKeywordConvertService;
    private final ListPlacementConvertService listPlacementConvertService;

    @Override
    public ConversionResult convertData(Object[] sheets) {
        //Cast from object to real type
        CampaignSheet campaignSheet = (CampaignSheet) sheets[0];
        AdGroupSheet adGroupSheet = (AdGroupSheet) sheets[1];
        AreaSheet areaSheet = (AreaSheet) sheets[2];
        TextSheet textSheet = (TextSheet) sheets[3];
        PlacementSheet placementSheet = (PlacementSheet) sheets[4];
        SearchTargetSheet searchTargetSheet = (SearchTargetSheet) sheets[5];
        SiteCategorySheet siteCategorySheet = (SiteCategorySheet) sheets[6];

        //User services convert to output data
        List<AdOutput> adOutputs = adConvertService.listConvert(textSheet, campaignSheet);
        List<AdGroupOutput> adGroupOutputs = adGroupConvertService.listConvert(adGroupSheet, campaignSheet);
        List<AgeOutput> ageOutputs = ageConvertService.listConvert(adGroupSheet, campaignSheet);
        List<CampaignOutput> campaignOutputs = campaignConvertService.listConvert(campaignSheet);
        List<DeviceOutput> deviceOutputs = deviceConvertService.listConvert(adGroupSheet, campaignSheet);
        List<GenderOutput> genderOutputs = genderConvertService.listConvert(adGroupSheet, campaignSheet);
        List<PlacementOutput> placementOutputs = placementConvertService.listConvert(placementSheet, adGroupSheet, campaignSheet);
        List<RegionOutput> regionOutputs = regionConvertService.listConvert(areaSheet, campaignSheet);
        List<SearchTargetOutput> searchTargetOutputs = searchTargetConvertService.listConvert(searchTargetSheet, adGroupSheet, campaignSheet);
        List<SiteCategoryOutput> siteCategoryOutputs = siteCategoryConvertService.listConvert(siteCategorySheet, adGroupSheet, campaignSheet);

        //Shared lib, can be null if no data entered, will be checked null/empty before write
        List<PlacementListOutput> placementListOutputs = listPlacementConvertService.listConvert(placementSheet);
        List<KeywordListOutput> keywordListOutputs = listKeywordConvertService.listConvert(searchTargetSheet);

        return ConversionResult.builder()
                .adOutputs(adOutputs)
                .adGroupOutputs(adGroupOutputs)
                .ageOutputs(ageOutputs)
                .campaignOutputs(campaignOutputs)
                .deviceOutputs(deviceOutputs)
                .genderOutputs(genderOutputs)
                .placementOutputs(placementOutputs)
                .regionOutputs(regionOutputs)
                .searchTargetOutputs(searchTargetOutputs)
                .siteCategoryOutputs(siteCategoryOutputs)
                .keywordListOutputs(keywordListOutputs)
                .placementListOutputs(placementListOutputs)
                .build();
    }
}
