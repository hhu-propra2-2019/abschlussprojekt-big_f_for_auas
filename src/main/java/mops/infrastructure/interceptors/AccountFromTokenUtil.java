package mops.infrastructure.interceptors;

import lombok.experimental.UtilityClass;
import mops.controllers.dtos.Account;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

@UtilityClass
public final class AccountFromTokenUtil {

    @SuppressWarnings("PMD.LawOfDemeter")
    public static Account createAccountFromToken(KeycloakAuthenticationToken token) {
        final KeycloakPrincipal principal = (KeycloakPrincipal) token.getPrincipal();
        return new Account(
                principal.getName(),
                principal.getKeycloakSecurityContext().getIdToken().getEmail(),
                null,
                token.getAccount().getRoles());
    }

    @SuppressWarnings({"PMD.AvoidCatchingGenericException", "PMD.LawOfDemeter"})
    public static boolean isUserLogged() {
        try {
            return !SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser");
        } catch (Exception e) {
            return false;
        }
    }
}
