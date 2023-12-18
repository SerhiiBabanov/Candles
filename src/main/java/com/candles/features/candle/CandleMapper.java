package com.candles.features.candle;

import com.candles.features.candle.aroma.AromaMapper;
import com.candles.features.local.Local;
import com.candles.service.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CandleMapper {
    private final AromaMapper aromaMapper;

    public CandleModel toModel(CandleEntity candleEntity, Local local) {
        CandleModel candleModel = new CandleModel();
        candleModel.setId(candleEntity.getId());
        candleModel.setTitle(Utils.getPropertyByLocal(candleEntity.getTitle(), local));
        candleModel.setName(Utils.getPropertyByLocal(candleEntity.getName(), local));
        candleModel.setWax(candleEntity.getWax());
        candleModel.setSlug(candleEntity.getSlug());
        candleModel.setAroma(aromaMapper.toModel(candleEntity.getAroma(), local));
        candleModel.setWick(candleEntity.getWick());
        candleModel.setWicks(candleEntity.getWicks());
        candleModel.setContainer(Utils.getPropertyByLocal(candleEntity.getContainer(), local));
        candleModel.setVolume(candleEntity.getVolume());
        candleModel.setPrice(candleEntity.getPrice());
        candleModel.setDescription(Utils.getPropertyByLocal(candleEntity.getDescription(), local));
        candleModel.setContainerColor(Utils.getPropertyByLocal(candleEntity.getContainerColor(), local));
        candleModel.setWaxColor(Utils.getPropertyByLocal(candleEntity.getWaxColor(), local));
        candleModel.setImages(candleEntity.getImages());
        return candleModel;
    }
}
