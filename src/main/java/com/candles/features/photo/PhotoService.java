package com.candles.features.photo;

import lombok.RequiredArgsConstructor;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PhotoService {
    private final PhotoRepository photoRepository;
    public String addPhoto(String title, MultipartFile file) throws IOException {
        PhotoEntity photoEntity = new PhotoEntity();
        photoEntity.setTitle(title);
        photoEntity.setImage(
                new Binary(BsonBinarySubType.BINARY, file.getBytes()));
        photoEntity = photoRepository.insert(photoEntity); return photoEntity.getId();
    }
    public String addPhoto(MultipartFile file) throws IOException {
        return addPhoto(file.getOriginalFilename(), file);
    }

    public PhotoEntity getPhoto(String id) {
        return photoRepository.findById(id).orElse(null);
    }
}
