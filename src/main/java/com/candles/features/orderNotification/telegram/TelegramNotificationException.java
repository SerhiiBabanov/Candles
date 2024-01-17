package com.candles.features.orderNotification.telegram;

import com.candles.features.orderNotification.NotificationException;

public class TelegramNotificationException extends NotificationException {
    public TelegramNotificationException(String message) {
        super(message);
    }
}
