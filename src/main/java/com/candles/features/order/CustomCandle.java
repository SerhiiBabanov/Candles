package com.candles.features.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomCandle {
    private Configuration configuration;
    private BigDecimal price;
    private Integer quantity;
}
