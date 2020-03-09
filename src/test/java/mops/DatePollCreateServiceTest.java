package mops;

import mops.applicationServices.DatePollBuilderAndView;
import mops.applicationServices.DatePollCreateService;
import mops.domain.models.*;
import mops.domain.models.DatePoll.DatePollBuilder;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.sql.ConnectionBuilder;
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
        User creator = new User();
        DatePollConfig datePollConfig = new DatePollConfig();
        DatePollID datePollID = new DatePollID();
        DatePollBuilder datePollBuilder = DatePoll.builder();
        datePollBuilder.datePollID(datePollID);
        datePollBuilder.creator(creator);
        datePollBuilder.datePollConfig(datePollConfig);
        this.testDatePollBuilderAndView = new DatePollBuilderAndView(datePollBuilder,datePollConfig);
    }

    /*
    Testet die Initalisierung des DatePolls - somit auch die obige Methode initTestDatePollBuilderAndView()
     */
    @Test
    public void test_DatePollBuildInitialisation() {
        //Set data
        User creator = new User();
        DatePollConfig datePollConfig = new DatePollConfig();
        DatePollID datePollID = new DatePollID();
        DatePollBuilder datePollBuilder = DatePoll.builder();
        datePollBuilder.datePollID(datePollID);
        datePollBuilder.creator(creator);
        datePollBuilder.datePollConfig(datePollConfig);
        DatePollBuilderAndView datePollBuilderAndView = new DatePollBuilderAndView(datePollBuilder,datePollConfig);

        when(mockedDatePollCreateService.initializeDatePoll(creator))
                .thenReturn(datePollBuilderAndView);

        //Get results:
        DatePollBuilder newDatePollBuilder = datePollCreateService.initializeDatePoll(creator).getBuilder();
        DatePoll initializedDatePoll = newDatePollBuilder.build();

        //Test ...
        assert(initializedDatePoll.getDatePollID().equals(datePollID));
        assert(initializedDatePoll.getCreator().equals(creator));
        assert(initializedDatePoll.getDatePollConfig().equals(datePollConfig));
    }

    @Test
    public void test_addDatePollMetaInfo() {
        Beschreibung beschreibung = new Beschreibung();
        Ort ort = new Ort();
        DatePollMetaInf datePollMetaInf = new DatePollMetaInf("foo",beschreibung,ort);
        DatePollBuilder datePollBuilder = testDatePollBuilderAndView.getBuilder();
        datePollBuilder.datePollMetaInf(datePollMetaInf);
        DatePoll datePollwithMetaInf = datePollBuilder.build();

        assert (datePollwithMetaInf.getDatePollMetaInf().equals(datePollMetaInf));
        assert (datePollwithMetaInf.getDatePollMetaInf().getTitel().equals("foo"));
    }

    @Test
    public void test_initDatePollOptionList() {
        List<DatePollOption> datePollOptionList = new ArrayList<>();
        //Initalize DatePollOptionList with 5 Entries
        for (int i = 0; i < 5; i++) {
            datePollOptionList.add(new DatePollOption());
        }

        DatePollBuilder testDatePollBuilder = testDatePollBuilderAndView.getBuilder();
        testDatePollBuilder.datePollOptionList(datePollOptionList);

        /*when(mockedDatePollCreateService.initDatePollOptionList(testDatePollBuilderAndView,datePollOptionList))
                .thenAnswer(
                        invocation -> {
                            DatePollBuilder testDatePollBuilder = testDatePollBuilderAndView.getBuilder();
                            testDatePollBuilder.datePollOptionList(datePollOptionList);
                            return null;
                        }
                );*/


        assert (testDatePollBuilder.build().getDatePollOptionList().size() == 5);
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
        User creator = new User();
        /*Mit der Initialisierung durch den creator wird ein neues DatePollBuilder Objekt erstellt, der eine neue
        DatePollID erhält.*/
        DatePollBuilderAndView datePollBuilder = datePollCreateService.initializeDatePoll(creator);
        assert(datePollBuilder.getBuilder().build().getDatePollID() != null);
    }


}
