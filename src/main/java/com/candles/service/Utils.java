package com.candles.service;

import com.candles.features.landTranslateSupport.Local;
import com.candles.features.landTranslateSupport.Pair;

import java.util.List;

public class Utils {
    public static String getPropertyByLocal(List<Pair> pairs, Local lang) {
        return pairs.stream()
                .filter(pair -> pair.getKey().equals(lang))
                .map(Pair::getValue)
                .findFirst()
                .orElse(null);
    }
    public static boolean checkInputStringForError(String input) {
        return (input == null || input.trim().isEmpty());
    }
}
