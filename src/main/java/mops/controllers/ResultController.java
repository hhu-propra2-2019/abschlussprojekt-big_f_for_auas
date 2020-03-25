package mops.controllers;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import mops.adapters.datepolladapter.DatePollInfoAdapter;
import mops.controllers.dtos.DatePollMetaInfDto;
import mops.controllers.dtos.DatePollResultDto;
import mops.domain.models.PollLink;
import mops.domain.models.Timespan;
import mops.domain.models.datepoll.DatePollMetaInf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ResultController {
    private final transient DatePollInfoAdapter datePollInfoAdapter;


    private SortedSet<DatePollResultDto> createFakeDatePollResultDtos()
    {
        DatePollResultDto fakeResultDto1 = new DatePollResultDto();
        fakeResultDto1.setTimespan(new Timespan(LocalDateTime.now(), LocalDateTime.now().plusHours(2)));
        fakeResultDto1.setYesVotes(14);
        fakeResultDto1.setMaybeVotes(10);

        DatePollResultDto fakeResultDto2 = new DatePollResultDto();
        fakeResultDto2.setTimespan(new Timespan(LocalDateTime.now().plusDays(2),
                LocalDateTime.now().plusDays(2).plusHours(2)));
        fakeResultDto2.setYesVotes(10);
        fakeResultDto2.setMaybeVotes(0);

        DatePollResultDto fakeResultDto3 = new DatePollResultDto();
        fakeResultDto3.setTimespan(new Timespan(LocalDateTime.now().minusDays(2), LocalDateTime.now().minusDays(2).plusHours(2)));
        fakeResultDto3.setYesVotes(5);
        fakeResultDto3.setMaybeVotes(15);

        DatePollResultDto fakeResultDto4 = new DatePollResultDto();
        fakeResultDto4.setTimespan(new Timespan(LocalDateTime.now().minusDays(1), LocalDateTime.now().minusDays(1).plusHours(2)));
        fakeResultDto4.setYesVotes(17);
        fakeResultDto4.setMaybeVotes(3);

        SortedSet<DatePollResultDto> testData = new TreeSet<DatePollResultDto>();
        testData.add(fakeResultDto1);
        testData.add(fakeResultDto2);
        testData.add(fakeResultDto3);
        testData.add(fakeResultDto4);

        return testData;
    }

    private DatePollMetaInfDto createFakeDatePollMetaInfDto() {
        DatePollMetaInf metaInf = new DatePollMetaInf(
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
