package com.candles.demo.controller;

import com.candles.demo.model.Candle;
import com.candles.demo.model.validator.CandleValidator;
import com.candles.demo.repository.CandleRepository;
import com.candles.demo.service.CandleService;
import com.candles.demo.service.PhotoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.data.rest.webmvc.RepositoryLinksResource;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/candles")
@RequiredArgsConstructor
public class CandleController implements RepresentationModelProcessor<RepositoryLinksResource> {

    private final CandleRepository candleRepository;
    private final PhotoService photoService;
    private final CandleService candleService;
    private final CandleValidator candleValidator;

    @PostMapping("/create")
    public void createCandle(HttpServletResponse response,
                             @RequestParam("model") String model,
                             @RequestParam(value = "file", required = false) MultipartFile file,
                             HttpServletRequest request) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Candle candle = objectMapper.readValue(model, Candle.class);
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(candle, "canlde");
        candleValidator.validate(candle, result);
        if(result.hasErrors()){
            throw new RepositoryConstraintViolationException(result);
        }
        Candle savedCandle = candleRepository.save(candle);
        if (!file.isEmpty()) {
            try {
                String photoId = photoService.addPhoto(file);
                String url = String.format("%s://%s:%d", request.getScheme(), request.getServerName(), request.getServerPort());
                url += "/photos/" + photoId;
                candleService.addPhoto(savedCandle.getId(), url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        response.sendRedirect("/candles/" + savedCandle.getId());
    }

    @Override
    public RepositoryLinksResource process(RepositoryLinksResource model) {
        model.add(linkTo(CandleController.class).slash("/create").withRel("candle create via form. Use model as json and file as image"));
        return model;
    }
}
