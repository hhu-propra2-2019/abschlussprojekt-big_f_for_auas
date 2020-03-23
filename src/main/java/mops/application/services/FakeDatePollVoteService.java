package mops.application.services;

import mops.controllers.dtos.DatePollBallotDto;
import mops.controllers.dtos.DatePollEntryDto;
import mops.domain.models.Timespan;
import mops.domain.models.user.UserId;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class FakeDatePollVoteService {


    @SuppressWarnings({"PMD.LawOfDemeter"})// Nullcheck
    public DatePollBallotDto showUserVotes(UserId id, String link) {

        final Set<DatePollEntryDto> entries = new HashSet<>();

        LocalDateTime time1 = LocalDateTime.of(2020, 03, 22, 16, 15, 27);
        LocalDateTime time2 = LocalDateTime.of(2021, 04, 29, 16, 15, 27);
        Timespan timespan = new Timespan(time1, time2);


        DatePollEntryDto entry1 = new DatePollEntryDto(timespan);
        DatePollEntryDto entry2 = new DatePollEntryDto(time1.minusMonths(32).minusHours(6).plusWeeks(3), time2.plusMonths(29).minusWeeks(23).plusHours(3));
        DatePollEntryDto entry3 = new DatePollEntryDto(time1.minusMonths(1).minusHours(12).plusWeeks(-3), time2.plusMonths(29).minusWeeks(23).plusHours(3));
        DatePollEntryDto entry4 = new DatePollEntryDto(time1.minusMonths(9).minusHours(4).plusWeeks(15), time2.plusMonths(29).minusWeeks(23).plusHours(3));
        DatePollEntryDto entry5 = new DatePollEntryDto(time1.minusMonths(5).minusHours(7).plusWeeks(13), time2.plusMonths(29).minusWeeks(23).plusHours(3));

        DatePollBallotDto balllot = new DatePollBallotDto(id);
        balllot.addEntries(entry1);
        balllot.addEntries(entry2);
        balllot.addEntries(entry3);
        balllot.addEntries(entry4);
        balllot.addEntries(entry5);
        return balllot;
    }

}
