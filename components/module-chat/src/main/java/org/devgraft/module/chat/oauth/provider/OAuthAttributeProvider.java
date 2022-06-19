package org.devgraft.module.chat.oauth.provider;

import org.devgraft.module.chat.oauth.domain.OAuthAttributes;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class OAuthAttributeProvider {

    OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        return ofGoogle(userNameAttributeName, attributes).build();
    }


    OAuthAttributes.OAuthAttributesBuilder ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName);
    }
}
