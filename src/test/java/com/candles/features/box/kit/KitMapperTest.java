package com.candles.features.box.kit;

import com.candles.features.landTranslateSupport.Local;
import com.candles.features.landTranslateSupport.Pair;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class KitMapperTest {

    @Test
    void toDto() {
        // Step 1: Generate test data
        Kit kit = new Kit();
        kit.setContainer(Arrays.asList(new Pair(Local.EN, "Container EN"), new Pair(Local.UA, "Container UA")));
        kit.setWax(Arrays.asList(new Pair(Local.EN, "Test Wax"), new Pair(Local.UA, "Test Wax UA")));
        kit.setWick(Arrays.asList(new Pair(Local.EN, "Test Wick"), new Pair(Local.UA, "Test Wick UA")));
        kit.setAromaToChoose(Arrays.asList(new Pair(Local.EN, "Aroma EN"), new Pair(Local.UA, "Aroma UA")));
        kit.setMatchsticks(Arrays.asList(new Pair(Local.EN, "Test Matchsticks"), new Pair(Local.UA, "Test Matchsticks UA")));

        // Step 2: Create a Local object
        Local local = Local.EN;

        // Step 3: Call method
        KitMapper kitMapper = new KitMapper();
        KitModel kitModel = kitMapper.toModel(kit, local);

        // Step 4: Assert
        assertEquals("Container EN", kitModel.getContainer());
        assertEquals("Test Wax", kitModel.getWax());
        assertEquals("Test Wick", kitModel.getWick());
        assertEquals("Aroma EN", kitModel.getAromaToChoose());
        assertEquals("Test Matchsticks", kitModel.getMatchsticks());
    }
}
