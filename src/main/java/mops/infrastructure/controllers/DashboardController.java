package mops.infrastructure.controllers;

import mops.domain.models.user.UserId;
import mops.infrastructure.adapters.datepolladapter.DatePollInfoAdapter;
import mops.infrastructure.controllers.dtos.DashboardItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;

import javax.annotation.security.RolesAllowed;
import java.util.List;

/**
 * Controller für das Dashboard, auf das die User zuerst stoßen.
 */

@Controller
@SuppressWarnings({"PMD.AtLeastOneConstructor", "checkstyle:DesignForExtension"})
public class DashboardController {

    private final transient DatePollInfoAdapter infoAdapter;

    @Autowired
    public DashboardController(DatePollInfoAdapter infoAdapter) {
        this.infoAdapter = infoAdapter;
    }

    @RolesAllowed({"ROLE_orga", "ROLE_studentin"})
    @GetMapping("/")
    @SuppressWarnings({"PMD.DataflowAnomalyAnalysis", "PMD.LawOfDemeter"})
    public String returnDashboard(@RequestAttribute(name = "userId") UserId userId, Model model) {
        final List<DashboardItemDto> ownPolls = infoAdapter.getOwnPollsForDashboard(userId);
        final List<DashboardItemDto> otherPolls = infoAdapter.getPollsByOthersForDashboard(userId);


        /* sorry zeitdruck */
        final List<DashboardItemDto> allPolls = ownPolls;
        allPolls.addAll(otherPolls);

        int terminated = 0;
        int open = 0;
        int reopened = 0;
        int ongoing = 0;
        for (final DashboardItemDto poll : allPolls) {
            final String status = poll.getStatus();
            switch (status) {
                case "fa-exclamation":
                    reopened++;
                    break;
                case "fa-hourglass":
                    open++;
                    break;
                case "fa-check":
                    ongoing++;
                    break;
                default:
                    terminated++;
                    break;
            }
        }

        model.addAttribute("terminated", terminated);
        model.addAttribute("open", open);
        model.addAttribute("reopened", reopened);
        model.addAttribute("ongoing", ongoing);

        model.addAttribute("userId", userId);
        model.addAttribute("vonMir", ownPolls);
        model.addAttribute("vonAnderen", otherPolls);
        return "mobile-dashboard";
    }
}
