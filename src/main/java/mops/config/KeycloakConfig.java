package mops.config;

import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * WORKAROUND for https://issues.redhat.com/browse/KEYCLOAK-11282
 * Bean should move into SecurityConfig once Bug has been resolved
 */

@Configuration
public class KeycloakConfig {

    /**
     * Gehört eigentlich in die SecurityConfig, siehe oben.
     * @return für uns unwichtig
     */
    @Bean
    public KeycloakSpringBootConfigResolver keycloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }
}
