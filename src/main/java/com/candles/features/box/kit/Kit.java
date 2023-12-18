package com.candles.features.box.kit;

import com.candles.model.Pair;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Kit {
    private List<Pair> container = new ArrayList<>();
    private String wax;
    private String wick;
    private List<Pair> aromaToChoose = new ArrayList<>();
    private String matchsticks;
}
