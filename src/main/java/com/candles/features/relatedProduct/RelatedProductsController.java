package com.candles.features.relatedProduct;

import com.candles.features.box.BoxMapper;
import com.candles.features.box.BoxService;
import com.candles.features.candle.CandleMapper;
import com.candles.features.candle.CandleService;
import com.candles.features.landTranslateSupport.Local;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public/related-products")
public class RelatedProductsController {
    private final CandleService candleService;
    private final BoxService boxService;
    private final BoxMapper boxMapper;
    private final CandleMapper candleMapper;

    @GetMapping
    public ResponseEntity<CollectionModel<RelatedProduct>> getRelatedProducts(@RequestParam(name = "id") String id,
                                                                              @RequestParam(name = "lang", defaultValue = "UA") Local lang) {
        String targetId = candleService.getCandleById(id).isPresent() ? id : boxService.getBoxById(id).isPresent() ? id : null;
        if (targetId == null) {
            throw new IllegalArgumentException("Invalid id: " + id);
        }
        List<RelatedProduct> relatedProducts = Stream
                .concat(getRelatedCandles(targetId, lang), getRelatedBoxes(targetId, lang))
                .limit(4L)
                .collect(Collectors.toCollection(java.util.ArrayList::new));
        Collections.shuffle(relatedProducts);
        return ResponseEntity.ok(CollectionModel.of(relatedProducts));
    }

    private Stream<RelatedProduct> getRelatedCandles(String id, Local lang) {
        return candleService.getRelatedCandles(id).stream()
                .map(candle -> candleMapper.toModel(candle, lang))
                .map(RelatedProduct::new);
    }

    private Stream<RelatedProduct> getRelatedBoxes(String id, Local lang) {
        return boxService.getSimilarBoxes(id).stream()
                .map(box -> boxMapper.toModel(box, lang))
                .map(RelatedProduct::new);
    }
}
