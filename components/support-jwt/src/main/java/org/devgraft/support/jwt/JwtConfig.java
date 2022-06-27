package org.devgraft.support.jwt;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationPropertiesScan(basePackages = {"org.devgraft.*"})
@EnableConfigurationProperties
@Configuration
public class JwtConfig {
}
