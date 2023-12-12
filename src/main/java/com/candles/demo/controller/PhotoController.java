package com.candles.demo.controller;

import com.candles.demo.model.Photo;
import com.candles.demo.service.BoxServices;
import com.candles.demo.service.CandleService;
import com.candles.demo.service.PhotoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@Controller
@RequestMapping("/photos")
@RequiredArgsConstructor
public class PhotoController {
    private final PhotoService photoService;
    private final BoxServices boxServices;
    private final CandleService candleService;

    @PostMapping("/box")
    public ResponseEntity<String> addPhotoToBox(@RequestParam("title") String title,
                                                @RequestParam("image") MultipartFile image,
                                                @RequestParam("id") String id,
                                                HttpServletRequest request)
            throws IOException {
        String idImage = photoService.addPhoto(title, image);
        String url = String.format("%s://%s:%d", request.getScheme(), request.getServerName(), request.getServerPort());
        url += "/photos/" + idImage;
        boxServices.addPhoto(id, url);
        return ResponseEntity.ok("saved");
    }

    @PostMapping("/candle")
    public ResponseEntity<String> addPhotoToCandle(@RequestParam("title") String title,
                                                   @RequestParam("image") MultipartFile image,
                                                   @RequestParam("id") String id,
                                                   HttpServletRequest request)
            throws IOException {
        String idImage = photoService.addPhoto(title, image);
        String url = String.format("%s://%s:%d", request.getScheme(), request.getServerName(), request.getServerPort());
        url += "/photos/" + idImage;
        candleService.addPhoto(id, url);
        return ResponseEntity.ok("saved");
    }

    @GetMapping(path = "/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] getPhoto(@PathVariable String id) throws IOException {
        Photo photo = photoService.getPhoto(id);
        if (Objects.isNull(photo)) {
            return new byte[0];
        }
        InputStream input = new ByteArrayInputStream(photo.getImage().getData());
        return IOUtils.toByteArray(input);
    }
}
