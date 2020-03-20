package mops.controllers;

import mops.domain.models.datepoll.DatePollLink;
import mops.domain.models.user.UserId;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.security.RolesAllowed;

@Controller
public class DatePollVoteController {



    @SuppressWarnings({"PMD.LawOfDemeter"})
    /* Verletzung in externer API*/
    private UserId createUserIdFromPrincipal(KeycloakAuthenticationToken token) {
        final KeycloakPrincipal principal = (KeycloakPrincipal) token.getPrincipal();
        return new UserId(principal.getKeycloakSecurityContext().getIdToken().getEmail());
    }

    @RolesAllowed({"ROLE_orga", "ROLE_studentin"})
    @GetMapping("/")
    public String returnDashboard(KeycloakAuthenticationToken token, Model model) {
        model.addAttribute("userId", createUserIdFromPrincipal(token));
        return "mobile-dashboard";
    }


    @GetMapping("/vote/{link}")
    public String getPoll( Model model, @PathVariable DatePollLink link) {
        return "pollVote";
    }
}
