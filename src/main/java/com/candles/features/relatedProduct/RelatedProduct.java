package com.candles.features.relatedProduct;

import com.candles.features.box.BoxModel;
import com.candles.features.candle.CandleModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
@Data
public class RelatedProduct {
    private String id;
    private String slug;
    private List<String> images;
    private String title;
    private BigDecimal price;
    private String name;

    RelatedProduct(CandleModel candleModel){
        this.id = candleModel.getId();
        this.slug = candleModel.getSlug();
        this.images = candleModel.getImages();
        this.title = candleModel.getTitle();
        this.price = candleModel.getPrice();
        this.name = candleModel.getName();
    }

    RelatedProduct(BoxModel boxModel){
        this.id = boxModel.getId();
        this.slug = boxModel.getSlug();
        this.images = boxModel.getImages();
        this.title = boxModel.getTitle();
        this.price = boxModel.getPrice();
        this.name = boxModel.getName();
    }
}
