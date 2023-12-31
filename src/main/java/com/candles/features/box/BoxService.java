package com.candles.features.box;

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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Service
@RequiredArgsConstructor
public class BoxService {
    private final BoxRepository boxRepository;

    public Page<BoxEntity> getAllBoxes(Predicate predicate,
                                       Pageable pageable) {
        return boxRepository.findAll(predicate,
                pageable);
    }
    public List<BoxEntity> getAllBoxesByIdIn(List<String> ids) {
        return boxRepository.findAllByIdIn(ids);
    }

    public BoxEntity getBoxById(String id) {
        Optional<BoxEntity> box = boxRepository.findById(id);
        return box.orElse(null);
    }

    public List<BoxEntity> getSimilarBoxes(String id) {
        Optional<BoxEntity> box = boxRepository.findById(id);
        if (box.isPresent()) {
            List<BoxEntity> similarByPrice = boxRepository.findAllByPrice(box.get().getPrice());
            List<BoxEntity> similarByVolume = boxRepository.findAllByVolume(box.get().getVolume());
            return Stream.of(similarByPrice, similarByVolume).flatMap(List::stream)
                    .distinct()
                    .limit(4)
                    .toList();
        }
        return boxRepository.findAll().stream().limit(4).toList();
    }

    public void addPhoto(String id, String url) {
        Optional<BoxEntity> box = boxRepository.findById(id);
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

    public void deleteAll() {
        boxRepository.deleteAll();
    }

    public void saveAll(List<BoxEntity> boxEntities) {
        boxRepository.saveAll(boxEntities);
    }

}
