package mops.infrastructure.controllers;


import mops.infrastructure.adapters.datepolladapter.DatePollEntryAdapterInterface;
import mops.infrastructure.controllers.dtos.DatePollUserEntryOverview;
import mops.domain.models.PollLink;
import mops.domain.models.user.UserId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.context.annotation.SessionScope;

@Controller
@SessionScope
public class DatePollVoteController {


    private final transient DatePollEntryAdapterInterface entryAdapter;


    @Autowired
    public DatePollVoteController(DatePollEntryAdapterInterface entryAdapter) {
        this.entryAdapter = entryAdapter;
    }

    /**
     * GET Mapping.
     * @param model
     * @param link
     * @param pollType
     * @param user UserId wird vom Interceptor gesetzt
     * @return mobilePollVote
     */
    @GetMapping("/vote/{pollType}/{link}")
    public String showPoll(Model model, @RequestAttribute(name = "userId") UserId user,
                           @PathVariable String pollType, @PathVariable String link) {
        final DatePollUserEntryOverview overview = entryAdapter.showUserEntryOverview(new PollLink(link), user);
        model.addAttribute("overview", overview);
        return "mobilePollVote";
    }


    /**
     * POST Mapping.
     * @param overview
     * @param model
     * @param link
     * @param pollType
     * @return redirecte auf /
     */
    @PostMapping("/vote/{pollType}/{link}")
    public String votePoll(@ModelAttribute("overview") DatePollUserEntryOverview overview,
                           Model model, @PathVariable String pollType, @PathVariable String link) {
        return "redirect:/result/" + pollType + "/" + link;
    }
}
