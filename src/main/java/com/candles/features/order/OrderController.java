package com.candles.features.order;

import com.candles.features.landTranslateSupport.Local;
import com.candles.features.orderNotification.email.EmailNotifierService;
import com.candles.features.orderNotification.telegram.TelegramNotifierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderValidatorService orderValidatorService;
    private final TelegramNotifierService telegramNotifierService;
    private final EmailNotifierService emailNotifierService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order,
                                             @RequestParam(name = "lang", defaultValue = "UA", required = false)
                                             Local lang) {
        List<String> errors = orderValidatorService.validate(order);
        if (!errors.isEmpty()) {
            throw new OrderValidateException(errors.toString());
        }
        Order savedOrder = orderService.createOrder(order);
        //send notifications
        try {
            telegramNotifierService.sendOrderNotification(order);
            emailNotifierService.sendOrderNotification(order, lang);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(savedOrder);
    }
}
