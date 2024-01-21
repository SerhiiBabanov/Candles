package com.candles.features.order;

import com.candles.features.box.BoxRepository;
import com.candles.features.candle.CandleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderValidatorService {
    private final BoxRepository boxRepository;
    private final CandleRepository candleRepository;

    public List<String> validate(Order order) {
        List<String> errors = new ArrayList<>();
        isCustomerHasAllInformation(order, errors);
        isItemsOrCustomCandlesPresent(order, errors);
        isItemsInfoCorrect(order, errors);
        isCustomCandlesCorrect(order, errors);
        isTotalPriceForOrderCorrect(order, errors);
        isOrderPaymentAndDeliveryCorrect(order, errors);
        if (order.getDate() == null) {
            errors.add("Date is null");
        } else if (order.getDate().isEmpty()) {
            errors.add("Date is empty");
        }
        return errors;
    }

    private void isOrderPaymentAndDeliveryCorrect(Order order, List<String> errors) {
        if (order.getCustomer().getPayment() == null) {
            errors.add("Payment is null");
        } else if (order.getCustomer().getPayment().isEmpty()) {
            errors.add("Payment is empty");
        }
        if (order.getCustomer().getDelivery() == null) {
            errors.add("Delivery is null");
        } else if (order.getCustomer().getDelivery().isEmpty()) {
            errors.add("Delivery is empty");
        }
    }

    private void isItemsOrCustomCandlesPresent(Order order, List<String> errors) {
        if ((order.getItems() == null || order.getItems().isEmpty())
                && (order.getCustomCandles() == null || order.getCustomCandles().isEmpty())) {
            errors.add("Items and candles is null or empty");
        }
    }

    private void isTotalPriceForOrderCorrect(Order order, List<String> errors) {
        BigDecimal total = BigDecimal.valueOf(0);
        if (order.getItems() != null && !order.getItems().isEmpty()) {
            for (Item item : order.getItems()) {
                total = total.add(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            }
        }
        if (order.getCustomCandles() != null && !order.getCustomCandles().isEmpty()) {
            for (CustomCandle candle : order.getCustomCandles()) {
                total = total.add(candle.getPrice().multiply(BigDecimal.valueOf(candle.getQuantity())));
            }
        }
        if (order.getTotal() == null) {
            errors.add("Total is null");
        } else if (order.getTotal().compareTo(total) != 0) {
            errors.add("Total is not equal to sum of items and custom candles");
        }
    }

    private void isItemsInfoCorrect(Order order, List<String> errors) {
        for (Item item : order.getItems()) {
            if (item.getPrice() == null) {
                errors.add("Price is null");
            } else if (item.getPrice().compareTo(BigDecimal.valueOf(0)) <= 0) {
                errors.add("Price is less than 0");
            } else if (item.getQuantity() == null) {
                errors.add("Quantity is null");
            } else if (item.getQuantity() <= 0) {
                errors.add("Quantity is less than 0");
            } else if (item.getCategory() == null) {
                errors.add("Category is null");
            } else if (item.getCategory().isEmpty()) {
                errors.add("Category is empty");
            } else if (!item.getCategory().equals("box") && !item.getCategory().equals("candle")) {
                errors.add("Category is not box or candle");
            } else if (!checkItemInDb(item)) {
                errors.add("Items not exist in db");
            }
            if (item.getCategory().equals("box")) {
                if (item.getConfiguration() == null) {
                    errors.add("Configuration is null");
                } else if (item.getConfiguration().getAroma() == null || item.getConfiguration().getAroma().isEmpty()) {
                    errors.add("Aroma is null or empty");
                }
            }
        }
    }

    private boolean checkItemInDb(Item item) {
        if (item.getCategory().equals("box")) {
            return boxRepository.existsById(item.getId());
        } else {
            return candleRepository.existsById(item.getId());
        }
    }

    private static void isCustomerHasAllInformation(Order order, List<String> errors) {
        if (order.getCustomer() == null) {
            errors.add("Customer is null");
        } else if (order.getCustomer().getAddress() == null) {
            errors.add("Address is null");
        } else if (order.getCustomer().getAddress().isEmpty()) {
            errors.add("Address is empty");
        }
    }

    private static void isCustomCandlesCorrect(Order order, List<String> errors) {
        if (order.getCustomCandles() != null && !order.getCustomCandles().isEmpty()) {
            for (CustomCandle candle : order.getCustomCandles()) {
                if (candle.getConfiguration() == null) {
                    errors.add("Configuration is null");
                    continue;
                }
                Configuration configuration = candle.getConfiguration();
                if (configuration.getContainer() == null) {
                    errors.add("Box is null");
                } else if (configuration.getContainer().isEmpty()) {
                    errors.add("Box is empty");
                }
                if (configuration.getWax() == null) {
                    errors.add("Wax is null");
                } else if (configuration.getWax().isEmpty()) {
                    errors.add("Wax is empty");
                }
                if (configuration.getAroma() == null) {
                    errors.add("Aroma name is null");
                } else if (configuration.getAroma().isEmpty()) {
                    errors.add("Aroma name is empty");
                }
                if (configuration.getWicks() == null) {
                    errors.add("Wicks is null");
                } else if (configuration.getWicks() <= 0 || configuration.getWicks() > 4) {
                    errors.add("Wicks is less than 0 or more than 4");
                }
                if (configuration.getColor() == null) {
                    errors.add("Wax color is null");
                } else if (configuration.getColor().isEmpty()) {
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
            }
        }
    }
}
