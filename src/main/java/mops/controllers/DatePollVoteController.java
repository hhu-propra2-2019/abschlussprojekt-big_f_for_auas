package mops.controllers;

import mops.adapters.datepolladapter.DatePollEntryAdapter;
import mops.controllers.dtos.FormattedDatePollEntryDto;
import mops.domain.models.datepoll.DatePollLink;
import mops.domain.models.user.UserId;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Set;

@Controller
@SessionScope
public class DatePollVoteController {

    private final DatePollEntryAdapter entryAdapter;
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


    @GetMapping("/vote/{link}")
    public String showPoll(Model model, @PathVariable DatePollLink link) {
        Set<FormattedDatePollEntryDto> formattedEntries = entryAdapter.getAllEntriesFormatted(link);
        model.addAttribute("entries", formattedEntries);
        return "pollVote";
    }

    @PostMapping("/vote/{link}")
    public String votePoll(Model model, @PathVariable DatePollLink link, Set<FormattedDatePollEntryDto>  dtos) {

        return "redirect:/vote/";
    }
}
