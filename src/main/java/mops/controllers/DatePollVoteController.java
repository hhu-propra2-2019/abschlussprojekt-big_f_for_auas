package mops.controllers;

import mops.adapters.datepolladapter.DatePollEntryAdapter;
import mops.controllers.dtos.DatePollBallotDto;
import mops.controllers.dtos.DatePollEntryDto;
import mops.controllers.dtos.FormattedDatePollEntryDto;
import mops.domain.models.datepoll.DatePollEntry;
import mops.domain.models.datepoll.DatePollLink;
import mops.domain.models.user.UserId;
import org.apache.tomcat.jni.Local;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
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

        DatePollEntryDto entry1 = new DatePollEntryDto(time1, time2);
        DatePollEntryDto entry2 = new DatePollEntryDto(LocalDateTime.now(), LocalDateTime.now());
        DatePollEntryDto entry3 = new DatePollEntryDto(LocalDateTime.now(), LocalDateTime.now());
        DatePollEntryDto entry4 = new DatePollEntryDto(LocalDateTime.now(), LocalDateTime.now());
        DatePollEntryDto entry5 = new DatePollEntryDto(LocalDateTime.now(), LocalDateTime.now());
        entry1.setTitle("ECHTER TITEL !!einself");
        entry2.setTitle("WTF2");
        entry3.setTitle("WTF3");
        entry4.setTitle("WTF4");
        entry5.setTitle("WTF5");

        entries.add(entry1);
        entries.add(entry2);
        entries.add(entry3);
        entries.add(entry4);
        entries.add(entry5);

        DatePollBallotDto balllot = new DatePollBallotDto(createUserIdFromPrincipal(token));
//        balllot.addEntries(entry1);
//        balllot.addEntries(entry2);
//        balllot.addEntries(entry3);
//        balllot.addEntries(entry4);
//        balllot.addEntries(entry5);

        System.out.println();
        model.addAttribute("entries", entries);
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
       // System.out.println(ballot.getSelectedEntriesYes().size());
        ballot.setUser(createUserIdFromPrincipal(token));
        System.out.println(ballot);
        System.out.println(ballot.getSelectedEntriesYes());
        return "redirect:/";
    }
}
