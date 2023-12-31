package com.candles.features.candle.aroma;

import com.candles.features.landTranslateSupport.Local;
import com.candles.service.Utils;
import org.springframework.stereotype.Service;

@Service
public class AromaMapper {
    public AromaModel toModel(Aroma aroma, Local locale) {
        AromaModel aromaModel = new AromaModel();
        aromaModel.setName(Utils.getPropertyByLocal(aroma.getName(), locale));
        aromaModel.setTopNotes(aroma.getTopNotes().stream()
                .map(topNote -> Utils.getPropertyByLocal(topNote, locale))
                .toList());
        aromaModel.setBaseNotes(aroma.getBaseNotes().stream()
                .map(baseNote -> Utils.getPropertyByLocal(baseNote, locale))
                .toList());
        return aromaModel;
    }
}
