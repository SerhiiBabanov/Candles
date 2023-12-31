package com.candles.features.box;

import com.candles.features.box.kit.KitMapper;
import com.candles.features.landTranslateSupport.Local;
import com.candles.service.InitTestDataService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class BoxMapperTest {

    @Test
    void toModel() {
        // Step 1: Generate test data
        BoxEntity boxEntity = InitTestDataService.getBoxEntity();

        // Step 2: Create a Local object
        Local local = Local.EN;

        // Step 3: Call method
        BoxMapper boxMapper = new BoxMapper(new KitMapper());
        BoxModel boxModel = boxMapper.toModel(boxEntity, local);

        // Step 4: Assert
        assertEquals("Test Id", boxModel.getId());
        assertEquals("Title EN", boxModel.getTitle());
        assertEquals("Name EN", boxModel.getName());
        assertEquals("Test Slug", boxModel.getSlug());
        assertEquals("Test Volume", boxModel.getVolume());
        assertEquals(new BigDecimal("10.00"), boxModel.getPrice());
        assertEquals("Description EN", boxModel.getDescription());
        assertEquals(Arrays.asList("Image1", "Image2"), boxModel.getImages());
        assertNotNull(boxModel.getKit());
    }
}
