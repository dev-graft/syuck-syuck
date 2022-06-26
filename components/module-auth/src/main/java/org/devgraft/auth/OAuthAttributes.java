package org.devgraft.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
@Getter
public class OAuthAttributes {
    private final String registrationId;
    private final String nameAttributeKey;
    private final String name;
    private final String email;
    private final String picture;
    private final Map<String, Object> attributes;

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        return new OAuthAttributes(registrationId, userNameAttributeName,
                (String)attributes.get("name"),
                (String)attributes.get("email"),
                (String)attributes.get("picture"),
                attributes);
    }
}
