package mops.utils;

import lombok.experimental.UtilityClass;
import mops.controllers.dtos.Account;
import mops.domain.models.user.UserId;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;

@UtilityClass
public final class AccountUtil {

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

    // Hier wird nichts auf null gecheckt, weil account nicht null sein kann, wenn Keycloak richtig konfiguriert ist.
    // Siehe auch infrastructure.interceptors.AccountInterceptor
    @SuppressWarnings("PMD.LawOfDemeter")
    public static UserId createUserIdFromRequest(HttpServletRequest request) {
        final Account account = (Account) request.getAttribute("account");
        return new UserId(account.getName());
    }
}
