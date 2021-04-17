package com.karimelnaggar.debezium.outbox.starter;

import com.karimelnaggar.debezium.outbox.starter.internal.DebeziumOutboxRuntimeConfig;
import com.karimelnaggar.debezium.outbox.starter.internal.EventDispatcherImpl;
import com.karimelnaggar.debezium.outbox.starter.internal.OutboxEventPersistence;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter({ HibernateJpaAutoConfiguration.class })
@ConditionalOnBean({EntityManagerFactoryBuilder.class})
@EntityScan("com.karimelnaggar.debezium.outbox")
@EnableJpaRepositories("com.karimelnaggar.debezium.outbox")
public class DebeziumOutboxAutoConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "com.karimelnaggar.debezium.outbox")
    public DebeziumOutboxRuntimeConfig debeziumOutboxRuntimeConfig() {
        return new DebeziumOutboxRuntimeConfig();
    }

    @Bean
    public EventDispatcher eventDispatcher(OutboxEventPersistence outboxEventPersistence, DebeziumOutboxRuntimeConfig debeziumOutboxRuntimeConfig) {
        return new EventDispatcherImpl(outboxEventPersistence, debeziumOutboxRuntimeConfig);
    }
}
