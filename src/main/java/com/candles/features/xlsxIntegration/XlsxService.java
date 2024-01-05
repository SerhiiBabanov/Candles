package com.candles.features.xlsxIntegration;

import com.candles.features.box.BoxEntity;
import com.candles.features.box.BoxRepository;
import com.candles.features.box.kit.Kit;
import com.candles.features.candle.CandleEntity;
import com.candles.features.candle.CandleRepository;
import com.candles.features.candle.aroma.Aroma;
import com.candles.features.landTranslateSupport.Local;
import com.candles.features.photo.PhotoController;
import com.candles.features.landTranslateSupport.Pair;
import lombok.RequiredArgsConstructor;
import org.dhatim.fastexcel.Workbook;
import org.dhatim.fastexcel.Worksheet;
import org.dhatim.fastexcel.reader.ReadableWorkbook;
import org.dhatim.fastexcel.reader.Row;
import org.dhatim.fastexcel.reader.Sheet;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Service
@RequiredArgsConstructor
public class XlsxService {
    private static final Integer BOX_SHEET_NUMBER = 1;
    private static final Integer CANDLE_SHEET_NUMBER = 0;
    private final CandleRepository candleRepository;
    private final BoxRepository boxRepository;

    public List<BoxEntity> parseBoxesFromXLSX(MultipartFile file) throws IOException {
        List<BoxEntity> boxEntities = new ArrayList<>();
        try (InputStream fileInputStream = file.getInputStream()) {
            ReadableWorkbook wb = new ReadableWorkbook(fileInputStream);
            Sheet sheet = wb.getSheet(BOX_SHEET_NUMBER).get();
            for (Row row : sheet.read()) {
                if (row.getRowNum() == 1) {
                    continue;
                }
                BoxEntity box = getBoxEntity(row);
                boxEntities.add(box);
            }
            return boxEntities;
        }
    }

    public List<CandleEntity> parseCandlesFromXLSX(MultipartFile file) throws IOException {
        List<CandleEntity> candleEntities = new ArrayList<>();
        try (InputStream fileInputStream = file.getInputStream()) {
            ReadableWorkbook wb = new ReadableWorkbook(fileInputStream);
            Sheet sheet = wb.getSheet(CANDLE_SHEET_NUMBER).get();
            for (Row row : sheet.read()) {
                if (row.getRowNum() == 1) {
                    continue;
                }
                CandleEntity candle = getCandleEntity(row);
                candleEntities.add(candle);
            }
            return candleEntities;
        }

    }

    private static CandleEntity getCandleEntity(Row row) {
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

        Aroma aroma = getAroma(row);
        candle.setAroma(aroma);
        return candle;
    }

    private static Aroma getAroma(Row row) {
        Aroma aroma = new Aroma();
        aroma.setName(List.of(new Pair(Local.UA, row.getCellText(19)), new Pair(Local.EN, row.getCellText(20))));
        List<Pair> notesAll = new ArrayList<>();
        List<List<Pair>> topNotes = new ArrayList<>();
        String[] notes = row.getCellText(21).split(",");
        for (int i = 0; i < notes.length; i++) {
            List<Pair> note = new ArrayList<>();
            String noteUA = notes[i].substring(0, notes[i].indexOf("/"));
            String noteEN = notes[i].substring(notes[i].indexOf("/") + 1);
            note.add(new Pair(Local.UA, noteUA));
            note.add(new Pair(Local.EN, noteEN));
            notesAll.addAll(note);
            topNotes.add(note);
        }
        aroma.setTopNotes(topNotes);

        List<List<Pair>> baseNotes = new ArrayList<>();
        notes = row.getCellText(22).split(",");
        for (int i = 0; i < notes.length; i++) {
            List<Pair> note = new ArrayList<>();
            String noteUA = notes[i].substring(0, notes[i].indexOf("/"));
            String noteEN = notes[i].substring(notes[i].indexOf("/") + 1);
            note.add(new Pair(Local.UA, noteUA));
            note.add(new Pair(Local.EN, noteEN));
            notesAll.addAll(note);
            baseNotes.add(note);
        }
        aroma.setBaseNotes(baseNotes);
        aroma.setNotes(notesAll);
        return aroma;
    }

    private static BoxEntity getBoxEntity(Row row) {
        BoxEntity box = new BoxEntity();
        box.setId(row.getCellText(0));
        box.setName(List.of(new Pair(Local.UA, row.getCellText(1)), new Pair(Local.EN, row.getCellText(2))));
        box.setTitle(List.of(new Pair(Local.UA, row.getCellText(3)), new Pair(Local.EN, row.getCellText(4))));
        box.setDescription(List.of(new Pair(Local.UA, row.getCellText(5)), new Pair(Local.EN, row.getCellText(6))));
        box.setPrice(BigDecimal.valueOf(Double.parseDouble(row.getCellText(7))));
        box.setSlug(row.getCellText(8));
        box.setVolume(row.getCellText(9));
        Kit kit = getKit(row);
        box.setKit(kit);
        List<String> images = row.getCellText(20).isEmpty() ? null : Stream.of(row.getCellText(20).split(","))
                .map(img -> linkTo(PhotoController.class) + "/api/public/photos/" + img.trim())
                .toList();
        box.setImages(images);
        box.setText(List.of(new Pair(Local.UA, row.getCellText(21)), new Pair(Local.EN, row.getCellText(22))));
        return box;
    }

    private static Kit getKit(Row row) {
        Kit kit = new Kit();
        kit.setContainer(List.of(new Pair(Local.UA, row.getCellText(10)), new Pair(Local.EN, row.getCellText(11))));
        kit.setWax(List.of(new Pair(Local.UA, row.getCellText(12)), new Pair(Local.EN, row.getCellText(13))));
        kit.setWick(List.of(new Pair(Local.UA, row.getCellText(14)), new Pair(Local.EN, row.getCellText(15))));
        kit.setAromaToChoose(List.of(new Pair(Local.UA, row.getCellText(16)), new Pair(Local.EN, row.getCellText(17))));
        kit.setMatchsticks(List.of(new Pair(Local.UA, row.getCellText(18)), new Pair(Local.EN, row.getCellText(19))));
        return kit;
    }

    public byte[] generateXLSX() {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            Workbook workbook = new Workbook(os, "data.xlsx", "31.2023"); // creates a new XLSX workbook
            // Create a sheet for candles
            Worksheet cs = workbook.newWorksheet("Candles");
            writeCandleNamesIntoCandleSheet(cs);
            List<CandleEntity> candles = candleRepository.findAll();
            writeCandlesDataIntoCandleSheet(cs, candles);

            // Create a sheet for boxes
            Worksheet bs = workbook.newWorksheet("Boxes");
            writeBoxNamesIntoBoxSheet(bs);
            List<BoxEntity> boxes = boxRepository.findAll();
            writeBoxesDataIntoBoxSheet(bs, boxes);
            workbook.finish();
            return os.toByteArray();
        } catch (IOException e) {
            throw new XlsxServiceException("Error while generating xlsx file");
        }

    }

    private void writeBoxNamesIntoBoxSheet(Worksheet bs) {
        bs.value(0, 0, "Id");
        bs.value(0, 1, "Name UA");
        bs.value(0, 2, "Name EN");
        bs.value(0, 3, "Title UA");
        bs.value(0, 4, "Title EN");
        bs.value(0, 5, "Description UA");
        bs.value(0, 6, "Description EN");
        bs.value(0, 7, "Price");
        bs.value(0, 8, "Slug");
        bs.value(0, 9, "Volume");
        bs.value(0, 10, "Kit name UA");
        bs.value(0, 11, "Kit name EN");
        bs.value(0, 12, "Wax name UA");
        bs.value(0, 13, "Wax name EN");
        bs.value(0, 14, "Wick name UA");
        bs.value(0, 15, "Wick name EN");
        bs.value(0, 16, "Aroma name UA");
        bs.value(0, 17, "Aroma name EN");
        bs.value(0, 18, "Matchsticks name UA");
        bs.value(0, 19, "Matchsticks name EN");
        bs.value(0, 20, "Images");
        bs.value(0, 21, "Text UA");
        bs.value(0, 22, "Text EN");
    }

    private void writeCandleNamesIntoCandleSheet(Worksheet cs) {
        cs.value(0, 0, "Id");
        cs.value(0, 1, "Name UA");
        cs.value(0, 2, "Name EN");
        cs.value(0, 3, "Title UA");
        cs.value(0, 4, "Title EN");
        cs.value(0, 5, "Description UA");
        cs.value(0, 6, "Description EN");
        cs.value(0, 7, "Volume");
        cs.value(0, 8, "Wax");
        cs.value(0, 9, "Wax color UA");
        cs.value(0, 10, "Wax color EN");
        cs.value(0, 11, "Container");
        cs.value(0, 12, "Container color UA");
        cs.value(0, 13, "Container color EN");
        cs.value(0, 14, "Price");
        cs.value(0, 15, "Slug");
        cs.value(0, 16, "Images");
        cs.value(0, 17, "Wick");
        cs.value(0, 18, "Wicks");
        cs.value(0, 19, "Aroma name UA");
        cs.value(0, 20, "Aroma name EN");
        cs.value(0, 21, "Aroma top notes");
        cs.value(0, 22, "Aroma base notes");
    }

    private void writeBoxesDataIntoBoxSheet(Worksheet bs, List<BoxEntity> boxes) {
        for (int i = 0; i < boxes.size(); i++) {
            int j = i + 1; // because of the first row with names
            bs.value(j, 0, boxes.get(i).getId());
            bs.value(j, 1, boxes.get(i).getName().get(0).getValue());
            bs.value(j, 2, boxes.get(i).getName().get(1).getValue());
            bs.value(j, 3, boxes.get(i).getTitle().get(0).getValue());
            bs.value(j, 4, boxes.get(i).getTitle().get(1).getValue());
            bs.value(j, 5, boxes.get(i).getDescription().get(0).getValue());
            bs.value(j, 6, boxes.get(i).getDescription().get(1).getValue());
            bs.value(j, 7, boxes.get(i).getPrice().toString());
            bs.value(j, 8, boxes.get(i).getSlug());
            bs.value(j, 9, boxes.get(i).getVolume());
            bs.value(j, 10, boxes.get(i).getKit().getContainer().get(0).getValue());
            bs.value(j, 11, boxes.get(i).getKit().getContainer().get(1).getValue());
            bs.value(j, 12, boxes.get(i).getKit().getWax().get(0).getValue());
            bs.value(j, 13, boxes.get(i).getKit().getWax().get(1).getValue());
            bs.value(j, 14, boxes.get(i).getKit().getWick().get(0).getValue());
            bs.value(j, 15, boxes.get(i).getKit().getWick().get(1).getValue());
            bs.value(j, 16, boxes.get(i).getKit().getAromaToChoose().get(0).getValue());
            bs.value(j, 17, boxes.get(i).getKit().getAromaToChoose().get(1).getValue());
            if (boxes.get(i).getKit().getMatchsticks() == null) {
                bs.value(j, 18, "");
                bs.value(j, 19, "");
            } else {
                bs.value(j, 18, boxes.get(i).getKit().getMatchsticks().get(0).getValue());
                bs.value(j, 19, boxes.get(i).getKit().getMatchsticks().get(1).getValue());
            }
            bs.value(j, 20, getStringWithPhotoNames(boxes.get(i).getImages()));
            bs.value(j, 21, boxes.get(i).getText().get(0).getValue());
            bs.value(j, 22, boxes.get(i).getText().get(1).getValue());

        }
    }

    private String getStringWithPhotoNames(List<String> images) {
        if (images == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (String image : images) {
            sb.append(image.substring(image.lastIndexOf("/") + 1)).append(",");
        }
        sb.deleteCharAt(sb.length() - 1); // delete last comma
        return sb.toString();
    }

    private void writeCandlesDataIntoCandleSheet(Worksheet cs, List<CandleEntity> candles) {
        for (int i = 0; i < candles.size(); i++) {
            int j = i + 1; // because of the first row with names
            cs.value(j, 0, candles.get(i).getId());
            cs.value(j, 1, candles.get(i).getName().get(0).getValue());
            cs.value(j, 2, candles.get(i).getName().get(1).getValue());
            cs.value(j, 3, candles.get(i).getTitle().get(0).getValue());
            cs.value(j, 4, candles.get(i).getTitle().get(1).getValue());
            cs.value(j, 5, candles.get(i).getDescription().get(0).getValue());
            cs.value(j, 6, candles.get(i).getDescription().get(1).getValue());
            cs.value(j, 7, candles.get(i).getVolume());
            cs.value(j, 8, candles.get(i).getWax());
            cs.value(j, 9, candles.get(i).getWaxColor().get(0).getValue());
            cs.value(j, 10, candles.get(i).getWaxColor().get(1).getValue());
            cs.value(j, 11, candles.get(i).getContainer());
            cs.value(j, 12, candles.get(i).getContainerColor().get(0).getValue());
            cs.value(j, 13, candles.get(i).getContainerColor().get(1).getValue());
            cs.value(j, 14, candles.get(i).getPrice().toString());
            cs.value(j, 15, candles.get(i).getSlug());
            cs.value(j, 16, getStringWithPhotoNames(candles.get(i).getImages()));
            cs.value(j, 17, candles.get(i).getWick());
            cs.value(j, 18, candles.get(i).getWicks().toString());
            cs.value(j, 19, candles.get(i).getAroma().getName().get(0).getValue());
            cs.value(j, 20, candles.get(i).getAroma().getName().get(1).getValue());
            cs.value(j, 21, getNotesAsString(candles.get(i).getAroma().getTopNotes()));
            cs.value(j, 22, getNotesAsString(candles.get(i).getAroma().getBaseNotes()));
        }
    }

    private String getNotesAsString(List<List<Pair>> topNotes) {
        StringBuilder sb = new StringBuilder();
        for (List<Pair> note : topNotes) {
            sb.append(note.get(0).getValue()).append("/").append(note.get(1).getValue()).append(",");
        }
        sb.deleteCharAt(sb.length() - 1); // delete last comma
        return sb.toString();
    }
}
