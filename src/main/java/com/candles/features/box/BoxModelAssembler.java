package com.candles.features.box;


import com.candles.features.landTranslateSupport.Local;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BoxModelAssembler extends RepresentationModelAssemblerSupport<BoxModel, BoxModel> {


    public BoxModelAssembler() {
        super(BoxController.class, BoxModel.class);
    }

    @Override
    public BoxModel toModel(BoxModel box) {
        return box.add(linkTo(methodOn(BoxController.class).getById(box.getId(), Local.UA)).withSelfRel());
    }

    @Override
    public CollectionModel<BoxModel> toCollectionModel(Iterable<? extends BoxModel> entities) {
        return super.toCollectionModel(entities).add(linkTo(methodOn(BoxController.class).getAll(null,
                null, null, null, null, null)).withSelfRel());
    }
}
