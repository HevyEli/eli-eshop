package org.eli.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SecurityConfigProperties {

    @Value("${application.security.disabled:false}")
    private boolean securityDisabled;

    public boolean isSecurityDisabled() {
        return securityDisabled;
    }
}