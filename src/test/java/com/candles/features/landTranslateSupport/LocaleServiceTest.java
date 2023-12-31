package com.candles.features.landTranslateSupport;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocaleServiceTest {

    @Test
    void isEnLocale() {
        assertTrue(LocaleService.isEnSymbolOnly("Test"));
        assertFalse(LocaleService.isEnSymbolOnly("Тест"));
    }
}
