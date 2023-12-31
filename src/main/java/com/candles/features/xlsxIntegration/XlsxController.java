package com.candles.features.xlsxIntegration;

import com.candles.features.box.BoxEntity;
import com.candles.features.box.BoxService;
import com.candles.features.candle.CandleEntity;
import com.candles.features.candle.CandleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/private/xlsx")
public class XlsxController {
    private final XlsxService xlsxService;
    private final CandleService candleService;
    private final BoxService boxService;

    @PostMapping("/load")
    @Transactional
    public ResponseEntity<String> load(@RequestParam(value = "file") MultipartFile file) {
        List<BoxEntity> boxes;
        List<CandleEntity> candles;
        try {
            candles = xlsxService.parseCandlesFromXLSX(file);
            boxes = xlsxService.parseBoxesFromXLSX(file);
        } catch (IOException e) {
            throw new XlsxServiceException("Wrong file format");
        }
        candleService.deleteAll();
        boxService.deleteAll();
        candleService.saveAll(candles);
        boxService.saveAll(boxes);
        return ResponseEntity.ok("Loaded");
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadXlsx() {
        byte[] bytes = xlsxService.generateXLSX();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDispositionFormData("attachment", "file.xlsx");
        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }

}
