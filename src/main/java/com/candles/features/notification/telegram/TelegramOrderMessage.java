package com.candles.features.notification.telegram;

import com.candles.features.order.Order;
import lombok.AllArgsConstructor;

import java.util.stream.Collectors;

@AllArgsConstructor
public class TelegramOrderMessage {
    private final Order order;

    @Override
    public String toString() {
        return "Order #" + order.getId() + ":\n" +
                "customer= " + order.getCustomer().getFullName() + "\n" +
                "phone= " + order.getCustomer().getPhone() + "\n" +
                "email= " + order.getCustomer().getEmail() + "\n" +
                "address= " + order.getCustomer().getAddress() + "\n" +
                "comment= " + order.getCustomer().getComment() + "\n" +
                "items: " + "\n" +
                order.getItems().stream()
                        .map(item -> "  " + item.getCategory() + " - quantity:" + item.getQuantity())
                        .collect(Collectors.joining("\n"))
                + "\n" +
                order.getCustomCandles().stream()
                        .map(item -> "  " + "Custom candle " + " - quantity:" + item.getQuantity())
                        .collect(Collectors.joining("\n"))
                + "\n" +
                "total price=" + order.getTotal();
    }
}
