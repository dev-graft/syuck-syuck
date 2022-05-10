package org.devgraft.gateway.config;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
@Primary
@AllArgsConstructor
public class SwaggerProvider implements SwaggerResourcesProvider {
    public static final String API_URI = "/v3/api-docs";
    private final RouteLocator routeLocator;
    private final GatewayProperties gatewayProperties;


    @Override
    public List get() {
        Set<String> paths = new HashSet<>();
        List<Object> resources = new ArrayList<>();

        for (RouteDefinition routeDefinition : gatewayProperties.getRoutes()) {
            for (PredicateDefinition predicateDefinition : routeDefinition.getPredicates()) {
                for(String value : predicateDefinition.getArgs().values()) {
                    if (StringUtils.hasText(value)) {
                        String docsUri = value.substring(0, value.indexOf("/", 1) != -1 ? value.indexOf("/", 1) : value.length() - 1) + API_URI;
                        if (!paths.contains(docsUri)) {
                            paths.add(docsUri);
                            resources.add(swaggerResource(routeDefinition.getId(), docsUri));
                        }
                    }
                }
            }
        }
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("3.0");
        return swaggerResource;
    }
}