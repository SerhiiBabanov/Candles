package com.candles.features.photo;

import lombok.Data;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "photos")
@Data
public class PhotoEntity {
    @Id
    private String id;
    private String title;
    private Binary image;
}
