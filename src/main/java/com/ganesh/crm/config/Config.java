package com.ganesh.crm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableMethodSecurity
public class Config {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(auth -> auth

                        // 🟢 PUBLIC
                        .requestMatchers("/api/users/register").permitAll()

                       /*
                        .requestMatchers(
                                "/api/customers",
                                "/api/customers/",
                                "/api/customers/*",
                                "/api/customers/search"
                        )
                        .hasAnyRole("ADMIN", "MANAGER", "USER")


                        .requestMatchers("/api/customers/**")
                        .hasAnyRole("ADMIN", "MANAGER")


                        .requestMatchers(
                                "/api/users",
                                "/api/users/*",
                                "/api/users/search/*"
                        )
                        .hasAnyRole("ADMIN", "MANAGER")


                        .requestMatchers("/api/**")
                        .hasRole("ADMIN")*/

                        .anyRequest().authenticated()
                )

                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
