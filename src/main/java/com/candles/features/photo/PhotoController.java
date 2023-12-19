package com.candles.features.photo;

import com.candles.features.box.BoxService;
import com.candles.features.candle.CandleService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Objects;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Controller
@RequiredArgsConstructor
public class PhotoController {
    private final PhotoService photoService;
    private final BoxService boxService;
    private final CandleService candleService;

    @PostMapping("/api/private/photos/box")
    public ResponseEntity<String> addPhotoToBox(@RequestParam("image") MultipartFile image,
                                                @RequestParam("id") String id)
            throws IOException {
        photoService.addPhoto(image);
        String idImage = image.getOriginalFilename();
        String url = linkTo(PhotoController.class).slash("api/public/photos/" + idImage).toString();
        boxService.addPhoto(id, url);
        return ResponseEntity.ok("saved");
    }

    @PostMapping("/api/private/photos/candle")
    public ResponseEntity<String> addPhotoToCandle(@RequestParam("image") MultipartFile image,
                                                   @RequestParam("id") String id,
                                                   HttpServletRequest request)
            throws IOException {
        photoService.addPhoto(image);
        String idImage = image.getOriginalFilename();
        String url = linkTo(PhotoController.class).slash("api/public/photos/" + idImage).toString();
        candleService.addPhoto(id, url);
        return ResponseEntity.ok("saved");
    }

    @GetMapping(path = "/api/public/photos/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] getPhoto(@PathVariable(name = "id") String id) throws IOException {
        PhotoEntity photoEntity = photoService.getPhoto(id);
        if (Objects.isNull(photoEntity)) {
            return new byte[0];
        }
        InputStream input = new ByteArrayInputStream(photoEntity.getImage().getData());
        return IOUtils.toByteArray(input);
    }

    @PostMapping("/api/private/photos")
    public ResponseEntity<String> uploadPhotos(@RequestParam("files") MultipartFile[] images) {
        try {
            Arrays.asList(images).stream().forEach(file -> {
                try {
                    photoService.addPhoto(file);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            return ResponseEntity.status(HttpStatus.OK).body("Uploaded the files successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Fail to upload files!");
        }
    }

}
