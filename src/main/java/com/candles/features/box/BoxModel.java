package com.candles.features.box;

import com.candles.features.box.kit.KitModel;
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
@Relation(collectionRelation = "boxes")
public class BoxModel extends RepresentationModel<BoxModel> {
    private String id;
    private String title;
    private String name;
    private String slug;
    private String volume;
    private BigDecimal price;
    private String description;
    private String text;
    private KitModel kit;
    private List<String> images;
}
