package mops.controllers;
/*
import java.time.LocalDateTime;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import mops.adapters.datepolladapter.DatePollInfoAdapter;
import mops.controllers.dtos.DatePollMetaInfDto;
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
  /*  @GetMapping("/result/{link}")
    public String mapResults(Model model, @PathVariable String link) {
        //final SortedSet<DatePollResultDto> results = datePollInfoAdapter.getAllDatePollResultDto(new PollLink(link));
        //final DatePollMetaInfDto metaInf = datePollInfoAdapter.showDatePollMetaInformation(new PollLink(link));
        model.addAttribute("results", results);
        //model.addAttribute("metaInf", metaInf);
        return "mobilePollResults";
    }
}*/
