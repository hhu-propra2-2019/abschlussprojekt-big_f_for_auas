package mops;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.security.RolesAllowed;

// TODO: add javadoc
@Controller
public class TemplateTestController {

    @RolesAllowed({"ROLE_orga", "ROLE_studentin"})
    @GetMapping("/")
    public String returnDashboard() {
        return "mobile-dashboard";
    }
}
