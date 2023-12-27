package com.candles.features.order;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;
@Document(collection = "orders")
@Data
public class Order {
    @Id
    private String id;
    private Customer customer;
    private List<Item> items;
    private List<CustomCandle> customCandles;
    private BigDecimal total;
    private Boolean payed;
    private String date;
}
