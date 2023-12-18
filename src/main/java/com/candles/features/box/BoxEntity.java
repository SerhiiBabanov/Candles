package com.candles.features.box;

import com.candles.features.box.kit.Kit;
import com.candles.model.Pair;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Document
public class BoxEntity {
    @Id
    private String id;
    private List<Pair> title = new ArrayList<>();
    private List<Pair> name = new ArrayList<>();
    private String slug;
    private String volume;
    private BigDecimal price;
    private List<Pair> description = new ArrayList<>();
    private Kit kit;
    private List<String> images;
}
