package mops;

import mops.application.services.DatePollBuilderAndView;
import mops.application.services.DatePollCreateService;

import mops.controllers.dtos.DatePollOptionDto;
import mops.domain.models.datepoll.*;
import mops.domain.models.user.UserId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DatePollCreateServiceTest {

    private DatePollCreateService datePollCreateService;

    DatePollBuilderAndView testDatePollBuilderAndView;

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
    public void test_datePollBuildInitialisation() {
        //Set data
        UserId creatorUsr = new UserId();
        //Submit function
        DatePollBuilderAndView secondTestDatePollBuilderAndView = datePollCreateService.initializeDatePoll(creatorUsr);
        //Test if fields are set
        assertThat(secondTestDatePollBuilderAndView.getBuilder()).isNotNull();
    }

    @Test
    public void test_addDatePollMetaInfo() {
        DatePollLifeCycle newLifeCycle = new DatePollLifeCycle(LocalDateTime.of(2011,11,11,11,11,11)
                , LocalDateTime.of(2012,11,11,11,11,11));
        DatePollMetaInf datePollMetaInf = new DatePollMetaInf(
                "foo",new DatePollDescription("blabla"),new DatePollLocation(),newLifeCycle);
        datePollCreateService.addDatePollMetaInf(testDatePollBuilderAndView,datePollMetaInf);
        assertThat(testDatePollBuilderAndView.getMetaInf()).isEqualTo(datePollMetaInf);
    }

    @Test
    public void test_initDatePollOptionList() {
        List<DatePollOptionDto> datePollOptionDtoList = new ArrayList<>();
        //Initialize DatePollOptionList with 5 Entries
        for (int i = 0; i < 5; i++) {
            datePollOptionDtoList.add(new DatePollOptionDto(
                    LocalDateTime.of(2020,5,5, 0+i, 20, 30),
                    LocalDateTime.of(2021, 5, 5, 0+i, 20, 30)));
        }
        datePollCreateService.initDatePollOptionList(testDatePollBuilderAndView,datePollOptionDtoList);
        assertThat(testDatePollBuilderAndView.getDatePollOptionDtos().size()).isEqualTo(5);
    }

    /**
     * Default booleans for DatePollConfiguration:
     * usersCanCreateOption = false;
     * singleChoiceDatePoll = true;
     * priorityChoice = false;
     * datePollIsAnonymous = true;
     * datePollIsPublic = false;
     */
    @Test
    public void test_defaultDateConfiguration() {
        datePollCreateService.openOrClosedPoll(testDatePollBuilderAndView,true);
        DatePollConfig datePollConfig = testDatePollBuilderAndView.getConfig();
        assertThat(datePollConfig).isNotEqualTo(new DatePollConfig());
    }

    @Test
    public void test_createDatePollConfiguration() {
        datePollCreateService.openOrClosedPoll(testDatePollBuilderAndView,true);
        assertThat(testDatePollBuilderAndView.getConfig().isOpenForOwnEntries()).isTrue();
    }


}
