package mops.infrastructure.controllers;

import mops.domain.models.user.UserId;
import mops.infrastructure.adapters.datepolladapter.DatePollInfoAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;

import javax.annotation.security.RolesAllowed;

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
    public String returnDashboard(@RequestAttribute(name = "userId") UserId userId, Model model) {
        model.addAttribute("userId", userId);
        model.addAttribute("vonMir", infoAdapter.getOwnPollsForDashboard(userId));
        model.addAttribute("vonAnderen", infoAdapter.getPollsByOthersForDashboard(userId));
        return "mobile-dashboard";
    }
}
