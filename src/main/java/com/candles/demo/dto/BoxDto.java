package com.candles.demo.dto;

import com.candles.demo.model.Kit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoxDto extends RepresentationModel<BoxDto> {
    private String id;
    private String titleEn;
    private String titleUa;
    private String nameEn;
    private String nameUa;
    private String volume;
    private BigDecimal price;
    private String descriptionEn;
    private String descriptionUa;
    private Kit kitEn;
    private Kit kitUa;
    private String image;
}
