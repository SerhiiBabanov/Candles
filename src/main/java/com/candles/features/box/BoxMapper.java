package com.candles.features.box;

import com.candles.features.box.kit.KitMapper;
import com.candles.features.local.Local;
import com.candles.service.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoxMapper {
    private final KitMapper kitMapper;

    public BoxModel toModel(BoxEntity boxEntity, Local local) {
        BoxModel boxModel = new BoxModel();
        boxModel.setId(boxEntity.getId());
        boxModel.setTitle(Utils.getPropertyByLocal(boxEntity.getTitle(), local));
        boxModel.setName(Utils.getPropertyByLocal(boxEntity.getName(), local));
        boxModel.setSlug(boxEntity.getSlug());
        boxModel.setVolume(boxEntity.getVolume());
        boxModel.setPrice(boxEntity.getPrice());
        boxModel.setDescription(Utils.getPropertyByLocal(boxEntity.getDescription(), local));
        boxModel.setKit(kitMapper.toModel(boxEntity.getKit(), local));
        boxModel.setImages(boxEntity.getImages());
        return boxModel;
    }
}
