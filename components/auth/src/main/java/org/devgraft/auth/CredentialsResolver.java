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
public class CredentialsResolver implements HandlerMethodArgumentResolver {
    private final AuthService authService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasAnnotation = parameter.hasParameterAnnotation(Credentials.class);
        boolean hasAuthResultType = MemberCredentials.class.isAssignableFrom(parameter.getParameterType());
        return hasAnnotation && hasAuthResultType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Optional<AuthResult> authResultOpt = authService.exportAuthorization((HttpServletRequest) webRequest.getNativeRequest());
        AuthResult authResult = authResultOpt.orElseThrow(AuthInfoNotFoundException::new);
        return authService.verity(authResult.getAccess(), authResult.getRefresh());
    }
}
