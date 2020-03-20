package mops.controllers;

import mops.adapters.datepolladapter.DatePollEntryAdapter;
import mops.controllers.dtos.DatePollEntryDto;
import mops.domain.models.datepoll.DatePollLink;
import mops.domain.models.user.UserId;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.context.annotation.SessionScope;

import javax.annotation.security.RolesAllowed;
import java.util.Set;

@Controller
@SessionScope
public class DatePollVoteController {

    DatePollEntryAdapter entryAdapter;
    private final UserId user;

    @Autowired
    public DatePollVoteController(DatePollEntryAdapter adapter, KeycloakAuthenticationToken token) {
        this.entryAdapter = adapter;
        this.user = createUserIdFromPrincipal(token);
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
        model.addAttribute("userId", createUserIdFromPrincipal(token));
        return "mobile-dashboard";
    }


    @GetMapping("/vote/{link}")
    public String showPoll(Model model, @PathVariable DatePollLink link) {
        Set<DatePollEntryDto> entries = entryAdapter.showAllEntries(link);
        model.addAttribute("entries", entries);
        return "pollVote";
    }
}
