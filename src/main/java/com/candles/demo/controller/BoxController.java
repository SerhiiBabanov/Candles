package com.candles.demo.controller;

import com.candles.demo.model.Box;
import com.candles.demo.repository.BoxRepository;
import com.candles.demo.service.BoxServices;
import com.candles.demo.service.PhotoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.RepositoryLinksResource;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/boxes")
@RequiredArgsConstructor
public class BoxController implements RepresentationModelProcessor<RepositoryLinksResource> {
    private final BoxRepository boxRepository;
    private final PhotoService photoService;
    private final BoxServices boxServices;

    @PostMapping("/create")
    public void createCandle(HttpServletResponse response,
                             @RequestParam("model") String model,
                             @RequestParam(value = "file", required = false) MultipartFile file,
                             HttpServletRequest request) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Box box = objectMapper.readValue(model, Box.class);
        Box savedBox = boxRepository.save(box);
        if (!file.isEmpty()) {
            try {
                String photoId = photoService.addPhoto(file);
                String url = String.format("%s://%s:%d", request.getScheme(), request.getServerName(), request.getServerPort());
                url += "/photos/" + photoId;
                boxServices.addPhoto(savedBox.getId(), url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        response.sendRedirect("/boxes/" + savedBox.getId());
    }

    @Override
    public RepositoryLinksResource process(RepositoryLinksResource model) {
        model.add(linkTo(BoxController.class).slash("/create").withRel("box create via form. Use model as json and file as image"));
        return model;
    }
}
