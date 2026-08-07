package com.github.sarthi267.inventorymanagementsystem;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final CustomOAuth2UserService customOAuth2UserService;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService) throws Exception{
        this.customOAuth2UserService = customOAuth2UserService;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/auth/register", "/api/auth/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/items").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST,"/items" ).hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/items/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/items/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/inventory").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/login/**", "/oauth2/**").permitAll()
                        .anyRequest().authenticated()
                )
                .headers(headers -> headers
                        .contentTypeOptions(Customizer.withDefaults())
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                        .httpStrictTransportSecurity(hsts -> hsts
                                .includeSubDomains(true)
                                .maxAgeInSeconds(3253600))
                        .contentSecurityPolicy(csp -> csp
                                .policyDirectives("default-src 'self'; " +
                                        "script-src 'self'; " +
                                        "style-src 'self' https://cdn.jsdelivr.net; " +
                                        "img-src 'self' data:;"))
                )
                .httpBasic(Customizer.withDefaults())
                .formLogin(form -> form
                        .defaultSuccessUrl("/inventory", true)
                        .permitAll()
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)
                        )
                        .defaultSuccessUrl("/inventory", true)
                        .failureUrl("/login?error"));
                return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }
}
