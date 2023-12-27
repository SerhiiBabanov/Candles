package com.candles.features.order;

import com.candles.features.box.BoxEntity;
import com.candles.features.box.BoxRepository;
import com.candles.features.candle.CandleEntity;
import com.candles.features.candle.CandleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderValidatorService {
    private final BoxRepository boxRepository;
    private final CandleRepository candleRepository;

    public List<String> validate(Order order) {
        List<String> errors = new ArrayList<>();
        if (order.getCustomer() == null) {
            errors.add("Customer is null");
        } else if (order.getCustomer().getAddress() == null) {
            errors.add("Address is null");
        } else if (order.getCustomer().getAddress().isEmpty()) {
            errors.add("Address is empty");
        }
        if (order.getItems().stream().anyMatch(item -> item.getQuantity() == null)) {
            errors.add("Quantity is null");
        } else if (order.getItems().stream().anyMatch(item -> item.getQuantity() <= 0)) {
            errors.add("Quantity is less than 0");
        }
        if (order.getItems().stream().anyMatch(item -> item.getPrice() == null)) {
            errors.add("Price is null");
        } else if (order.getItems().stream().anyMatch(item -> item.getPrice().compareTo(BigDecimal.valueOf(0)) <= 0)) {
            errors.add("Price is less than 0");
        }
        if (order.getItems().stream().anyMatch(item -> item.getName() == null)) {
            errors.add("Name is null");
        } else if (order.getItems().stream().anyMatch(item -> item.getName().isEmpty())) {
            errors.add("Name is empty");
        }
        if (order.getTotal() == null) {
            errors.add("Total is null");
        }
        if (order.getItems() == null || order.getItems().isEmpty()) {
            errors.add("Items are null or empty");
        } else if (order.getItems().stream().map(Item::getCategory).anyMatch(Objects::isNull)) {
            errors.add("Category is null");
        } else {
            BigDecimal total = BigDecimal.valueOf(0);

            for (Item item : order.getItems()) {
                BigDecimal price = BigDecimal.valueOf(-100.0);
                if (item.getCategory().equals("box")) {
                    Optional<BoxEntity> box = boxRepository.findById(item.getId());
                    if (box.isPresent()) {
                        price = box.get().getPrice();
                    } else {
                        errors.add("Box with id " + item.getId() + " does not exist");
                    }
                } else if (item.getCategory().equals("candle")) {
                    Optional<CandleEntity> candle = candleRepository.findById(item.getId());
                    if (candle.isPresent()) {
                        price = candle.get().getPrice();
                    } else {
                        errors.add("Candle with id " + item.getId() + " does not exist");
                    }
                } else {
                    errors.add("Category is not box or candle");
                }
                total = total.add(price.multiply(BigDecimal.valueOf(item.getQuantity())));
            }
            if (order.getCustomCandles() != null && !order.getCustomCandles().isEmpty()) {
                for (CustomCandle candle : order.getCustomCandles()) {
                    total = total.add(candle.getPrice().multiply(BigDecimal.valueOf(candle.getQuantity())));
                }
            }
            if (order.getTotal().compareTo(total) != 0) {
                errors.add("Total is not correct");
            }
        }
        if (order.getDate() == null) {
            errors.add("Date is null");
        }
        if (order.getCustomCandles() != null && !order.getCustomCandles().isEmpty()){
            for (CustomCandle candle : order.getCustomCandles()) {
                if (candle.getContainer() == null) {
                    errors.add("Box is null");
                } else if (candle.getContainer().isEmpty()) {
                    errors.add("Box is empty");
                }
                if (candle.getWax() == null) {
                    errors.add("Wax is null");
                } else if (candle.getWax().isEmpty()) {
                    errors.add("Wax is empty");
                }
                if (candle.getAroma() == null) {
                    errors.add("Aroma name is null");
                } else if (candle.getAroma().isEmpty()) {
                    errors.add("Aroma name is empty");
                }
                if (candle.getWicks() == null) {
                    errors.add("Wicks is null");
                } else if (candle.getWicks() <= 0 || candle.getWicks() > 3) {
                    errors.add("Wicks is less than 0 or more than 3");
                }
                if (candle.getColor() == null) {
                    errors.add("Wax color is null");
                } else if (candle.getColor().isEmpty()) {
                    errors.add("Wax color is empty");
                }
                if (candle.getPrice() == null) {
                    errors.add("Price is null");
                } else if (candle.getPrice().compareTo(BigDecimal.valueOf(0)) <= 0) {
                    errors.add("Price is less than 0");
                }
                if (candle.getQuantity() == null) {
                    errors.add("Quantity is null");
                } else if (candle.getQuantity() <= 0) {
                    errors.add("Quantity is less than 0");
                }
                if (candle.getTotal() == null) {
                    errors.add("Total is null");
                } else if (candle.getTotal().compareTo(BigDecimal.valueOf(0)) <= 0) {
                    errors.add("Total is less than 0");
                }
            }
        }
        return errors;
    }
}
