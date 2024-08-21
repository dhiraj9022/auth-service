package com.auth_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.SpringSecurityCoreVersion;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class Config {

    @Bean
    public AuditorAware<String> auditorProvider(){
        return new SpringSecurityAuditorAware();
    }
}
