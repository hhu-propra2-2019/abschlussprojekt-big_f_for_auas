package mops.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.security.RolesAllowed;

@Controller
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
