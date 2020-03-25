package mops.application.services;

import lombok.NoArgsConstructor;
import mops.infrastructure.controllers.dtos.DashboardItemDto;
import mops.domain.models.PollLink;
import mops.domain.models.Timespan;
import mops.domain.models.datepoll.DatePoll;
import mops.domain.models.datepoll.DatePollEntry;
import mops.domain.models.datepoll.DatePollMetaInf;
import mops.domain.models.datepoll.DatePollRecordAndStatus;
import mops.domain.models.datepoll.DatePollBallot;
import mops.domain.models.datepoll.DatePollConfig;
import mops.domain.models.user.UserId;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@SuppressWarnings({"PMD.LawOfDemeter", "PMD.AvoidInstantiatingObjectsInLoops",
        "checkstyle:MagicNumber", "PMD.DataflowAnomalyAnalysis"}) //NOPMD
@Service
@NoArgsConstructor
public class FakeDatePollInfoService {
    /** get All Entries of one Poll.
     *
     * @param link
     * @return Set<DatePollEntry>
     */
    @SuppressWarnings("checkstyle:MagicNumber")
    public Set<DatePollEntry> getEntries(PollLink link) {

        final Set<DatePollEntry> entries = new HashSet<>();

        final LocalDateTime time1 = LocalDateTime.of(2020, 03, 22, 16, 15, 27);
        final LocalDateTime time2 = LocalDateTime.of(2021, 04, 29, 16, 15, 27);
        final Timespan timespan = new Timespan(time1, time2);


        final DatePollEntry entry1 = new DatePollEntry(timespan);
        final DatePollEntry entry2 = new DatePollEntry(new Timespan(time1.minusMonths(32)
                .minusHours(6).plusWeeks(3), time2.plusMonths(29).minusWeeks(23).plusHours(3)));
        final DatePollEntry entry3 = new DatePollEntry(new Timespan(time1.minusMonths(1)
                .minusHours(12).plusWeeks(-3), time2.plusMonths(29).minusWeeks(23).plusHours(3)));
        final DatePollEntry entry4 = new DatePollEntry(new Timespan(time1
                .minusMonths(9).minusHours(4).plusWeeks(15), time2.plusMonths(29).minusWeeks(23).plusHours(3)));
        final DatePollEntry entry5 = new DatePollEntry(new Timespan(time1
                .minusMonths(5).minusHours(7).plusWeeks(13), time2.plusMonths(29).minusWeeks(23).plusHours(3)));
        final DatePollEntry entry6 = new DatePollEntry(new Timespan(time1.plusYears(100), time2.plusYears(100)));

        entries.add(entry1);
        entries.add(entry2);
        entries.add(entry3);
        entries.add(entry4);
        entries.add(entry5);
        entries.add(entry6);

        return entries;
    }

    /** generate deterministic 10 DatePolls.
     *definieren und dann das als ein dummy service implementieren
     * @param userid
     * @return Set<DatePoll>
     */
    public Set<DatePoll> generateDatePolls(UserId userid) {

        final Set<DatePoll> datePolls = new HashSet<>();

        for (int i = 0; i < 10; i++) {
            final DatePollRecordAndStatus datePollRecordAndStatus = new DatePollRecordAndStatus();

            final Timespan timespan = new Timespan(LocalDateTime.now(),
                    LocalDateTime.of(2020 + i, 3, 20, 19, 50, 56));
            final DatePollMetaInf datePollMetaInf = new DatePollMetaInf("Abstimmung : " + i,
                    "Beschreibung :" + i, "Bei Nathan", timespan);
            final DatePollConfig datePollConfig = new DatePollConfig();
            final Set<DatePollEntry> datePollEntries = new HashSet<>();
            final DatePollEntry entry1 = new DatePollEntry(new Timespan(LocalDateTime.now(), LocalDateTime.now()));
            datePollEntries.add(entry1);
            final Set<UserId> participants = new HashSet<>();
            participants.add(userid);
            final Set<DatePollBallot> datePollBallots = new HashSet<>();
            final PollLink datePollLink = new PollLink();
            final DatePoll testDatePoll = new DatePoll(datePollRecordAndStatus, datePollMetaInf,
                    userid, datePollConfig, datePollEntries, participants, datePollBallots, datePollLink);

            testDatePoll.castBallot(userid, datePollEntries, datePollEntries);
            datePolls.add(testDatePoll);
        }
        return datePolls;
    }
    /**
     * Gibt die Dtos für jeweils einen Eintrag im Dashboard zurück.
     * @param userId für diesen User
     * @return dashboardItemDtos
     */
    public Set<DashboardItemDto> getAllListItemDtos(UserId userId) {
        final TreeSet<DashboardItemDto> dashboardItemDtos = new TreeSet<>();
        final Set<DatePoll> datePolls = generateDatePolls(userId);
        for (final DatePoll datePoll: datePolls) {
            dashboardItemDtos.add(new DashboardItemDto(datePoll, userId));
        }
        return dashboardItemDtos;
    }
}
