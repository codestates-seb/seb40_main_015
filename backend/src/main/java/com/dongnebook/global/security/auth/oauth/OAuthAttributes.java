package com.dongnebook.global.security.auth.oauth;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

public enum OAuthAttributes {
    GOOGLE("google", attributes -> new OAuthUserProfile(
            String.valueOf(attributes.get("sub")),
            (String) attributes.get("name"),
            (String) attributes.get("email")
    ));
    private final String registrationId;
    private final Function<Map<String, Object>, OAuthUserProfile> of;

    OAuthAttributes(String registrationId, Function<Map<String, Object>, OAuthUserProfile> of) {
        this.registrationId = registrationId;
        this.of = of;
    }

    public static OAuthUserProfile extract(String registrationId, Map<String, Object> attributes) {
        return Arrays.stream(values())
                .filter(provider -> registrationId.equals(provider.registrationId))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                .of.apply(attributes);
    }
}
