package mops.controllers;

import mops.adapters.datepolladapter.DatePollEntryAdapter;
import mops.controllers.dtos.DatePollBallotDto;
import mops.controllers.dtos.DatePollEntryDto;
import mops.domain.models.Timespan;
import mops.domain.models.user.UserId;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;

import java.time.LocalDateTime;
import java.util.HashSet;
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
    public String showPoll(Model model, @PathVariable String link, KeycloakAuthenticationToken token) {
 //       final Set<FormattedDatePollEntryDto> formattedEntries = entryAdapter.getAllEntriesFormatted(link);
        final Set<DatePollEntryDto> entries = new HashSet<>();

        LocalDateTime time1 = LocalDateTime.of(2020, 03, 22, 16, 15, 27);
        LocalDateTime time2 = LocalDateTime.of(2021, 04, 29, 16, 15, 27);
        Timespan timespan = new Timespan(time1, time2);


        DatePollEntryDto entry1 = new DatePollEntryDto(timespan);
        DatePollEntryDto entry2 = new DatePollEntryDto(time1.minusMonths(32).minusHours(6).plusWeeks(3), time2.plusMonths(29).minusWeeks(23).plusHours(3));
        DatePollEntryDto entry3 = new DatePollEntryDto(time1.minusMonths(1).minusHours(12).plusWeeks(-3), time2.plusMonths(29).minusWeeks(23).plusHours(3));
        DatePollEntryDto entry4 = new DatePollEntryDto(time1.minusMonths(9).minusHours(4).plusWeeks(15), time2.plusMonths(29).minusWeeks(23).plusHours(3));
        DatePollEntryDto entry5 = new DatePollEntryDto(time1.minusMonths(5).minusHours(7).plusWeeks(13), time2.plusMonths(29).minusWeeks(23).plusHours(3));


        DatePollBallotDto balllot = new DatePollBallotDto(createUserIdFromPrincipal(token));
        balllot.addEntries(entry1);
        balllot.addEntries(entry2);
        balllot.addEntries(entry3);
        balllot.addEntries(entry4);
        balllot.addEntries(entry5);

        System.out.println();
        model.addAttribute("ballot", balllot);
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
    public String votePoll(@ModelAttribute("ballot") DatePollBallotDto ballot, Model model, @PathVariable String link,  KeycloakAuthenticationToken token) {
        System.out.println(ballot.getEntries().size());
        ballot.setUser(createUserIdFromPrincipal(token));

        System.out.println("User " + ballot.getUser() + " stimmt ja ab für : ");
        for(DatePollEntryDto entry : ballot.getEntries()) {
            System.out.println(entry.formatString());
        }
//        System.out.println(ballot.getEntries().iterator().next());
        return "redirect:/";
    }
}
