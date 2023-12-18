package com.candles.service;

import com.candles.features.local.Local;
import com.candles.model.Pair;

import java.util.List;

public class Utils {
    public static String getPropertyByLocal(List<Pair> pairs, Local local) {
        return pairs.stream()
                .filter(pair -> pair.getKey().equals(local))
                .map(Pair::getValue)
                .findFirst()
                .orElse(null);
    }
    public static boolean checkInputStringForError(String input) {
        return (input == null || input.trim().length() == 0);
    }
}
