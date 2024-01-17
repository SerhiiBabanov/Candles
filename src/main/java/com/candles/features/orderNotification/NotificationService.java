package com.candles.features.orderNotification;

import com.candles.features.landTranslateSupport.Local;
import com.candles.features.order.Order;
import com.candles.features.orderNotification.email.EmailNotifierService;
import com.candles.features.orderNotification.telegram.TelegramNotifierService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final TelegramNotifierService telegramNotifierService;
    private final EmailNotifierService emailNotifierService;

    @Async
    public void sendOrderNotification(Order order, Local lang) {
        try {
            telegramNotifierService.sendOrderNotification(order);
            emailNotifierService.sendOrderNotification(order, lang);
        } catch (NotificationException e) {
            //TODO: log
        }
    }

}
