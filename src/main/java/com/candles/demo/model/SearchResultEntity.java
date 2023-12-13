package com.candles.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class SearchResultEntity {
    private String id;
    private String titleEn;
    private String titleUa;
    private String slug;
    private BigDecimal price;
    private List<String> images;

    public SearchResultEntity(Box box) {
        this.id = box.getId();
        this.titleEn = box.getTitleEn();
        this.titleUa = box.getTitleUa();
        this.slug = box.getSlug();
        this.price = box.getPrice();
        this.images = box.getImages();
    }

    public SearchResultEntity(Candle candle) {
        this.id = candle.getId();
        this.titleEn = candle.getTitleEn();
        this.titleUa = candle.getTitleUa();
        this.slug = candle.getSlug();
        this.price = candle.getPrice();
        this.images = candle.getImages();
    }
}
