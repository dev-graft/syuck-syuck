package org.devgraft;

import org.devgraft.auth.AuthResult;
import org.devgraft.auth.AuthService;
import org.devgraft.auth.TokenName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

@SpringBootTest
@ExtendWith(RestDocumentationExtension.class)
public class IntegrationTest {
    @Autowired
    private AuthService authService;
    protected MockMvc mockMvc;
    protected RestDocumentationResultHandler document;

    @BeforeEach
    void setUp(RestDocumentationContextProvider provider, WebApplicationContext context) {
        document = MockMvcRestDocumentation.document(
                "{class-name}/{method-name}",
                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                Preprocessors.preprocessResponse(Preprocessors.prettyPrint())
        );

        AuthResult authResult = authService.issuedMemberAuthToken("identifyToken");
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(MockMvcRestDocumentation.documentationConfiguration(provider))
                .alwaysDo(document)
                .addFilter(new TestAuthSupportFilter(authResult))
                .build();
    }

    static class TestAuthSupportFilter implements Filter {
        private final AuthResult authResult;
        public TestAuthSupportFilter(AuthResult authResult) {
            this.authResult = authResult;
        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
            chain.doFilter(new CustomAuthRequestWrapper((HttpServletRequest) request, authResult), response);
        }
    }

    static class CustomAuthRequestWrapper extends HttpServletRequestWrapper {
        private final AuthResult authResult;
        private final ArrayList<Cookie> cookies;
        public CustomAuthRequestWrapper(HttpServletRequest request, AuthResult authResult) {
            super(request);
            this.authResult = authResult;
            this.cookies = new ArrayList<>();
            if (request.getCookies() != null) Collections.addAll(this.cookies, request.getCookies());
            Cookie cookie = new Cookie(TokenName.REFRESH_TOKEN_SYNTAX, authResult.getRefresh());
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            this.addCookie(cookie);
        }

        @Override
        public String getHeader(String name) {
            if (name.equals(TokenName.ACCESS_TOKEN_SYNTAX)) {
                return TokenName.HEADER_TOKEN_PREFIX.concat(authResult.getAccess());
            }
            return super.getHeader(name);
        }

        @Override
        public Enumeration<String> getHeaders(String name) {
            if (name.equals(TokenName.ACCESS_TOKEN_SYNTAX)) {
                return Collections.enumeration(Collections.singleton(TokenName.HEADER_TOKEN_PREFIX.concat(authResult.getAccess())));
            }
            return super.getHeaders(name);
        }

        @Override
        public Enumeration<String> getHeaderNames() {
            ArrayList<String> headerNames = Collections.list(super.getHeaderNames());
            headerNames.add(TokenName.ACCESS_TOKEN_SYNTAX);
            return Collections.enumeration(headerNames);
        }

        public void addCookie(Cookie cookie) {
            this.cookies.add(cookie);
        }

        @Override
        public Cookie[] getCookies() {
            return cookies.toArray(new Cookie[0]);
        }
    }
}
