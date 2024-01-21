package com.candles.features.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Item {
    private String id;
    private String category;
    private String name;
    private BigDecimal price;
    private Integer quantity;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Configuration configuration;
}
