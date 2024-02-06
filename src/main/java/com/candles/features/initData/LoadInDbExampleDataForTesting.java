package com.candles.features.initData;

import com.candles.features.box.BoxEntity;
import com.candles.features.box.BoxService;
import com.candles.features.candle.CandleEntity;
import com.candles.features.candle.CandleService;
import com.candles.features.photo.PhotoService;
import com.candles.features.xlsxIntegration.XlsxService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LoadInDbExampleDataForTesting {
    private final XlsxService xlsxService;
    private final CandleService candleService;
    private final BoxService boxService;
    private final PhotoService photoService;
    @Value("${init.data.enabled}")
    private String isInitDataEnabled;
    private final String imagesRoot = "initData/images";
    private final String objectsRoot = "initData/objects/file.xlsx";

    @PostConstruct
    public void load() {
        if (isInitDataEnabled.equals("true")) {
            reloadCandlesAndBoxes();
            reloadImages();
        }
    }

    private void reloadImages() {
        // Step 1: Delete all images from the db
        photoService.deleteAll();
        // Step 2: Get all images from the imagesRoot folder
        File images = new File(imagesRoot);
        // Step 3: Add all images to the db
        for (File image : images.listFiles()) {
            try {
                photoService.addPhoto(image.getName(), wrapFileInMultipartFile(image));
            } catch (IOException e) {
                throw new RuntimeException("Cannot load images to db in test mode. Check images folder in initData folder." + e.getMessage());
            }
        }
    }

    private void reloadCandlesAndBoxes() {
        // Step 1: Get file with data from the objectsRoot folder
        File file = new File(objectsRoot);
        // Step 2: Parse candles and boxes from the file
        List<BoxEntity> boxes;
        List<CandleEntity> candles;
        try {
            MultipartFile objects = wrapFileInMultipartFile(file);
            candles = xlsxService.parseCandlesFromXLSX(objects);
            boxes = xlsxService.parseBoxesFromXLSX(objects);
        } catch (IOException e) {
            throw new RuntimeException("Cannot parse candle and box from file in test mode. Check objects folder in initData folder and xlsx file." + e.getMessage());
        }
        // Step 3: Delete all candles and boxes from the db
        candleService.deleteAll();
        boxService.deleteAll();
        // Step 4: Add all candles and boxes to the db
        candleService.saveAll(candles);
        boxService.saveAll(boxes);
    }

    private MultipartFile wrapFileInMultipartFile(File file) throws IOException {
        MultipartFile multipartFile = new MultipartFile() {
            @Override
            public String getName() {
                return file.getName();
            }

            @Override
            public String getOriginalFilename() {
                return file.getName();
            }

            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public long getSize() {
                return file.length();
            }

            @Override
            public byte[] getBytes() throws IOException {
                return new FileInputStream(file).readAllBytes();
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return new FileInputStream(file);
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {

            }
        };
        return multipartFile;
    }
}
