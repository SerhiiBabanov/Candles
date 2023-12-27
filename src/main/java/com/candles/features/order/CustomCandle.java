package com.candles.features.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomCandle {
    private String container;
    private String wax;
    private String aroma;
    private Integer wicks;
    private String color;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal total;
}
