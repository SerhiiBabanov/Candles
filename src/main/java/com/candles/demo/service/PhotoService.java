package com.candles.demo.service;

import com.candles.demo.model.Photo;
import com.candles.demo.repository.PhotoRepository;
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
        Photo photo = new Photo();
        photo.setTitle(title);
        photo.setImage(
                new Binary(BsonBinarySubType.BINARY, file.getBytes()));
        photo = photoRepository.insert(photo); return photo.getId();
    }
    public String addPhoto(MultipartFile file) throws IOException {
        return addPhoto(file.getOriginalFilename(), file);
    }

    public Photo getPhoto(String id) {
        return photoRepository.findById(id).orElse(null);
    }
}
