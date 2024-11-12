package io.mateu.article2.financial;

import org.jooq.conf.RenderNameStyle;
import org.springframework.boot.autoconfigure.jooq.DefaultConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
class JooqConfig {
    @Bean
    @Primary
    public DefaultConfigurationCustomizer myJooqConfiguration() {
        return configuration -> configuration.settings().withRenderNameStyle(RenderNameStyle.LOWER);
    }
}