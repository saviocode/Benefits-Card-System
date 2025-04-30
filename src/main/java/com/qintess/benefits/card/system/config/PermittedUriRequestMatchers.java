package com.qintess.benefits.card.system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
public class PermittedUriRequestMatchers {

    @Bean
    RequestMatcher permittedUris() {
        return new OrRequestMatcher(
                new AntPathRequestMatcher("/api/auth/**"),
                new AntPathRequestMatcher("/api/user/**"),
                new AntPathRequestMatcher("/api/card/**")
                );
    }
}