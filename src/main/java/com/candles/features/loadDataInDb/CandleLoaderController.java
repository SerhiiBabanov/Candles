package com.candles.features.loadDataInDb;

import com.candles.features.candle.CandleEntity;
import com.candles.features.candle.CandleService;
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
@RequiredArgsConstructor
@RequestMapping("/api/private/candle-loader")
public class CandleLoaderController {

    private final CandleService candleService;

    @PostMapping
    @Transactional
    public ResponseEntity<String> loadCandles(@RequestParam(value = "file") MultipartFile file) {
        List<CandleEntity> candleEntities;
        try {
            candleEntities = candleService.parseFromXLSX(file);
        } catch (IOException e) {
            throw new WrongDataFormatException("Wrong data format");
        }
        candleService.deleteAll();
        candleService.saveAll(candleEntities);
        return ResponseEntity.ok("Candles loaded");
    }
}
