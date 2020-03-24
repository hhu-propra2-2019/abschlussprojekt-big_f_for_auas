package mops.controllers;

import mops.adapters.datepolladapter.DatePollEntryAdapterInterface;
import mops.application.services.FakeDatePollInfoService;
import mops.controllers.dtos.Account;
import mops.domain.models.user.UserId;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final transient DatePollEntryAdapterInterface entryAdapter;

   @Autowired
   public DashboardController(DatePollEntryAdapterInterface entryAdapter) {
        this.entryAdapter = entryAdapter;
   }

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
        model.addAttribute("vonMir", entryAdapter.getAllListItemDtos(user));
        model.addAttribute("vonAnderen", entryAdapter.getAllListItemDtos(user));
        return "mobile-dashboard";
    }
}
