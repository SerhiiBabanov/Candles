package com.candles.demo.config;

import com.candles.demo.model.Box;
import com.candles.demo.model.Candle;
import com.candles.demo.model.EmailSubscription;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.stereotype.Component;

@Component
public class RestConfig implements RepositoryRestConfigurer {
    @Bean
    public RepositoryRestConfigurer repositoryRestConfigurer() {
        return RepositoryRestConfigurer.withConfig(config -> {
            config.exposeIdsFor(Box.class);
            config.exposeIdsFor(Candle.class);
            config.exposeIdsFor(EmailSubscription.class);
        });
    }
}
