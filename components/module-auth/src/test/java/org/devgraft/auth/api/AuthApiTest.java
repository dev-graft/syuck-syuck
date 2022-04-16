package org.devgraft.auth.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

class AuthApiTest {
    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToController(new AuthApi())
                .configureClient()
                .build();
    }

    @Test
    void issueToken_createHttpStatus() {
        webTestClient.post()
                .uri("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue("{\"roles\": [\"ROLE_TEST\"], \"data\": {\"id\":\"id\"}, \"validity\": 100000, \"refreshValidity\": 1000000}"))
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    void issueToken_returnValue() {
        webTestClient.post()
                .uri("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue("{\"roles\": [\"ROLE_TEST\"], \"data\": {\"id\":\"id\"}, \"validity\": 100000, \"refreshValidity\": 1000000}"))
                .exchange()
                .expectBody()
                .jsonPath("$.accessToken").isEqualTo("accessToken")
                .jsonPath("$.refreshToken").isEqualTo("refreshToken");
    }

    @Test
    void breakToken_okHttpStatus() {
        webTestClient.delete()
                .uri("/auth/{token}", "token")
                .exchange()
                .expectStatus()
                .isOk();
    }
}