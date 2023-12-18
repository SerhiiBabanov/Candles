package com.candles.features.local;

import org.springframework.stereotype.Service;

@Service
public class LocaleService {
    public static boolean isEnLocale(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (Character.UnicodeBlock.of(s.charAt(i)).equals(Character.UnicodeBlock.CYRILLIC)) {
                return false;
            }
        }
        return true;
    }
}
