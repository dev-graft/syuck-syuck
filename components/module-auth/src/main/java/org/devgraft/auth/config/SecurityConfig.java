package org.devgraft.auth.config;

import lombok.RequiredArgsConstructor;
import org.devgraft.auth.config.filter.JwtAuthorizationExceptionFilter;
import org.devgraft.auth.config.filter.JwtAuthorizationFilter;
import org.devgraft.auth.oauth.CustomOAuth2UserService;
import org.devgraft.auth.oauth.handler.OAuth2AuthenticationFailureHandler;
import org.devgraft.auth.oauth.handler.OAuth2AuthenticationSuccessHandler;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    private final JwtAuthorizationExceptionFilter jwtAuthorizationExceptionFilter;
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v*/api-docs", "/swagger-resources/**",
                "/swagger-ui.html", "/webjars/**", "/swagger/**", "/static/**", "/templates/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .anyRequest().permitAll()
                .and()
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthorizationExceptionFilter, JwtAuthorizationFilter.class)
                .oauth2Login()
                .userInfoEndpoint()
                .userService(customOAuth2UserService)
                .and()
                .successHandler(oAuth2AuthenticationSuccessHandler)
                .failureHandler(oAuth2AuthenticationFailureHandler);
    }
}