package mops;

import mops.application.services.DatePollBuilderAndView;
import mops.application.services.DatePollCreateService;

import mops.controllers.DatePollOptionDto;
import mops.domain.models.datepoll.*;
import mops.domain.models.datepoll.DatePoll.DatePollBuilder;
import mops.domain.models.user.UserId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

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
        DatePollLifeCycle newLifeCycle = new DatePollLifeCycle(new Date(2000),new Date(2001));
        DatePollMetaInf datePollMetaInf = new DatePollMetaInf(
                "foo",new DatePollDescription(),new DatePollLocation(),newLifeCycle);
        datePollCreateService.addDatePollMetaInf(testDatePollBuilderAndView,datePollMetaInf);
        assertThat(testDatePollBuilderAndView.getMetaInf()).isEqualTo(datePollMetaInf);
    }

    @Test
    public void test_initDatePollOptionList() {
        List<DatePollOptionDto> datePollOptionDtoList = new ArrayList<>();
        //Initialize DatePollOptionList with 5 Entries
        for (int i = 0; i < 5; i++) {
            datePollOptionDtoList.add(new DatePollOptionDto());
        }
        datePollCreateService.initDatePollOptionList(testDatePollBuilderAndView,datePollOptionDtoList);
        assert(testDatePollBuilderAndView.getDatePollOptionDtos().size() == 5);
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
        DatePollConfig datePollConfig = testDatePollBuilderAndView.getConfig();
        datePollCreateService.openOrClosedPoll(testDatePollBuilderAndView,false);

        assertThat(datePollConfig).isEqualTo(new DatePollConfig());
    }


    @Test
    public void test_createDatePollConfiguration() {
        datePollCreateService.openOrClosedPoll(testDatePollBuilderAndView,true);
        assertThat(testDatePollBuilderAndView.getConfig().isSingleChoiceDatePoll()).isTrue();
    }


}
