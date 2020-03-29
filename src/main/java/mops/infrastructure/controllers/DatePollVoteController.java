package mops.infrastructure.controllers;

import mops.application.services.PollInfoService;
import mops.domain.models.datepoll.DatePoll;
import mops.domain.models.pollstatus.PollStatus;
import mops.domain.models.user.User;
import mops.domain.repositories.UserRepository;
import mops.infrastructure.adapters.datepolladapter.DatePollEntriesAdapter;
import mops.infrastructure.adapters.datepolladapter.DatePollInfoAdapter;
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

    private final transient DatePollEntriesAdapter entryAdapter;
    private final transient DatePollInfoAdapter infoAdapter;
    private final transient PollInfoService infoService;
    private final transient UserRepository userRepository;

    @Autowired
    public DatePollVoteController(
            DatePollEntriesAdapter entryAdapter,
            DatePollInfoAdapter infoAdapter,
            PollInfoService infoService,
            UserRepository userRepository) {
        this.entryAdapter = entryAdapter;
        this.infoAdapter = infoAdapter;
        this.infoService = infoService;
        this.userRepository = userRepository;
    }

    /**
     * GET Mapping.
     * @param model
     * @param link
     * @param pollType
     * @param userId UserId wird vom Interceptor gesetzt
     * @return mobilePollVote
     */
    @GetMapping("/vote/{pollType}/{link}")
    public String showPoll(Model model, @RequestAttribute(name = "userId") UserId userId,
                           @PathVariable String pollType, @PathVariable String link) {
        // TODO: Check, of User vorhanden
        final User user = userRepository.load(userId).get();
        final DatePoll datepoll = infoService.getDatePollByLink(new PollLink(link));
        final PollStatus userStatus = datepoll.getUserStatus(user);
        if (userStatus == PollStatus.ONGOING) {
            return "redirect:/result/" + pollType + "/" + link;
        }

        model.addAttribute("overview", entryAdapter.showUserEntryOverview(new PollLink(link), user.getId()));
        model.addAttribute("config", infoAdapter.getDatePollConfig(new PollLink(link)));
        return "mobilePollVote";
    }

    /**
     * POST Mapping.
     * @param overview
     * @param model
     * @param link
     * @param pollType
     * @param user
     * @return redirecte auf /
     */
    @PostMapping("/vote/{pollType}/{link}")
    public String votePoll(@ModelAttribute("overview") DatePollUserEntryOverview overview,
                           Model model, @PathVariable String pollType, @PathVariable String link,
                           @RequestAttribute(name = "userId") UserId user) {
        entryAdapter.vote(new PollLink(link), user, overview); //NOPMD
        return "redirect:/result/" + pollType + "/" + link;
    }
}
