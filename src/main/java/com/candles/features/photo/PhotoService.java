package com.candles.features.photo;

import lombok.RequiredArgsConstructor;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PhotoService {
    private final PhotoRepository photoRepository;
    public String addPhoto(String title, MultipartFile file) throws IOException {
        PhotoEntity photoEntity = new PhotoEntity();
        photoEntity.setTitle(title);
        photoEntity.setId(file.getOriginalFilename());
        photoEntity.setImage(
                new Binary(BsonBinarySubType.BINARY, file.getBytes()));
        photoEntity = photoRepository.save(photoEntity); return photoEntity.getId();
    }
    public void addPhoto(MultipartFile file) throws IOException {
        addPhoto(file.getOriginalFilename(), file);
    }

    public PhotoEntity getPhoto(String id) {
        return photoRepository.findById(id).orElse(null);
    }

    public List<PhotoEntity> getAllPhotos() {
        return photoRepository.findAll();
    }

    public void deleteAll() {
        photoRepository.deleteAll();
    }
}
