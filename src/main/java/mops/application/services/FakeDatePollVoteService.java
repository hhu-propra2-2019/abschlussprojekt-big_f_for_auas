package mops.application.services;

import mops.domain.models.Timespan;
import mops.domain.models.datepoll.DatePollBallot;
import mops.domain.models.datepoll.DatePollEntry;
import mops.domain.models.user.UserId;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


/** Faked die Funktionsweise vom richtigen DatetPollVoteService, also :
 *  - Gibt mit userid und link das zugehörige ballot zurück.
 *
 */
@Service
public class FakeDatePollVoteService {


    @SuppressWarnings({"PMD.LawOfDemeter"})// Nullcheck
    public DatePollBallot showUserVotes(UserId id, String link) {

        final Set<DatePollEntry> entriesYes = new HashSet<>();
        final Set<DatePollEntry> entriesNo = new HashSet<>();

        LocalDateTime time1 = LocalDateTime.of(2020, 03, 22, 16, 15, 27);
        LocalDateTime time2 = LocalDateTime.of(2021, 04, 29, 16, 15, 27);
        Timespan timespan = new Timespan(time1, time2);


        DatePollEntry entry1 = new DatePollEntry(timespan);
        DatePollEntry entry2 = new DatePollEntry(new Timespan(time1.minusMonths(32).minusHours(6).plusWeeks(3), time2.plusMonths(29).minusWeeks(23).plusHours(3)));
        DatePollEntry entry3 = new DatePollEntry(new Timespan(time1.minusMonths(1).minusHours(12).plusWeeks(-3), time2.plusMonths(29).minusWeeks(23).plusHours(3)));
        DatePollEntry entry4 = new DatePollEntry(new Timespan(time1.minusMonths(9).minusHours(4).plusWeeks(15), time2.plusMonths(29).minusWeeks(23).plusHours(3)));
        DatePollEntry entry5 = new DatePollEntry(new Timespan(time1.minusMonths(5).minusHours(7).plusWeeks(13), time2.plusMonths(29).minusWeeks(23).plusHours(3)));
        entriesYes.add(entry1);
        entriesYes.add(entry2);
        entriesYes.add(entry3);
        entriesYes.add(entry4);
        entriesYes.add(entry5);

        DatePollEntry entry6 = new DatePollEntry(new Timespan(time1.plusYears(100), time2.plusYears(100)));
        entriesNo.add(entry6);


        DatePollBallot ballot = new DatePollBallot(id, entriesYes, entriesNo);


        return ballot;
    }

}
