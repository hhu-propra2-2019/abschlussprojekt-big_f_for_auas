package mops.controllers;

import mops.adapters.datepolladapter.DatePollEntryAdapter;
import mops.application.services.FakeDatePollVoteService;
import mops.controllers.dtos.DatePollBallotDto;
import mops.domain.models.user.UserId;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;


@Controller
@SessionScope
public class DatePollVoteController {

    private final transient DatePollEntryAdapter entryAdapter;
    private final FakeDatePollVoteService voteService = new FakeDatePollVoteService();


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
     *
     * @param model
     * @param link
     * @param token
     * @return
     */
    @GetMapping("/vote/{link}")
    public String showPoll(Model model, @PathVariable String link, KeycloakAuthenticationToken token) {
        final UserId user = createUserIdFromPrincipal(token);
        final DatePollBallotDto ballot = voteService.showUserVotes(user, link);
        model.addAttribute("ballot", ballot);
        return "mobilePollVote";
    }

    /**
     *
     * @param ballot
     * @param model
     * @param link
     * @param token
     * @return
     */
    @PostMapping("/vote/{link}")
    public String votePoll(@ModelAttribute("ballot") DatePollBallotDto ballot, Model model, @PathVariable String link,  KeycloakAuthenticationToken token) {
        return "redirect:/";
    }
}
