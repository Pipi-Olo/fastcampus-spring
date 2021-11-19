package com.pipiolo.calendar.core.config;

import com.pipiolo.calendar.core.util.BCryptEncryptor;
import com.pipiolo.calendar.core.util.Encryptor;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan("com.pipiolo.calendar.core")
@EnableJpaRepositories("com.pipiolo.calendar.core")
@EnableJpaAuditing
@Configuration
public class CoreConfig {

    @Bean
    public Encryptor bcryptEncryptor() {
        return new BCryptEncryptor();
    }
}