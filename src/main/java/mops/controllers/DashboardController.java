package mops.controllers;

import mops.domain.models.user.UserId;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.security.RolesAllowed;

/**
 * Controller für das Dashboard, auf das die User zuerst stoßen.
 */

@Controller
@SuppressWarnings("PMD.AtLeastOneConstructor")
public class DashboardController {

    private UserId createUserIdFromPrincipal(KeycloakAuthenticationToken token) {
        KeycloakPrincipal principal = (KeycloakPrincipal) token.getPrincipal();
        return new UserId(principal.getKeycloakSecurityContext().getIdToken().getEmail());
    }

    /**
     * GetMapping für das Dashboard.
     * @return Dashboard-Template.
     */
    @RolesAllowed({"ROLE_orga", "ROLE_studentin"})
    @GetMapping("/")
    public String returnDashboard(KeycloakAuthenticationToken token, Model model) {
        model.addAttribute("userId", createUserIdFromPrincipal(token));
        return "mobile-dashboard";
    }
}
