package mops.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.security.RolesAllowed;

/**
 * Controller für das Dashboard, auf das die User zuerst stoßen.
 */

@Controller
@SuppressWarnings("PMD.AtLeastOneConstructor")
public class DashboardController {

    /**
     * GetMapping für das Dashboard.
     * @return Dashboard-Template.
     */
    @RolesAllowed({"ROLE_orga", "ROLE_studentin"})
    @GetMapping("/")
    public String returnDashboard() {
        return "mobile-dashboard";
    }
}
