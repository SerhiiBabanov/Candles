package com.candles.features.candle;

import com.candles.features.candle.aroma.Aroma;
import com.candles.model.Pair;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class CandleEntity {
    @Id
    private String id;
    private List<Pair> title = new ArrayList<>();
    private List<Pair> name = new ArrayList<>();
    private String wax;
    private String slug;
    private Aroma aroma;
    private String wick;
    private Integer wicks;
    private String container;
    private String volume;
    private BigDecimal price;
    private List<Pair> description = new ArrayList<>();
    private List<Pair> containerColor = new ArrayList<>();
    private List<Pair> waxColor = new ArrayList<>();
    private List<String> images;

}

