package com.candles.features.similiarProduct;

import com.candles.features.box.BoxController;
import com.candles.features.box.BoxMapper;
import com.candles.features.box.BoxModel;
import com.candles.features.box.BoxService;
import com.candles.features.candle.CandleController;
import com.candles.features.candle.CandleMapper;
import com.candles.features.candle.CandleModel;
import com.candles.features.candle.CandleService;
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
                                                                   @RequestParam(name = "local", defaultValue = "UA") Local local) {
        List<CandleModel> candles = candleService.getSimilarCandles(id).stream()
                .map(candle -> candleMapper.toModel(candle, local))
                .peek(candle -> candle.add(linkTo(CandleController.class).slash(candle.getId()).withSelfRel()))
                .toList();
        return ResponseEntity.ok(CollectionModel.of(candles));
    }

    @GetMapping("/boxes")
    public ResponseEntity<CollectionModel<BoxModel>> getBoxes(@RequestParam(name = "id") String id,
                                                              @RequestParam(name = "local", defaultValue = "UA") Local local) {
        List<BoxModel> boxes = boxService.getSimilarBoxes(id).stream()
                .map(box -> boxMapper.toModel(box, local))
                .peek(box -> box.add(linkTo(BoxController.class).slash(box.getId()).withSelfRel()))
                .toList();
        return ResponseEntity.ok(CollectionModel.of(boxes));
    }
}
