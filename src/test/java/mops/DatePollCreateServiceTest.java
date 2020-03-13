package mops;

import mops.application.services.DatePollBuilderAndView;
import mops.application.services.DatePollCreateService;

import mops.controllers.dtos.DatePollOptionDto;
import mops.domain.models.datepoll.DatePollConfig;
import mops.domain.models.datepoll.DatePollDescription;
import mops.domain.models.datepoll.DatePollLifeCycle;
import mops.domain.models.datepoll.DatePollLocation;
import mops.domain.models.datepoll.DatePollMetaInf;
import mops.domain.models.user.UserId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("PMD.AtLeastOneConstructor")
public final class DatePollCreateServiceTest {

    @SuppressWarnings("PMD.BeanMembersShouldSerialize")
    private DatePollCreateService datePollCreateService;

    @SuppressWarnings("PMD.BeanMembersShouldSerialize")
    private DatePollBuilderAndView testDatePollBuilderAndView;

    @BeforeEach
    public void initDatePollCreateServiceTest() {
        this.datePollCreateService = new DatePollCreateService();
    }

    /*
    Damit steht den Tests immer ein neues DatePollBuilderAndView Objekt zur Verfügung.
     */
    @BeforeEach
    public void initTestDatePollBuilderAndView() {
        this.testDatePollBuilderAndView = datePollCreateService.initializeDatePoll(new UserId());
    }

    /*
    Testet die Initalisierung des DatePolls - somit auch die obige Methode initTestDatePollBuilderAndView()
     */
    @Test
    public void testDatePollBuildInitialisation() {
        //Set data
        final UserId creatorUsr = new UserId();
        //Submit function
        final DatePollBuilderAndView secondTestDatePollBuilderAndView = datePollCreateService
                .initializeDatePoll(creatorUsr);
        //Test if fields are set
        assertThat(secondTestDatePollBuilderAndView.getBuilder()).isNotNull();
    }

    @Test
    public void testAddDatePollMetaInfo() {
        final DatePollLifeCycle newLifeCycle = new DatePollLifeCycle(LocalDateTime.of(2011, 11, 11, 11, 11, 11),
                LocalDateTime.of(2012, 11, 11, 11, 11, 11));
        final DatePollMetaInf datePollMetaInf = new DatePollMetaInf(
                "foo", new DatePollDescription("blabla"), new DatePollLocation(), newLifeCycle);
        datePollCreateService.addDatePollMetaInf(testDatePollBuilderAndView, datePollMetaInf);
        assertThat(testDatePollBuilderAndView.getMetaInf()).isEqualTo(datePollMetaInf);
    }

    @Test
    @SuppressWarnings({"PMD.AvoidInstantiatingObjectsInLoops", "checkstyle:MagicNumber"})
    public void testInitDatePollOptionList() {
        final List<DatePollOptionDto> datePollOptionDtoList = new ArrayList<>();
        //Initialize DatePollOptionList with 5 Entries
        for (int i = 0; i < 5; i++) {
            datePollOptionDtoList.add(new DatePollOptionDto(
                    LocalDateTime.of(2020, 5, 5, i, 20, 30),
                    LocalDateTime.of(2021, 5, 5, i, 20, 30)));
        }
        datePollCreateService.initDatePollOptionList(testDatePollBuilderAndView, datePollOptionDtoList);
        assertThat(testDatePollBuilderAndView.getDatePollOptionDtos().size()).isEqualTo(5);
    }

    /**
     * Default booleans for DatePollConfiguration.
     * usersCanCreateOption = false;
     * singleChoiceDatePoll = true;
     * priorityChoice = false;
     * datePollIsAnonymous = true;
     * datePollIsPublic = false;
     */
    @Test
    public void testDefaultDateConfiguration() {
        datePollCreateService.openOrClosedPoll(testDatePollBuilderAndView, true);
        final DatePollConfig datePollConfig = testDatePollBuilderAndView.getConfig();
        assertThat(datePollConfig).isNotEqualTo(new DatePollConfig());
    }

    @Test
    public void testCreateDatePollConfiguration() {
        datePollCreateService.openOrClosedPoll(testDatePollBuilderAndView, true);
        assertThat(testDatePollBuilderAndView.getConfig().isOpenForOwnEntries()).isTrue();
    }


}
