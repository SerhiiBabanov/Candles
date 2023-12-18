package com.candles.features.box;

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
@RequestMapping("/api/public/boxes")
@RequiredArgsConstructor
public class BoxController {
    private final BoxService boxService;
    private final BoxMapper boxMapper;
    private final PagedResourcesAssembler<BoxModel> pagedResourcesAssembler;
    private final BoxModelAssembler boxModelAssembler;

    @GetMapping
    public ResponseEntity<PagedModel<BoxModel>> getAll(@RequestParam(name = "local", defaultValue = "UA", required = false)
                                                           Local local,
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
                .map(box -> boxMapper.toModel(box, local));
        PagedModel<BoxModel> pagedModel = pagedResourcesAssembler.toModel(boxModels, boxModelAssembler);
        return new ResponseEntity<>(pagedModel, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public BoxModel getById(@PathVariable(name = "id") String id,
                            @RequestParam(name = "local", defaultValue = "UA") Local local) {
        BoxModel boxModel = boxMapper.toModel(boxService.getBoxById(id), local);
        boxModel.add(linkTo(BoxController.class).slash(boxModel.getId()).withSelfRel());
        return boxModel;
    }

}
