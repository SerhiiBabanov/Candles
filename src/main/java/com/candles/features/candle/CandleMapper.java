package com.candles.features.candle;

import com.candles.features.candle.aroma.AromaMapper;
import com.candles.features.landTranslateSupport.Local;
import com.candles.service.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CandleMapper {
    private final AromaMapper aromaMapper;

    public CandleModel toModel(CandleEntity candleEntity, Local lang) {
        CandleModel candleModel = new CandleModel();
        candleModel.setId(candleEntity.getId());
        candleModel.setTitle(Utils.getPropertyByLocal(candleEntity.getTitle(), lang));
        candleModel.setName(Utils.getPropertyByLocal(candleEntity.getName(), lang));
        candleModel.setWax(candleEntity.getWax());
        candleModel.setSlug(candleEntity.getSlug());
        candleModel.setAroma(aromaMapper.toModel(candleEntity.getAroma(), lang));
        candleModel.setWick(candleEntity.getWick());
        candleModel.setWicks(candleEntity.getWicks());
        candleModel.setContainer(candleEntity.getContainer());
        candleModel.setVolume(candleEntity.getVolume());
        candleModel.setPrice(candleEntity.getPrice());
        candleModel.setDescription(Utils.getPropertyByLocal(candleEntity.getDescription(), lang));
        candleModel.setContainerColor(Utils.getPropertyByLocal(candleEntity.getContainerColor(), lang));
        candleModel.setWaxColor(Utils.getPropertyByLocal(candleEntity.getWaxColor(), lang));
        candleModel.setImages(candleEntity.getImages());
        return candleModel;
    }
}
