package com.candles.features.box;

import com.candles.features.landTranslateSupport.Local;
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

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/public/boxes")
@RequiredArgsConstructor
public class BoxController {
    private final BoxService boxService;
    private final BoxMapper boxMapper;
    private final PagedResourcesAssembler<BoxModel> pagedResourcesAssembler;
    private final BoxModelAssembler boxModelAssembler;

    @GetMapping
    public ResponseEntity<PagedModel<BoxModel>> getAll(@RequestParam(name = "lang", defaultValue = "UA", required = false) Local lang,
                                                       @QuerydslPredicate(root = BoxEntity.class)
                                                       Predicate predicate,
                                                       @RequestParam(name = "page", defaultValue = "0", required = false)
                                                       Integer page,
                                                       @RequestParam(name = "size", defaultValue = "10", required = false)
                                                       Integer size,
                                                       @RequestParam(name = "sort", defaultValue = "UNSORTED", required = false)
                                                       String sort,
                                                       Pageable pageable) {
        Page<BoxModel> boxModels = boxService.getAllBoxes(predicate,
                        pageable)
                .map(box -> boxMapper.toModel(box, lang));
        PagedModel<BoxModel> pagedModel = pagedResourcesAssembler.toModel(boxModels, boxModelAssembler);
        return new ResponseEntity<>(pagedModel, HttpStatus.OK);
    }

    @PostMapping("/by-id-in")
    public ResponseEntity<List<BoxModel>> getAllByIdIn(@RequestParam(name = "lang", defaultValue = "UA", required = false) Local lang,
                                                       @RequestBody List<String> ids) {
        List<BoxModel> boxModels = boxService.getAllBoxesByIdIn(ids)
                .stream()
                .map(box -> boxMapper.toModel(box, lang))
                .peek(boxModel -> boxModel.add(linkTo(BoxController.class).slash(boxModel.getId()).withSelfRel()))
                .toList();
        return new ResponseEntity<>(boxModels, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public BoxModel getById(@PathVariable(name = "id") String id,
                            @RequestParam(name = "lang", defaultValue = "UA") Local lang) {
        BoxModel boxModel = boxMapper.toModel(boxService.getBoxById(id).orElseThrow(() -> new IllegalArgumentException("Incorrect id: " + id)), lang);
        boxModel.add(linkTo(BoxController.class).slash(boxModel.getId()).withSelfRel());
        return boxModel;
    }

}
