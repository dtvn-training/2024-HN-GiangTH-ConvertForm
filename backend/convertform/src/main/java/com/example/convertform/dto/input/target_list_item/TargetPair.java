package com.example.convertform.dto.input.target_list_item;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TargetPair {
    private String targetName;
    private String number;

    public String getPairName() {
        return this.targetName+this.number;
    }
}
