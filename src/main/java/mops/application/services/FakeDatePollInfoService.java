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
        DatePollEntry entry1 = new DatePollEntry(new Timespan(LocalDateTime.now(), LocalDateTime.now()));
        DatePollEntry entry2 = new DatePollEntry(new Timespan(LocalDateTime.now(), LocalDateTime.now()));
        DatePollEntry entry3 = new DatePollEntry(new Timespan(LocalDateTime.now(), LocalDateTime.now()));
        Set<DatePollEntry> datePollEntries = new HashSet<>();
        datePollEntries.add(entry1);
        datePollEntries.add(entry2);
        datePollEntries.add(entry3);
        return datePollEntries;
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
