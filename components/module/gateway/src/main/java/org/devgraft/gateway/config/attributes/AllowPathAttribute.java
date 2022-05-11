package org.devgraft.gateway.config.attributes;

import org.springframework.http.HttpMethod;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

public class AllowPathAttribute {
    private final PathPatternParser _parser = new PathPatternParser();
    private PathPattern path;
    private HttpMethod method;

    public AllowPathAttribute() {
    }

    public AllowPathAttribute(String path, HttpMethod method) {
        this.path = _parser.parse(path);
        this.method = method;
    }

    public void setPath(String path) {
        this.path = _parser.parse(path);
    }

    public PathPattern getPath() {
        return path;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }
}
