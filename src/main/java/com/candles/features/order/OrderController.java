package com.candles.features.order;

import com.candles.features.landTranslateSupport.Local;
import com.candles.features.notification.NotificationService;
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
    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order,
                                             @RequestParam(name = "lang", defaultValue = "UA", required = false)
                                             Local lang) {
        List<String> errors = orderValidatorService.validate(order);
        if (!errors.isEmpty()) {
            throw new OrderValidateException(errors.toString());
        }
        Order savedOrder = orderService.createOrder(order);
        orderService.fillNameOfItems(savedOrder, lang);
        notificationService.sendOrderNotification(savedOrder, lang);
        return ResponseEntity.ok(savedOrder);
    }
}
