package com.candles.features.search;

import com.candles.features.box.BoxEntity;
import com.candles.features.candle.CandleEntity;
import com.candles.features.local.Local;
import com.candles.model.Pair;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class SearchResult {
    private String id;
    private String title;
    private String slug;
    private BigDecimal price;
    private List<String> images;

    public SearchResult(BoxEntity boxEntity, Local locale) {
        this.id = boxEntity.getId();
        this.title = boxEntity.getTitle().stream()
                .filter(pair -> pair.getKey().equals(locale))
                .map(Pair::getValue)
                .findFirst()
                .orElse(null);
        this.slug = boxEntity.getSlug();
        this.price = boxEntity.getPrice();
        this.images = boxEntity.getImages();
    }

    public SearchResult(CandleEntity candleEntity, Local locale) {
        this.id = candleEntity.getId();
        this.title = candleEntity.getTitle().stream()
                .filter(pair -> pair.getKey().equals(locale))
                .map(Pair::getValue)
                .findFirst()
                .orElse(null);
        this.slug = candleEntity.getSlug();
        this.price = candleEntity.getPrice();
        this.images = candleEntity.getImages();
    }
}
