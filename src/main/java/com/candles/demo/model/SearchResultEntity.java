package com.candles.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchResultEntity {
    private String id;
    private String titleEn;
    private String titleUa;
    private String nameEn;
    private String nameUa;

    public SearchResultEntity(Box box) {
        this.id = box.getId();
        this.titleEn = box.getTitleEn();
        this.titleUa = box.getTitleUa();
        this.nameEn = box.getNameEn();
        this.nameUa = box.getNameUa();
    }

    public SearchResultEntity(Candle candle) {
        this.id = candle.getId();
        this.titleEn = candle.getTitleEn();
        this.titleUa = candle.getTitleUa();
        this.nameEn = candle.getNameEn();
        this.nameUa = candle.getNameUa();
    }
}
