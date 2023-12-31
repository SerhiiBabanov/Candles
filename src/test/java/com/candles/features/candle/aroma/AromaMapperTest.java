package com.candles.features.candle.aroma;

import com.candles.features.landTranslateSupport.Local;
import com.candles.service.InitTestDataService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AromaMapperTest {

    @Test
    void toModel() {
        // Step 1: Generate test data
        Aroma aroma = InitTestDataService.getAroma();

        // Step 2: Create a Local object
        Local local = Local.EN;

        // Step 3: Call method
        AromaMapper aromaMapper = new AromaMapper();
        AromaModel aromaModel = aromaMapper.toModel(aroma, local);

        // Step 4: Assert
        assertEquals("Name EN", aromaModel.getName());
        assertEquals(List.of("Top note EN"), aromaModel.getTopNotes());
        assertEquals(List.of("Base note EN"), aromaModel.getBaseNotes());
    }
}
