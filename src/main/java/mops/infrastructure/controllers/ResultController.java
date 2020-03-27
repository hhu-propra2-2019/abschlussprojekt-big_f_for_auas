package mops.infrastructure.controllers;


import mops.domain.models.PollLink;
import mops.domain.models.user.UserId;
import mops.infrastructure.adapters.datepolladapter.DatePollInfoAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;

@Controller
public class ResultController {
    private final transient DatePollInfoAdapter datePollInfoAdapter;

    @Autowired
    public ResultController(DatePollInfoAdapter datePollInfoAdapter) {
        this.datePollInfoAdapter = datePollInfoAdapter;
    }

    /**
     * Getmapping.
     * @param userId
     * @param model
     * @param pollType
     * @param link
     * @return String.
     */
    @GetMapping("/result/{pollType}/{link}")
    public String mapResults(@RequestAttribute(name = "userId") UserId userId,
        Model model, @PathVariable String pollType, @PathVariable String link) {
        model.addAttribute("results", datePollInfoAdapter
            .getAllDatePollResultDto(new PollLink(link)));
        model.addAttribute("metaInf", datePollInfoAdapter
            .getDatePollMetaInformation(new PollLink(link), userId));
        model.addAttribute("config", datePollInfoAdapter
            .getDatePollConfig(new PollLink(link)));
        return "mobilePollResults";
    }
}

