package com.candles.features.candle;

import com.candles.features.local.Local;
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

    public CandleEntity getCandleById(String id) {
        Optional<CandleEntity> candle = candleRepository.findById(id);
        return candle.orElse(null);
    }

    public List<CandleEntity> getSimilarCandles(String id) {
        Optional<CandleEntity> candle = candleRepository.findById(id);
        Local local = Local.EN;
        if (candle.isPresent()) {
            List<CandleEntity> similarByAroma = candleRepository.findAllByAroma(candle.get().getAroma());
            List<CandleEntity> similarByVolume = candleRepository.findAllByVolume(candle.get().getVolume());
            List<CandleEntity> similarBySlug = candleRepository.findAllBySlug(candle.get().getSlug());
            return Stream.of(similarByAroma, similarByVolume, similarBySlug).flatMap(List::stream)
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

}
