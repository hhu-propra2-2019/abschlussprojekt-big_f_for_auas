package mops;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// TODO: add javadoc
@Controller
public final class TemplateTestController {

    @GetMapping("/")
    public String returnDashboard() {
        return "webflows/scheduling/mobileSchedulingAccessSettings.html";
    }
}
