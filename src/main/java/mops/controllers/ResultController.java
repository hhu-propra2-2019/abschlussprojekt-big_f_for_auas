package mops.controllers;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import mops.adapters.datepolladapter.DatePollInfoAdapter;
import mops.controllers.dtos.DatePollResultDto;
import mops.domain.models.PollLink;
import mops.domain.models.Timespan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ResultController {
    private final transient DatePollInfoAdapter datePollInfoAdapter;


    private Set<DatePollResultDto> createFakeDatePollResultDtos()
    {
        DatePollResultDto fakeResultDto1 = new DatePollResultDto();
        fakeResultDto1.setSuggestedPeriod(new Timespan(LocalDateTime.now(), LocalDateTime.now().plusHours(2)));
        fakeResultDto1.setYesVotes(14);
        fakeResultDto1.setMaybeVotes(10);

        DatePollResultDto fakeResultDto2 = new DatePollResultDto();
        fakeResultDto2.setSuggestedPeriod(new Timespan(LocalDateTime.now().plusDays(2),
                LocalDateTime.now().plusDays(2).plusHours(2)));
        fakeResultDto2.setYesVotes(10);
        fakeResultDto2.setMaybeVotes(0);

        DatePollResultDto fakeResultDto3 = new DatePollResultDto();
        fakeResultDto3.setSuggestedPeriod(new Timespan(LocalDateTime.now().minusDays(2), LocalDateTime.now().minusDays(2).plusHours(2)));
        fakeResultDto3.setYesVotes(5);
        fakeResultDto3.setMaybeVotes(15);

        DatePollResultDto fakeResultDto4 = new DatePollResultDto();
        fakeResultDto4.setSuggestedPeriod(new Timespan(LocalDateTime.now().minusDays(1), LocalDateTime.now().minusDays(1).plusHours(2)));
        fakeResultDto4.setYesVotes(17);
        fakeResultDto4.setMaybeVotes(3);

        Set<DatePollResultDto> testData = new TreeSet<DatePollResultDto>();
        testData.add(fakeResultDto1);
        testData.add(fakeResultDto2);
        testData.add(fakeResultDto3);
        testData.add(fakeResultDto4);

        return testData;
    }
    @Autowired
    public ResultController(DatePollInfoAdapter datePollInfoAdapter) {
        this.datePollInfoAdapter = datePollInfoAdapter;
    }

    /**
     * Getmapping.
     * @param model
     * @param link
     * @return String.
     */
    @GetMapping("/result/{link}")
    public String mapResults(Model model, @PathVariable String link) {
        final SortedSet<DatePollResultDto> results = datePollInfoAdapter.getAllDatePollResultDto(new PollLink(link));
        model.addAttribute("results", results);
        return "mobilePollResults";
    }
}
