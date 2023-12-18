package com.candles.config;

import com.candles.features.box.BoxEntity;
import com.candles.features.candle.CandleEntity;
import com.candles.features.subscription.EmailSubscriptionEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.stereotype.Component;

@Component
public class RestConfig implements RepositoryRestConfigurer {
    @Bean
    public RepositoryRestConfigurer repositoryRestConfigurer() {
        return RepositoryRestConfigurer.withConfig(config -> {
            config.exposeIdsFor(BoxEntity.class);
            config.exposeIdsFor(CandleEntity.class);
            config.exposeIdsFor(EmailSubscriptionEntity.class);
        });
    }
}
