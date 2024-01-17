package com.candles.features.notification.telegram;

import com.candles.features.notification.NotificationException;

public class TelegramNotificationException extends NotificationException {
    public TelegramNotificationException(String message) {
        super(message);
    }
}
