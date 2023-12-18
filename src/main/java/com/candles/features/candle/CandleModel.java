package com.candles.features.candle;

import com.candles.features.candle.aroma.AromaModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Relation(collectionRelation = "candles")
public class CandleModel extends RepresentationModel<CandleModel> {
    private String id;
    private String title;
    private String name;
    private String wax;
    private String slug;
    private AromaModel aroma;
    private String wick;
    private Integer wicks;
    private String container;
    private String volume;
    private BigDecimal price;
    private String description;
    private String containerColor;
    private String waxColor;
    private List<String> images;
}
