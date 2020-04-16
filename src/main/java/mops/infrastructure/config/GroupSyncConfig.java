package mops.infrastructure.config;

import lombok.NoArgsConstructor;
import mops.domain.repositories.GroupRepository;
import mops.infrastructure.groupsync.GroupSyncService;
import mops.infrastructure.groupsync.GroupSyncValidator;
import mops.infrastructure.groupsync.GroupSyncWebclient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrations;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.server.UnAuthenticatedServerOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableScheduling
@NoArgsConstructor
public class GroupSyncConfig {

    @Value("${hhu.keycloak.issuer-uri}")
    private String issuerUri;
    @Value("${keycloak.resource}")
    private String clientId;
    @Value("${keycloak.credentials.secret}")
    private String clientSecret;
    @Value("${hhu.keycloak.user-name-attribute}")
    private String userNameAttribute;

    /**
     * Hier konfigurieren wir, dass der GroupSyncService nur erstellt wird, wenn das in der application.properties
     * eingestellt ist.
     * @param webclient Der Webclient, der die Anfrage an das SCS stellt
     * @param validator Die Komponente, die das von webclient zurückgegebene Dto validiert
     * @param groupRepository Das Repository, in das die Gruppen synchronisiert werden sollen
     * @return der Service, der die Gruppen synchronisiert.
     */
    @Autowired
    @Bean
    @ConditionalOnProperty(value = "mops.gruppen2.sync.enabled", havingValue = "true")
    public GroupSyncService syncService(GroupSyncWebclient webclient,
                                        GroupSyncValidator validator,
                                        GroupRepository groupRepository) {
        return new GroupSyncService(webclient, validator, groupRepository);
    }

    /**
     * Hier wird eine ClientRegistration anhand der Konfigurationsdaten aus application.properties erzeugt,
     * die dann im WebClient unten verwendet wird.
     * @return ...
     */
    @Bean
    @SuppressWarnings("PMD.LawOfDemeter")
    public InMemoryReactiveClientRegistrationRepository customClientRegistrationRepository() {
        final ClientRegistration clientRegistration =
                ClientRegistrations.fromOidcIssuerLocation(issuerUri)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .registrationId(clientId)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .userNameAttributeName(userNameAttribute)
                .build();
        return new InMemoryReactiveClientRegistrationRepository(clientRegistration);
    }

    /**
     * Hier wird der Webclient für OAuth2 konfiguriert. Dieser Webclient wird dann in GroupSyncWebclient injected.
     * @return ...
     */
    @Bean
    @SuppressWarnings("PMD.LawOfDemeter")
    public WebClient webClient() {
        final ServerOAuth2AuthorizedClientExchangeFilterFunction oauth =
                new ServerOAuth2AuthorizedClientExchangeFilterFunction(
                        customClientRegistrationRepository(),
                        new UnAuthenticatedServerOAuth2AuthorizedClientRepository());
        oauth.setDefaultClientRegistrationId(clientId);
        return WebClient.builder()
                .filter(oauth)
                .build();
    }
}
