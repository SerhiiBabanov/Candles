package com.candles.demo.service;

import com.candles.demo.model.Box;
import com.candles.demo.repository.BoxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class BoxServices {
    private final BoxRepository boxRepository;

    public List<Box> getSimilarBoxes(String id) {
        Optional<Box> box = boxRepository.findById(id);
        if (box.isPresent()) {
            List<Box> similarByPrice = boxRepository.findAllByPrice(box.get().getPrice());
            List<Box> similarByVolume = boxRepository.findAllByVolume(box.get().getVolume());
            return Stream.of(similarByPrice, similarByVolume).flatMap(List::stream)
                    .distinct()
                    .limit(4)
                    .toList();
        }
        return boxRepository.findAll().stream().limit(4).toList();
    }

    public void addPhoto(String id, String url) {
        Optional<Box> box = boxRepository.findById(id);
        if (box.isPresent()) {
            List<String> images = box.get().getImages();
            if (Objects.isNull(images)) {
                images = new ArrayList<>();
            }
            images.add(url);
            box.get().setImages(images);
            boxRepository.save(box.get());
        }
    }
}
