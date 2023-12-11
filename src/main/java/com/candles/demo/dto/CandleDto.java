package com.candles.demo.dto;

import com.candles.demo.model.Aroma;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandleDto extends RepresentationModel<CandleDto> {
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
    private String image;
}
