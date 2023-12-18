package com.candles.features.candle.aroma;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AromaModel {
    private String name;
    private List<String> topNotes;
    private List<String> baseNotes;
}
