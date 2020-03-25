package mops.infrastructure.controllers;

import mops.infrastructure.adapters.datepolladapter.DatePollEntryAdapterInterface;
import mops.domain.models.user.UserId;
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

    private final transient DatePollEntryAdapterInterface entryAdapter;

   @Autowired
   public DashboardController(DatePollEntryAdapterInterface entryAdapter) {
        this.entryAdapter = entryAdapter;
   }

    @RolesAllowed({"ROLE_orga", "ROLE_studentin"})
    @GetMapping("/")
    public String returnDashboard(@RequestAttribute(name = "userId") UserId userId, Model model) {
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAA: userId ->" + userId.toString());
        System.out.println(entryAdapter.getAllListItemDtos(userId));
        model.addAttribute("userId", userId);
        model.addAttribute("vonMir", entryAdapter.getAllListItemDtos(userId));
        model.addAttribute("vonAnderen", entryAdapter.getAllListItemDtos(userId));
        return "mobile-dashboard";
    }
}
