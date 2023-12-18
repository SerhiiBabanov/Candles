package com.candles.features.local;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocaleServiceTest {

    @Test
    void isEnLocale() {
        assertTrue(LocaleService.isEnLocale("Test"));
        assertFalse(LocaleService.isEnLocale("Тест"));
    }
}
