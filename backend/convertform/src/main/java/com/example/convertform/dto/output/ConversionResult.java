package com.example.convertform.dto.output;

import com.example.convertform.dto.output.shared_lib.KeywordListOutput;
import com.example.convertform.dto.output.shared_lib.PlacementListOutput;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ConversionResult {
    List<CampaignOutput> campaignOutputs;
    List<AdGroupOutput> adGroupOutputs;
    List<AdOutput> adOutputs;
    List<GenderOutput> genderOutputs;
    List<AgeOutput> ageOutputs;
    List<RegionOutput> regionOutputs;
    List<DeviceOutput> deviceOutputs;
    List<SearchTargetOutput> searchTargetOutputs;
    List<PlacementOutput> placementOutputs;
    List<SiteCategoryOutput> siteCategoryOutputs;

    List<KeywordListOutput> keywordListOutputs;
    List<PlacementListOutput> placementListOutputs;
}
