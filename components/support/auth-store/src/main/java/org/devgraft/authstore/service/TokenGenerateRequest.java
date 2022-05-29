package org.devgraft.authstore.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TokenGenerateRequest {
    @NotNull
    private List<@Pattern(regexp = "^ROLE_\\w{1,20}$", message = "틀렸어!") String> roles;

    private Serializable data;
    @NotNull
    private Long validity;
    @NotNull
    private Long refreshValidity;
}
