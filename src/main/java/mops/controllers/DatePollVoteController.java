package mops.controllers;

import mops.adapters.datepolladapter.DatePollEntryAdapter;
import mops.controllers.dtos.FormattedDatePollEntryDto;
import mops.domain.models.PollLink;
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

    private final transient DatePollEntryAdapter entryAdapter;
    @Autowired
    public DatePollVoteController(DatePollEntryAdapter adapter) {
        this.entryAdapter = adapter;
    }

    @SuppressWarnings({"PMD.LawOfDemeter", "PMD.UnusedPrivateMethod"})
    /* Verletzung in externer API*/
    private UserId createUserIdFromPrincipal(KeycloakAuthenticationToken token) {
        final KeycloakPrincipal principal = (KeycloakPrincipal) token.getPrincipal();
        return new UserId(principal.getKeycloakSecurityContext().getIdToken().getEmail());
    }


    /**
     * getmapping.
     * @param model
     * @param link
     * @return string
     */
    @GetMapping("/vote/{link}")
    public String showPoll(Model model, @PathVariable PollLink link) {
        final Set<FormattedDatePollEntryDto> formattedEntries = entryAdapter.getAllEntriesFormatted(link);
        model.addAttribute("entries", formattedEntries);
        return "pollVote";
    }

    /**
     * postmapping.
     * @param model
     * @param link
     * @param dtos
     * @return string
     */
    @PostMapping("/vote/{link}")
    public String votePoll(Model model, @PathVariable PollLink link, Set<FormattedDatePollEntryDto>  dtos) {

        return "redirect:/vote/";
    }
}
