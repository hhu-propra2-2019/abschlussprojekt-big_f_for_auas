package mops.controllers;

import java.time.LocalDateTime;
import java.util.SortedSet;
import java.util.TreeSet;

import mops.adapters.datepolladapter.DatePollInfoAdapter;
import mops.controllers.dtos.DatePollMetaInfDto;
import mops.controllers.dtos.DatePollResultDto;
import mops.domain.models.Timespan;
import mops.domain.models.datepoll.DatePollMetaInf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ResultController {

    private DatePollInfoAdapter datePollInfoAdapter; //NOPMD

    @SuppressWarnings("PMD.LawOfDemeter")
    private SortedSet<DatePollResultDto> createFakeDatePollResultDtos() {
        final int number = 5;
        final DatePollResultDto fakeResultDto1 = new DatePollResultDto();
        fakeResultDto1.setTimespan(new Timespan(LocalDateTime.now(), LocalDateTime.now().plusHours(number)));
        fakeResultDto1.setYesVotes(number);
        fakeResultDto1.setMaybeVotes(number);

        final DatePollResultDto fakeResultDto2 = new DatePollResultDto();
        fakeResultDto2.setTimespan(new Timespan(LocalDateTime.now().plusDays(number),
                LocalDateTime.now().plusDays(number).plusHours(number)));
        fakeResultDto2.setYesVotes(number);
        fakeResultDto2.setMaybeVotes(number);

        final DatePollResultDto fakeResultDto3 = new DatePollResultDto();
        fakeResultDto3.setTimespan(new Timespan(LocalDateTime.now().minusDays(number),
                LocalDateTime.now().minusDays(number).plusHours(number)));
        fakeResultDto3.setYesVotes(number);
        fakeResultDto3.setMaybeVotes(number);

        final DatePollResultDto fakeResultDto4 = new DatePollResultDto();
        fakeResultDto4.setTimespan(new Timespan(LocalDateTime.now().minusDays(1),
                LocalDateTime.now().minusDays(number).plusHours(number)));
        fakeResultDto4.setYesVotes(number);
        fakeResultDto4.setMaybeVotes(number);

        final SortedSet<DatePollResultDto> testData = new TreeSet<>();
        testData.add(fakeResultDto1);
        testData.add(fakeResultDto2);
        testData.add(fakeResultDto3);
        testData.add(fakeResultDto4);

        return testData;
    }

    @SuppressWarnings("PMD.LawOfDemeter")
    private DatePollMetaInfDto createFakeDatePollMetaInfDto() {
        final DatePollMetaInf metaInf = new DatePollMetaInf(
                "FakeTerminfindung",
                "Nur zum Test",
                "Zuhause",
                new Timespan(LocalDateTime.now().minusDays(20), LocalDateTime.now().plusDays(2))
        );
        return new DatePollMetaInfDto(metaInf);
    }
    @Autowired
    public ResultController(DatePollInfoAdapter datePollInfoAdapter) {
        this.datePollInfoAdapter = datePollInfoAdapter;
    }

    /**
     * Getmapping.
     * @param model
     * @param pollType
     * @param link
     * @return String.
     */
    @GetMapping("/result/{pollType}/{link}")
    public String mapResults(Model model, @PathVariable String pollType, @PathVariable String link) {
        //final SortedSet<DatePollResultDto> results = datePollInfoAdapter.getAllDatePollResultDto(new PollLink(link));
        //final DatePollMetaInfDto metaInf = datePollInfoAdapter.showDatePollMetaInformation(new PollLink(link));

        //fakes
        final SortedSet<DatePollResultDto> results = createFakeDatePollResultDtos();
        final DatePollMetaInfDto metaInf = createFakeDatePollMetaInfDto();


        model.addAttribute("results", results);
        model.addAttribute("metaInf", metaInf);
        return "mobilePollResults";
    }
}
