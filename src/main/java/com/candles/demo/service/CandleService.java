package com.candles.demo.service;

import com.candles.demo.model.Candle;
import com.candles.demo.repository.CandleRepository;
import lombok.RequiredArgsConstructor;
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

    public List<Candle> getSimilarCandles(String id) {
        Optional<Candle> candle = candleRepository.findById(id);
        if (candle.isPresent()) {
            List<Candle> similarByAroma = candleRepository.findAllByAromaEn(candle.get().getAromaEn());
            List<Candle> similarByVolume = candleRepository.findAllByVolume(candle.get().getVolume());
            List<Candle> similarBySlug = candleRepository.findAllBySlug(candle.get().getSlug());
            return Stream.of(similarByAroma, similarByVolume, similarBySlug).flatMap(List::stream)
                    .distinct()
                    .limit(4)
                    .toList();
        }
        return candleRepository.findAll().stream().limit(4).toList();
    }

    public void addPhoto(String id, String url) {
        Optional<Candle> candle = candleRepository.findById(id);
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
