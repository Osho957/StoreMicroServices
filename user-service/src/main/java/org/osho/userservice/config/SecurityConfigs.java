package org.osho.userservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfigs {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests
                        (authorizeRequests -> {
                            try {
                                authorizeRequests
                                        .anyRequest().permitAll()
                                        .and().cors().disable()
                                        .csrf().disable()
                                ;
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        });

        return http.build();
    }
}
