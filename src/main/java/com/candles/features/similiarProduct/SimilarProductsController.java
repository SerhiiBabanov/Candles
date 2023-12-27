package com.candles.features.similiarProduct;

import com.candles.features.box.BoxController;
import com.candles.features.box.BoxMapper;
import com.candles.features.box.BoxModel;
import com.candles.features.box.BoxService;
import com.candles.features.candle.*;
import com.candles.features.local.Local;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public/similar-products")
public class SimilarProductsController {
    private final CandleService candleService;
    private final BoxService boxService;
    private final BoxMapper boxMapper;
    private final CandleMapper candleMapper;

    @GetMapping("/candles")
    public ResponseEntity<CollectionModel<CandleModel>> getCandles(@RequestParam(name = "id") String id,
                                                                   @RequestParam(name = "lang", defaultValue = "UA") Local lang) {
        List<CandleModel> candles = candleService.getSimilarCandles(id).stream()
                .map(candle -> candleMapper.toModel(candle, lang))
                .peek(candle -> candle.add(linkTo(CandleController.class).slash(candle.getId()).withSelfRel()))
                .toList();
        return ResponseEntity.ok(CollectionModel.of(candles));
    }

    @GetMapping("/boxes")
    public ResponseEntity<CollectionModel<BoxModel>> getBoxes(@RequestParam(name = "id") String id,
                                                              @RequestParam(name = "lang", defaultValue = "UA") Local lang) {
        List<BoxModel> boxes = boxService.getSimilarBoxes(id).stream()
                .map(box -> boxMapper.toModel(box, lang))
                .peek(box -> box.add(linkTo(BoxController.class).slash(box.getId()).withSelfRel()))
                .toList();
        return ResponseEntity.ok(CollectionModel.of(boxes));
    }

    @GetMapping
    public ResponseEntity<CollectionModel<RelatedProduct>> getSimilarProducts(@RequestParam(name = "id") String id,
                                                                                    @RequestParam(name = "lang", defaultValue = "UA") Local lang) {
        //check if id is valid, if not throw exception
        //if id is valid, get similar products
        //logic for getting similar products not implemented yet, return 4 random products

        List<RelatedProduct> similarProducts = new java.util.ArrayList<>(candleService.getSimilarCandles(id).stream()
                .map(candle -> candleMapper.toModel(candle, lang))
                .map(RelatedProduct::new)
                .toList());
        similarProducts.addAll(boxService.getSimilarBoxes(id).stream()
                .map(box -> boxMapper.toModel(box, lang))
                .map(RelatedProduct::new)
                .toList());
        return ResponseEntity.ok(CollectionModel.of(similarProducts));
    }
}
