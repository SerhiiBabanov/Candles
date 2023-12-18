package com.candles.features.candle.aroma;

import com.candles.model.Pair;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Aroma {
    private List<Pair> name = new ArrayList<>();
    private List<List<Pair>> topNotes = new ArrayList<>();
    private List<List<Pair>> baseNotes = new ArrayList<>();
}
