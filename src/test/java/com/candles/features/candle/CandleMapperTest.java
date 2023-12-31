package com.candles.features.candle;

import com.candles.features.candle.aroma.AromaMapper;
import com.candles.features.landTranslateSupport.Local;
import com.candles.service.InitTestDataService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CandleMapperTest {

    @Test
    void toModel() {
        // Step 1: Generate test data
        CandleEntity candleEntity = InitTestDataService.getCandleEntity();

        // Step 2: Create a Local object
        Local local = Local.EN;

        // Step 3: Call method
        CandleMapper candleMapper = new CandleMapper(new AromaMapper());
        CandleModel candleModel = candleMapper.toModel(candleEntity, local);

        // Step 4: Assert
        assertEquals("Test Id", candleModel.getId());
        assertEquals("Title EN", candleModel.getTitle());
        assertEquals("Name EN", candleModel.getName());
        assertEquals("Test Wax", candleModel.getWax());
        assertEquals("Test Slug", candleModel.getSlug());
        assertNotNull(candleModel.getAroma());
        assertEquals("Test Wick", candleModel.getWick());
        assertEquals(1, candleModel.getWicks());
        assertEquals("Container EN", candleModel.getContainer());
        assertEquals("Test Volume", candleModel.getVolume());
        assertEquals(new BigDecimal("100"), candleModel.getPrice());
        assertEquals("Description EN", candleModel.getDescription());
        assertEquals("Container color EN", candleModel.getContainerColor());
        assertEquals("Wax color EN", candleModel.getWaxColor());
        assertEquals(List.of("Test image"), candleModel.getImages());
    }
}
