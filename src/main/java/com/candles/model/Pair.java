package com.candles.model;

import com.candles.features.local.Local;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pair {
    private Local key;
    private String value;

    public String get(Local key) {
        return this.value;
    }
}
