package mops.controllers;

import mops.application.services.FakeDatePollInfoService;
import mops.domain.models.user.UserId;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.security.RolesAllowed;

/**
 * Controller für das Dashboard, auf das die User zuerst stoßen.
 */

@Controller
@SuppressWarnings({"PMD.AtLeastOneConstructor", "checkstyle:DesignForExtension"})
public class DashboardController {

   private final FakeDatePollInfoService pollInfoService = new FakeDatePollInfoService();

    @SuppressWarnings({"PMD.LawOfDemeter"})
    /* Verletzung in externer API*/
    private UserId createUserIdFromPrincipal(KeycloakAuthenticationToken token) {
        final KeycloakPrincipal principal = (KeycloakPrincipal) token.getPrincipal();
        return new UserId(principal.getKeycloakSecurityContext().getIdToken().getEmail());
    }


    @RolesAllowed({"ROLE_orga", "ROLE_studentin"})
    @GetMapping("/")
    public String returnDashboard(KeycloakAuthenticationToken token, Model model) {
        final UserId user = createUserIdFromPrincipal(token);
        model.addAttribute("userId", user);
        model.addAttribute("vonMir", pollInfoService.getAllListItemDtos(user));
        model.addAttribute("vonAnderen", pollInfoService.getAllListItemDtos(user));
        return "mobile-dashboard";
    }
}
