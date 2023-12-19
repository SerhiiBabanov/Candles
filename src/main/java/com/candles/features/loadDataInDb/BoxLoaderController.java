package com.candles.features.loadDataInDb;

import com.candles.features.box.BoxEntity;
import com.candles.features.box.BoxService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/api/private/box-loader")
@RequiredArgsConstructor
public class BoxLoaderController {

    private final BoxService boxService;

    @PostMapping
    @Transactional
    public ResponseEntity<String> loadBoxes(@RequestParam(value = "file") MultipartFile file) {
        List<BoxEntity> boxEntities;
        try {
            boxEntities = boxService.parseFromXLSX(file);
        } catch (IOException e) {
            throw new WrongDataFormatException("Wrong data format");
        }
        boxService.deleteAll();
        boxService.saveAll(boxEntities);
        return ResponseEntity.ok("Boxes loaded");
    }
}
