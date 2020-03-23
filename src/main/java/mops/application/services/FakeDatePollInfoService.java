package mops.application.services;

import mops.controllers.dtos.DashboardItemDto;
import mops.domain.models.Timespan;
import mops.domain.models.datepoll.*;
import mops.domain.models.user.UserId;
import mops.domain.repositories.DatePollRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@SuppressWarnings("checkstyle:DesignForExtension")
@Service
public class FakeDatePollInfoService {
    private Set<DatePoll>  testDatePollObjects = new HashSet<>();



    public Set<DatePollEntry> getEntries(String link) {

        final Set<DatePollEntry> entries = new HashSet<>();

        LocalDateTime time1 = LocalDateTime.of(2020, 03, 22, 16, 15, 27);
        LocalDateTime time2 = LocalDateTime.of(2021, 04, 29, 16, 15, 27);
        Timespan timespan = new Timespan(time1, time2);


        DatePollEntry entry1 = new DatePollEntry(timespan);
        DatePollEntry entry2 = new DatePollEntry(new Timespan(time1.minusMonths(32).minusHours(6).plusWeeks(3), time2.plusMonths(29).minusWeeks(23).plusHours(3)));
        DatePollEntry entry3 = new DatePollEntry(new Timespan(time1.minusMonths(1).minusHours(12).plusWeeks(-3), time2.plusMonths(29).minusWeeks(23).plusHours(3)));
        DatePollEntry entry4 = new DatePollEntry(new Timespan(time1.minusMonths(9).minusHours(4).plusWeeks(15), time2.plusMonths(29).minusWeeks(23).plusHours(3)));
        DatePollEntry entry5 = new DatePollEntry(new Timespan(time1.minusMonths(5).minusHours(7).plusWeeks(13), time2.plusMonths(29).minusWeeks(23).plusHours(3)));
        DatePollEntry entry6 = new DatePollEntry(new Timespan(time1.plusYears(100), time2.plusYears(100)));

        entries.add(entry1);
        entries.add(entry2);
        entries.add(entry3);
        entries.add(entry4);
        entries.add(entry5);
        entries.add(entry6);

        return entries;
    }

    public Set<DatePoll> genRandomDatePolls(UserId userid) {

        final Set<DatePoll> datePolls = new HashSet<>();

        for (int i = 0; i<10; i++) {
            DatePollRecordAndStatus datePollRecordAndStatus = new DatePollRecordAndStatus();

            Timespan timespan = new Timespan(LocalDateTime.now(), LocalDateTime.of(2020 + i, 3,20, 19, 50, 56));
            DatePollMetaInf datePollMetaInf = new DatePollMetaInf("Abstimmung : " + i, "Beschreibung :" + i, "Bei Nathan", timespan);
            DatePollConfig datePollConfig = new DatePollConfig();
            Set<DatePollEntry> datePollEntries = new HashSet<>();
            DatePollEntry entry1 = new DatePollEntry(new Timespan(LocalDateTime.now(), LocalDateTime.now()));
            datePollEntries.add(entry1);
            Set<UserId> participants = new HashSet<>();
            participants.add(userid);
            Set<DatePollBallot> datePollBallots = new HashSet<>();
            DatePollLink datePollLink = new DatePollLink();
            DatePoll testDatePoll = new DatePoll(datePollRecordAndStatus, datePollMetaInf, userid, datePollConfig, datePollEntries, participants, datePollBallots, datePollLink);

            testDatePoll.castBallot(userid, datePollEntries, datePollEntries);
            datePolls.add(testDatePoll);
        }
        return datePolls;
    }
    /**
     * Gibt die Dtos für jeweils einen Eintrag im Dashboard zurück.
     * @param userId für diesen User
     * @return ...
     */
    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    public Set<DashboardItemDto> getAllListItemDtos(UserId userId) {
        final TreeSet<DashboardItemDto> dashboardItemDtos = new TreeSet<>();
        final Set<DatePoll> datePolls = genRandomDatePolls(userId);
        this.testDatePollObjects = datePolls;
        for (final DatePoll datePoll: datePolls) {
            dashboardItemDtos.add(new DashboardItemDto(datePoll, userId));
        }
        return dashboardItemDtos;
    }

}
