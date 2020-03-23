package mops.controllers;

import mops.adapters.datepolladapter.FakeDatePollEntryAdapter;
import mops.controllers.dtos.DatePollUserEntryOverview;
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


    private final transient FakeDatePollEntryAdapter entryAdapter;

    @Autowired
    public DatePollVoteController(FakeDatePollEntryAdapter adapter) {
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
    public String showPoll(Model model, @PathVariable String link, KeycloakAuthenticationToken token) {

        final UserId user = createUserIdFromPrincipal(token);
        final DatePollUserEntryOverview overview = entryAdapter.showUserEntryOverview(link, user);
        model.addAttribute("overview", overview);
        return "mobilePollVote";
    }

    /**
     * postmapping.
     * @param model
     * @param link
     * @param dtos
     * @return string
     */

    @PostMapping("/vote/{link}")

    public String votePoll(@ModelAttribute("overview") DatePollUserEntryOverview overview, Model model, @PathVariable String link,  KeycloakAuthenticationToken token) {

        return "redirect:/";
    }
}
