package com.candles.features.candle;


import com.candles.features.landTranslateSupport.Local;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CandleModelAssembler extends RepresentationModelAssemblerSupport<CandleModel, CandleModel> {


    public CandleModelAssembler() {
        super(CandleController.class, CandleModel.class);
    }

    @Override
    public CandleModel toModel(CandleModel candle) {

        return candle.add(linkTo(methodOn(CandleController.class).getById(candle.getId(), Local.UA)).withSelfRel());
    }

    @Override
    public CollectionModel<CandleModel> toCollectionModel(Iterable<? extends CandleModel> entities) {
        return super.toCollectionModel(entities).add(linkTo(methodOn(CandleController.class).getAll(null,
                null, null, null, null, null)).withSelfRel());
    }
}
