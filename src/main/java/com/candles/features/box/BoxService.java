package com.candles.features.box;

import com.candles.features.box.kit.Kit;
import com.candles.features.local.Local;
import com.candles.features.photo.PhotoController;
import com.candles.model.Pair;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.dhatim.fastexcel.reader.ReadableWorkbook;
import org.dhatim.fastexcel.reader.Row;
import org.dhatim.fastexcel.reader.Sheet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
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

    public List<BoxEntity> parseFromXLSX(MultipartFile file) throws IOException {
        List<BoxEntity> boxEntities = new ArrayList<>();
        try (InputStream fileInputStream = file.getInputStream()) {
            ReadableWorkbook wb = new ReadableWorkbook(fileInputStream);
            Sheet sheet = wb.getSheet(1).get();
            for (Row row : sheet.read()) {
                if (row.getRowNum() == 1) {
                    continue;
                }
                BoxEntity box = new BoxEntity();
                box.setId(row.getCellText(0));
                box.setName(List.of(new Pair(Local.UA, row.getCellText(1)), new Pair(Local.EN, row.getCellText(2))));
                box.setTitle(List.of(new Pair(Local.UA, row.getCellText(3)), new Pair(Local.EN, row.getCellText(4))));
                box.setDescription(List.of(new Pair(Local.UA, row.getCellText(5)), new Pair(Local.EN, row.getCellText(6))));
                box.setPrice(BigDecimal.valueOf(Double.parseDouble(row.getCellText(7))));
                box.setSlug(row.getCellText(8));
                box.setVolume(row.getCellText(9));
                Kit kit = new Kit();
                kit.setContainer(List.of(new Pair(Local.UA, row.getCellText(10)), new Pair(Local.EN, row.getCellText(11))));
                kit.setWax(row.getCellText(12));
                kit.setWick(row.getCellText(13));
                kit.setAromaToChoose(List.of(new Pair(Local.UA, row.getCellText(14)), new Pair(Local.EN, row.getCellText(15))));
                kit.setMatchsticks(row.getCellText(16));
                box.setKit(kit);
                List<String> images = row.getCellText(17).isEmpty() ? null : Stream.of(row.getCellText(17).split(","))
                        .map(img -> linkTo(PhotoController.class) + "/api/public/photos/" + img.trim())
                        .toList();
                box.setImages(images);
                box.setText(List.of(new Pair(Local.UA, row.getCellText(18)), new Pair(Local.EN, row.getCellText(19))));
                boxEntities.add(box);
            }
            return boxEntities;
        }

    }
}
