package mops;

import mops.applicationServices.DatePollBuilderAndView;
import mops.applicationServices.DatePollCreateService;

import mops.controllers.DatePollMetaInfDto;
import mops.controllers.DatePollOptionDto;
import mops.domain.models.DatePoll.DatePoll;
import mops.domain.models.DatePoll.DatePoll.DatePollBuilder;
import mops.domain.models.DatePoll.DatePollId;
import mops.domain.models.User.UserId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class DatePollCreateServiceTest {

    private DatePollCreateService datePollCreateService;
    private DatePollCreateService mockedDatePollCreateService;

    DatePollBuilderAndView testDatePollBuilderAndView;

    @BeforeEach
    public void initDatePollCreateServiceTest() {
        this.datePollCreateService = new DatePollCreateService();
        this.mockedDatePollCreateService = mock(DatePollCreateService.class);
    }

    /*
    Damit steht den Tests immer ein neues DatePollBuilderAndView Objekt zur Verfügung.
     */
    @BeforeEach
    public void initTestDatePollBuilderAndView() {
        DatePollId datePollID = new DatePollId();
        DatePollBuilder datePollBuilder = DatePoll.builder();
        datePollBuilder.datePollId(datePollID);
        datePollBuilder.creator(new UserId());
        this.testDatePollBuilderAndView = new DatePollBuilderAndView(datePollBuilder);
    }

    /*
    Testet die Initalisierung des DatePolls - somit auch die obige Methode initTestDatePollBuilderAndView()
     */
    @Test
    public void test_datePollBuildInitialisation() {
        //Set data
        DatePollId datePollId = new DatePollId();
        DatePollBuilder datePollBuilder = DatePoll.builder();
        datePollBuilder.datePollId(datePollId);
        UserId usrId = new UserId();
        datePollBuilder.creator(usrId);
        DatePollBuilderAndView datePollBuilderAndView = new DatePollBuilderAndView(datePollBuilder);

        when(mockedDatePollCreateService.initializeDatePoll(new UserId()))
                .thenReturn(datePollBuilderAndView);

        //Get results:
        DatePollBuilder newDatePollBuilder = mockedDatePollCreateService.initializeDatePoll(new UserId()).getBuilder();
        DatePoll initializedDatePoll = newDatePollBuilder.build();

        //Test ...
        assert(initializedDatePoll.getDatePollId().equals(datePollId));
        assert(initializedDatePoll.getCreator().equals(usrId));
    }

    @Test
    public void test_addDatePollMetaInfo() {
        DatePollMetaInfDto datePollMetaInfDto = new DatePollMetaInfDto();
        datePollCreateService.addDatePollMetaInf(testDatePollBuilderAndView,datePollMetaInfDto);
        assert (testDatePollBuilderAndView.getMetaInfDto().equals(datePollMetaInfDto));
    }

    @Test
    public void test_initDatePollOptionList() {
        List<DatePollOptionDto> datePollOptionDtoList = new ArrayList<>();
        //Initalize DatePollOptionList with 5 Entries
        for (int i = 0; i < 5; i++) {
            datePollOptionDtoList.add(new DatePollOptionDto());
        }

        doAnswer(invocation -> {
            DatePollBuilder testDatePollBuilder = testDatePollBuilderAndView.getBuilder();
            testDatePollBuilder.datePollOptionDtos(datePollOptionDtoList);
            assert (testDatePollBuilder.build().getDatePollOptions().size() == 5);
            return null;
        }).when(mockedDatePollCreateService).initDatePollOptionList(testDatePollBuilderAndView,datePollOptionDtoList);

        mockedDatePollCreateService.initDatePollOptionList(testDatePollBuilderAndView,datePollOptionDtoList);
        verify(mockedDatePollCreateService,times(1)).initDatePollOptionList(testDatePollBuilderAndView,datePollOptionDtoList);
    }

    /*@Test
    public void test_createDatePollConfiguration() {
        datePollCreateService.openOrClosedPoll(testDatePollBuilderAndView,true);
        datePollCreateService.setDatePollChoicePriority(testDatePollBuilderAndView,true);
        datePollCreateService.setDatePollChoiceType(testDatePollBuilderAndView,true);
        datePollCreateService.setDatePollVisibility(testDatePollBuilderAndView,true);

    }*/

    @Test
    public void testDatePollIDNotNull() {
        /*Mit der Initialisierung durch den creator wird ein neues DatePollBuilder Objekt erstellt, der eine neue
        DatePollID erhält.*/
        DatePollBuilderAndView datePollBuilder = datePollCreateService.initializeDatePoll(new UserId());
        assert(datePollBuilder.getBuilder().build().getDatePollId() != null);
    }


}
