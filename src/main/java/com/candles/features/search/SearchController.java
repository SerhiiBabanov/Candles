package com.candles.features.search;

import com.candles.features.box.BoxRepository;
import com.candles.features.candle.CandleRepository;
import com.candles.features.local.Local;
import com.candles.features.local.LocaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/public/search")
@RequiredArgsConstructor
public class SearchController{
    private final BoxRepository boxRepository;
    private final CandleRepository candleRepository;

    @GetMapping
    public List<SearchResult> search(@RequestParam(name = "q") String pattern,
                                     @RequestParam(name = "lang", required = false) Local lang) {
        if (lang == null) {
            lang = LocaleService.isEnSymbolOnly(pattern) ?
                    Local.EN : Local.UA;
        }
        Local finalLang = lang;
        Stream<SearchResult> candles = candleRepository.searchByPattern(pattern).stream()
                .map(candle -> new SearchResult(candle, finalLang));

        Stream<SearchResult> boxes = boxRepository.searchByPattern(pattern).stream()
                .map(box -> new SearchResult(box, finalLang));
        return Stream.concat(boxes, candles).collect(Collectors.toList());
    }
}
