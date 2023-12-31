package com.candles.features.order;

import com.candles.features.email.EmailSenderService;
import com.candles.features.landTranslateSupport.Local;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final EmailSenderService emailSenderService;

    public Order createOrder(Order order, Local lang) {
        Order savedOrder = orderRepository.save(order);
        try {
            emailSenderService.sendOrderConfirmationEmail(order, lang);
        } catch (MessagingException e) {
            throw new RuntimeException(e);      //add logging
        }
        return savedOrder;
    }
}
