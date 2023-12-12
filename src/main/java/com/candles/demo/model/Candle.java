package com.candles.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Candle {
    @Id
    private String id;
    private String titleEn;
    private String titleUa;
    private String nameEn;
    private String nameUa;
    private String wax;
    private String slug;
    private Aroma aromaEn;
    private Aroma aromaUa;
    private String wick;
    private Integer wicks;
    private String container;
    private String volume;
    private BigDecimal price;
    private String descriptionEn;
    private String descriptionUa;
    private String containerColorEn;
    private String containerColorUa;
    private String waxColorEn;
    private String waxColorUa;
    private List<String> images;
}

