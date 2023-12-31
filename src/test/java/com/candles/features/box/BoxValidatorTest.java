package com.candles.features.box;

import com.candles.features.landTranslateSupport.Local;
import com.candles.features.landTranslateSupport.Pair;
import com.candles.service.InitTestDataService;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BoxValidatorTest {

    @Test
    void validateCorrectBoxWithoutError() {
        // Step 1: Generate test data
        BoxEntity boxEntity = InitTestDataService.getBoxEntity();

        // Step 2: Create an Errors object
        Errors errors = new BeanPropertyBindingResult(boxEntity, "boxEntity");

        // Step 3: Call method
        BoxValidator boxValidator = new BoxValidator();
        boxValidator.validate(boxEntity, errors);

        // Step 4: Assert
        assertFalse(errors.hasErrors());
    }

    @Test
    void validateIncorrectBoxWithError() {
        // Step 1: Generate test data
        BoxEntity boxEntity = InitTestDataService.getBoxEntity();
        boxEntity.setName(null);
        boxEntity.setTitle(List.of(new Pair(Local.EN, "Name EN")));

        // Step 2: Create an Errors object
        Errors errors = new BeanPropertyBindingResult(boxEntity, "boxEntity");

        // Step 3: Call method
        BoxValidator boxValidator = new BoxValidator();
        boxValidator.validate(boxEntity, errors);

        // Step 4: Assert
        assertTrue(errors.hasErrors());
    }
}
