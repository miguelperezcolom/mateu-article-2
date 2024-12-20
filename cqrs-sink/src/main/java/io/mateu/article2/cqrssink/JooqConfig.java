package io.mateu.article2.cqrssink;

import io.r2dbc.spi.ConnectionFactory;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.RenderNameStyle;
import org.jooq.impl.DSL;
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