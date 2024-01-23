package com.candles.features.search;

import com.candles.features.landTranslateSupport.Local;
import com.candles.features.landTranslateSupport.Pair;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.Document;

import java.util.List;

@Data
@AllArgsConstructor
public class SearchResult {
    private String id;
    private String title;
    private String slug;
    private String price;
    private List<String> images;

    public SearchResult(Document document, Local lang) {
        this.id = document.getObjectId("_id").toString();
        this.title = document.getList("title", Document.class).stream()
                .map(doc -> new Pair(Local.valueOf(doc.getString("key")), doc.getString("value")))
                .filter(pair -> pair.getKey().equals(lang))
                .map(Pair::getValue)
                .findFirst()
                .orElse(null);
        this.slug = document.getString("slug");
        this.price = document.getString("price");
        this.images = document.getList("images", String.class);
    }
}
