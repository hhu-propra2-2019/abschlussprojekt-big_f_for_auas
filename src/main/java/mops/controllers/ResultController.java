package mops.controllers;

import java.util.SortedSet;
import mops.adapters.datepolladapter.DatePollInfoAdapter;
import mops.controllers.dtos.DatePollResultDto;
import mops.domain.models.PollLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ResultController {
    private final transient DatePollInfoAdapter datePollInfoAdapter;

    @Autowired
    public ResultController(DatePollInfoAdapter datePollInfoAdapter ) {
        this.datePollInfoAdapter = datePollInfoAdapter;
    }

    @GetMapping("/vote/{link}")
    public String mapResults(Model model,@PathVariable String link) {
        SortedSet<DatePollResultDto> results = datePollInfoAdapter.getAllDatePollResultDto(new PollLink(link));
        model.addAttribute("results", results);
        return "mobilePollResults";
    }
}
