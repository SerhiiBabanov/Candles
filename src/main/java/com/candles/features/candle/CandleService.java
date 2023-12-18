package com.candles.features.candle;

import com.candles.features.candle.aroma.Aroma;
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

    public void saveAll(List<CandleEntity> candleEntities) {
        candleRepository.saveAll(candleEntities);
    }

    public List<CandleEntity> parseFromXLSX(MultipartFile file) throws IOException {
        List<CandleEntity> candleEntities = new ArrayList<>();
        try (InputStream fileInputStream = file.getInputStream()) {
            ReadableWorkbook wb = new ReadableWorkbook(fileInputStream);
            Sheet sheet = wb.getFirstSheet();
            for (Row row : sheet.read()) {
                if (row.getRowNum() == 1) {
                    continue;
                }
                CandleEntity candle = new CandleEntity();
                candle.setId(row.getCellText(0));
                candle.setName(List.of(new Pair(Local.UA, row.getCellText(1)), new Pair(Local.EN, row.getCellText(2))));
                candle.setTitle(List.of(new Pair(Local.UA, row.getCellText(3)), new Pair(Local.EN, row.getCellText(4))));
                candle.setDescription(List.of(new Pair(Local.UA, row.getCellText(5)), new Pair(Local.EN, row.getCellText(6))));
                candle.setVolume(row.getCellText(7));
                candle.setWax(row.getCellText(8));
                candle.setWaxColor(List.of(new Pair(Local.UA, row.getCellText(9)), new Pair(Local.EN, row.getCellText(10))));
                candle.setContainer(row.getCellText(11));
                candle.setContainerColor(List.of(new Pair(Local.UA, row.getCellText(12)), new Pair(Local.EN, row.getCellText(13))));
                candle.setPrice(BigDecimal.valueOf(Double.parseDouble(row.getCellText(14))));
                candle.setSlug(row.getCellText(15));
                List<String> images = row.getCellText(16).isEmpty() ? null : Stream.of(row.getCellText(16).split(","))
                        .map(img -> linkTo(PhotoController.class) + "/api/public/photos/" + img.trim())
                        .toList();

                candle.setImages(images);
                candle.setWick(row.getCellText(17));
                candle.setWicks(Integer.valueOf(row.getCellText(18)));

                Aroma aroma = new Aroma();
                aroma.setName(List.of(new Pair(Local.UA, row.getCellText(19)), new Pair(Local.EN, row.getCellText(20))));

                List<List<Pair>> topNotes = new ArrayList<>();
                String[] notes = row.getCellText(21).split(",");
                for (int i = 0; i < notes.length; i++) {
                    List<Pair> note = new ArrayList<>();
                    note.add(new Pair(Local.UA, notes[i]));
                    note.add(new Pair(Local.EN, notes[++i]));
                    topNotes.add(note);
                }
                aroma.setTopNotes(topNotes);

                List<List<Pair>> baseNotes = new ArrayList<>();
                notes = row.getCellText(22).split(",");
                for (int i = 0; i < notes.length; i++) {
                    List<Pair> note = new ArrayList<>();
                    note.add(new Pair(Local.UA, notes[i]));
                    note.add(new Pair(Local.EN, notes[++i]));
                    baseNotes.add(note);
                }
                aroma.setBaseNotes(baseNotes);
                candle.setAroma(aroma);

                candleEntities.add(candle);
            }
            return candleEntities;
        }

    }

    public void deleteAll() {
        candleRepository.deleteAll();
    }
}
