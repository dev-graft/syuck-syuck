package org.devgraft.auth;

import lombok.RequiredArgsConstructor;
import org.devgraft.auth.exception.AuthInfoNotFoundException;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class AuthInfoResolver implements HandlerMethodArgumentResolver {
    private final AuthService authService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasAnnotation = parameter.hasParameterAnnotation(AuthInfo.class);
        boolean hasAuthResultType = AuthResult.class.isAssignableFrom(parameter.getParameterType());
        return hasAnnotation && hasAuthResultType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Optional<AuthResult> authResultOpt = authService.exportAuthorization((HttpServletRequest) webRequest.getNativeRequest());
        return authResultOpt.orElseThrow(AuthInfoNotFoundException::new);
    }
}
