package com.candles.features.order;

import com.candles.features.box.BoxService;
import com.candles.features.candle.CandleService;
import com.candles.features.landTranslateSupport.Local;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final BoxService boxService;
    private final CandleService candleService;

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public void fillNameOfItems(Order order, Local lang) {
        if (order.getItems() != null && !order.getItems().isEmpty()) {
            for (Item item : order.getItems()) {
                if (item.getCategory().equals("box")) {
                    item.setName(boxService.findNameById(item.getId(), lang));
                } else if (item.getCategory().equals("candle")) {
                    item.setName(candleService.findNameById(item.getId(), lang));
                }
            }
        }
    }
}
