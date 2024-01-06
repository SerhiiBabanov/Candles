package com.candles.features.candle;

import com.candles.features.landTranslateSupport.Local;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CandleService {
    private final CandleRepository candleRepository;

    public Page<CandleEntity> getAllCandles(Predicate predicate,
                                            Pageable pageable) {
        return candleRepository.findAll(predicate,
                pageable);
    }

    public Optional<CandleEntity> getCandleById(String id) {
        return candleRepository.findById(id);
    }

    public List<CandleEntity> getAllCandlesByIdIn(List<String> ids) {
        return candleRepository.findAllByIdIn(ids);
    }

    public List<CandleEntity> getRelatedCandles(String id) {
        Optional<CandleEntity> candle = candleRepository.findById(id);
        Local local = Local.EN;
        if (candle.isPresent()) {
            Stream<CandleEntity> relatedByAroma = candleRepository.findAllByAroma(candle.get().getAroma()).stream().limit(5);
            Stream<CandleEntity> any4 = candleRepository.findAll().stream().limit(5);
            return Stream.concat(relatedByAroma, any4)
                    .filter(candleEntity -> !candleEntity.getId().equals(id))
                    .distinct()
                    .limit(4)
                    .toList();
        }
        return candleRepository.findAll().stream().limit(4).toList();
    }

    public void addPhoto(String id, String url) {
        Optional<CandleEntity> candle = candleRepository.findById(id);
        if (candle.isPresent()) {
            List<String> images = candle.get().getImages();
            if (Objects.isNull(images)) {
                images = new ArrayList<>();
            }
            images.add(url);
            candle.get().setImages(images);
            candleRepository.save(candle.get());
        }
    }

    public void saveAll(List<CandleEntity> candleEntities) {
        candleRepository.saveAll(candleEntities);
    }

    public void deleteAll() {
        candleRepository.deleteAll();
    }
}