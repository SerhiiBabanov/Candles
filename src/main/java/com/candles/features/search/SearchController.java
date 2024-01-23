package com.candles.features.search;

import com.candles.features.landTranslateSupport.Local;
import com.candles.features.landTranslateSupport.LocaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/public/search")
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;

    @GetMapping
    public List<SearchResult> search(@RequestParam(name = "q") String keywords,
                                     @RequestParam(name = "lang", required = false) Local lang) {
        if (lang == null) {
            lang = LocaleService.isEnSymbolOnly(keywords) ? Local.EN : Local.UA;
        }
        return searchService.fullTextSearch(keywords, lang);
    }
}
