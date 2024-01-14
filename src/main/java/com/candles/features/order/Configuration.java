package com.candles.features.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
class Configuration {
    private String container;
    private String wax;
    private String aroma;
    private Integer wicks;
    private String color;
}
