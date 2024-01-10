package com.candles.features.order;

import com.candles.features.email.EmailSenderService;
import com.candles.features.landTranslateSupport.Local;
import com.candles.features.orderNotification.telegram.TelegramNotifierService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final EmailSenderService emailSenderService;
    private final TelegramNotifierService telegramNotifierService;

    public Order createOrder(Order order, Local lang) {
        Order savedOrder = orderRepository.save(order);
        try {
            emailSenderService.sendOrderConfirmationEmail(order, lang);
        } catch (MessagingException e) {
            throw new RuntimeException(e);      //add logging
        }
        try {
            telegramNotifierService.sendOrderNotification(order);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return savedOrder;
    }
}
