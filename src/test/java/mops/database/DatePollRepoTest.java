package mops.database;

import mops.domain.models.datepoll.*;
//import mops.domain.models.Timespan;

//import mops.domain.models.pollstatus.PollRecordAndStatus;
//import mops.domain.models.user.UserId;
//import mops.infrastructure.database.daos.PollLifeCycleDao;
import mops.infrastructure.database.repositories.DatePollRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/*
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.mock;
*/
public class DatePollRepoTest {
    private DatePollRepositoryImpl datePollRepository;
    private DatePollRepositoryImpl mockedDatePollRepository;
    private DatePoll datePoll;
    @SuppressWarnings({"checkstyle:DesignForExtension", "checkstyle:MagicNumber"})
    @BeforeEach
    public void setupDatePollRepoTest() {
        /*
        datePollRepository = new DatePollRepositoryImpl();
        mockedDatePollRepository = mock(DatePollRepositoryImpl.class);
        Timespan timespan = new Timespan(LocalDateTime.now(), LocalDateTime.now().plusDays(10));
        DatePollMetaInf datePollMetaInf = new DatePollMetaInf("TestDatePoll", "Testing", "Uni", timespan);
        UserId creator = new UserId("1234");
        DatePollConfig datePollConfig = new DatePollConfig();
        List<UserId> participants = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            UserId newUser = new UserId(Integer.toString(i));
            participants.add(newUser);
        }
        DatePollLink datePollLink = new DatePollLink("poll/link");
        PollRecordAndStatus recordAndStatus = new PollRecordAndStatus();
        Set<DatePollOption> pollOptions = new HashSet<>();
        for (int i = 0; i < 3; i++) {
            DatePollOption option = new DatePollOption(
                    new Timespan(LocalDateTime.now().plusDays(i), LocalDateTime.now().plusDays(10 + i))
            );
            pollOptions.add(option);
        }

        datePoll = new DatePollBuilder()
                .datePollMetaInf(datePollMetaInf)
                .creator(creator)
                .datePollConfig(datePollConfig)
                .datePollOptions(pollOptions)
                .participants(participants)
                .datePollLink(datePollLink)
                .build();
         */
    }
    @Test
    public void saveOneDatePollDao() {
        //datePollRepository.save(datePoll);
    }
}
