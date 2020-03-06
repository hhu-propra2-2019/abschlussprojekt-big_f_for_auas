package mops;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// TODO: add javadoc
@Controller
public final class TemplateTestController {

    @GetMapping("/")
    public String returnDashboard() {
        return "mobile-dashboard";
    }

    @GetMapping("/wizard1")
    public String returnWizard1() {
        return "webflows/scheduling/mobileSchedulingNameSettings.html";
    }

    @GetMapping("/wizard2")
    public String returnWizard2() {
        return "webflows/scheduling/mobileSchedulingAccessSettings.html";
    }

    @GetMapping("/wizard3")
    public String returnWizard3() {
        return "webflows/scheduling/mobileSchedulingChoiceSettings.html";
    }

    @GetMapping("/wizard4")
    public String returnWizard4() {
        return "webflows/scheduling/mobileSchedulingChoiceOptions.html";
    }

    @GetMapping("/wizard5")
    public String returnWizard5() {
        return "webflows/scheduling/mobileSchedulingPublicationSettings.html";
    }

    @GetMapping("/wizard6")
    public String returnWizard6() {
        return "webflows/scheduling/mobileSchedulingConfirmation.html";
    }
}
