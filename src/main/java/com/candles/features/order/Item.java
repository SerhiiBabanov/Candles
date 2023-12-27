package com.candles.features.order;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Item {
    private String id;
    private String category;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal total;
}
