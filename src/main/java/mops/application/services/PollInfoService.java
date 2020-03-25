package mops.application.services;

import mops.domain.models.datepoll.DatePoll;
import mops.domain.models.datepoll.DatePollEntry;
import mops.domain.models.PollLink;
import mops.domain.models.user.UserId;
import mops.domain.repositories.DatePollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@SuppressWarnings({"checkstyle:DesignForExtension", "PMD.AvoidInstantiatingObjectsInLoops",
        "PMD.DataflowAnomalyAnalysis"})
@Service
public class PollInfoService {

    private final transient DatePollRepository datePollRepository;

    @Autowired
    public PollInfoService(DatePollRepository datePollRepository) {
        this.datePollRepository = datePollRepository;
    }

    @SuppressWarnings("PMD.LawOfDemeter")
    public Set<DatePollEntry> getEntries(PollLink link) {
        final DatePoll datePoll = datePollRepository.load(link).orElseThrow();
        return datePoll.getEntries();
    }

    /**
     * Gibt das DTO für die Detailansicht einer Terminabstimmung zurück.
     * @param datePollLink die Referenz für die Terminabstimmung
     * @return ...
     */
    @SuppressWarnings("PMD.LawOfDemeter") // stream
    public DatePoll getDatePollByLink(PollLink datePollLink) {
        return datePollRepository.load(datePollLink).orElseThrow();
    }

    public Set<DatePoll> getDatePollByCreator(UserId userId) {
        return datePollRepository.getDatePollByCreator(userId);
    }

    public Set<DatePoll> getDatePollByStatusFromUser(UserId userId) {
        return datePollRepository.getDatePollWhereUserHasStatus(userId);
    }
}
