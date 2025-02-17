package com.candles.features.box.kit;

import com.candles.features.landTranslateSupport.Pair;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Kit {
    private List<Pair> container = new ArrayList<>();
    private List<Pair> wax = new ArrayList<>();
    private List<Pair> wick = new ArrayList<>();
    private List<Pair> aromaToChoose = new ArrayList<>();
    private List<Pair> matchsticks = new ArrayList<>();
}
