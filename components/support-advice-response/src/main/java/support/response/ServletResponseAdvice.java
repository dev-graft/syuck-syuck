package support.response;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.PathContainer;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;
import support.CommonResult;
import support.SingleResult;

import java.util.Arrays;
import java.util.List;

@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@RestControllerAdvice
public class ServletResponseAdvice implements ResponseBodyAdvice<Object> {
    private final List<PathPattern> whitelist = Arrays.asList(
            new PathPatternParser().parse("/v*/api-docs"),
            new PathPatternParser().parse("/swagger-resources/**"),
            new PathPatternParser().parse("/swagger-ui.html"),
            new PathPatternParser().parse("/webjars/**"),
            new PathPatternParser().parse("/swagger/**"));
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (whitelist.stream().anyMatch(pathPattern -> pathPattern.matches(PathContainer.parsePath(request.getURI().getPath())))) return body;
        if (body instanceof CommonResult) {
            CommonResult commonResult = (CommonResult) body;
            try {
                HttpStatus status = HttpStatus.valueOf(commonResult.getStatus());
                response.setStatusCode(status);
            } catch (IllegalArgumentException e) {
                response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            }

            return commonResult;
        }
        int status = response instanceof ServletServerHttpResponse ? ((ServletServerHttpResponse) response).getServletResponse().getStatus() : 200;
        return body != null ? SingleResult.success(body, status) : CommonResult.success(status);
    }
}
