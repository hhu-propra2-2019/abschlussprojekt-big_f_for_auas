package mops.domain.models.datepoll;

import lombok.Builder;
import lombok.Getter;
import mops.controllers.DatePollOptionDto;
import mops.domain.models.user.UserId;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class DatePoll {

    /**
     * Meta-Informationen zur DatePoll.
     */
    private DatePollMetaInf datePollMetaInf;
    /**
     * Id der DatePoll.
     */
    private DatePollId datePollId;
    /**
     * Creator (Typ User) der DatePoll.
     */
    private UserId creator;
    /**
     * Konfiguration der DatePoll. Aggregiert in einem eigens dafür angelegten Objekt.
     */
    private DatePollConfig datePollConfig;
    /**
     * Liste der Terminoptionen der DatePoll.
     */
    private List<DatePollOption> datePollOptions;
    /**
     * Liste der User, die an der Abstimmung teilnehmen **können**.
     */
    private List<UserId> participants;

    /**
     * Link fuer den DatePoll.
     */
    private final DatePollLink datePollLink;

    /**
     * Konstruktor Builder - siehe Lombok Annotation
     * @param creator Ersteller des DatePolls
     * @param datePollId id
     * @param datePollMetaInf Title, Description, Location
     * @param datePollConfig Konfigurationsparameter (booleans)
     * @param datePollOptionDtos Die verschiedenen Datumseintraege
     * @param participants Die Benutzer, die an dem DatePoll teilnehmen.
     */
    @SuppressWarnings("checkstyle:HiddenField")
    @Builder
    public DatePoll(final UserId creator,
                    final DatePollId datePollId,
                    final DatePollMetaInf datePollMetaInf,
                    final DatePollConfig datePollConfig,
                    final List<DatePollOptionDto> datePollOptionDtos,
                    final List<UserId> participants) {
        this.creator = creator;
        this.datePollId = datePollId;
        this.participants = participants;
        this.datePollMetaInf = datePollMetaInf;
        this.datePollConfig = datePollConfig;
        this.datePollOptions = datePollOptionDtos.stream()
                .map(DatePollOption::new)
                .collect(Collectors.toList());
    }

}
