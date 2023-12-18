package com.candles.features.candle;

import com.candles.features.local.Local;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/public/candles")
@RequiredArgsConstructor
public class CandleController {

    private final CandleService candleService;
    private final CandleMapper candleMapper;
    private final PagedResourcesAssembler<CandleModel> pagedResourcesAssembler;
    private final CandleModelAssembler candleModelAssembler;

    @GetMapping
    public ResponseEntity<PagedModel<CandleModel>> getAll(@RequestParam(name = "local", defaultValue = "UA", required = false)
                                                              Local local,
                                                          @QuerydslPredicate(root = CandleEntity.class)
                                                          Predicate predicate,
                                                          @RequestParam(name = "page", defaultValue = "0", required = false)
                                                          Integer page,
                                                          @RequestParam(name = "size", defaultValue = "10", required = false)
                                                          Integer size,
                                                          @RequestParam(name = "sort", defaultValue = "UNSORTED", required = false)
                                                          String sort,
                                                          Pageable pageable) {
        Page<CandleModel> candleModels = candleService.getAllCandles(predicate,
                        pageable)
                .map(candle -> candleMapper.toModel(candle, local));
        PagedModel<CandleModel> pagedModel = pagedResourcesAssembler.toModel(candleModels, candleModelAssembler);
        return new ResponseEntity<>(pagedModel, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public CandleModel getById(@PathVariable(name = "id") String id,
                               @RequestParam(name = "local", defaultValue = "UA") Local local) {
        CandleModel candleModel = candleMapper.toModel(candleService.getCandleById(id), local);
        candleModel.add(linkTo(CandleController.class).slash(candleModel.getId()).withSelfRel());
        return candleModel;
    }
}
